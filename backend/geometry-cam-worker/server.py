import json
import math
import os
import tempfile
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import parse_qs, urlparse

import cadquery as cq

MAX_UPLOAD_BYTES = 20 * 1024 * 1024
PORT = int(os.environ.get("PORT", "8090"))


def vector(value):
    return [round(component, 9) for component in value.toTuple()]


def legal_orientation(normal):
    nx, ny, nz = normal
    length = math.sqrt(nx * nx + ny * ny + nz * nz)
    if length == 0:
        return None
    nx, ny, nz = nx / length, ny / length, nz / length
    tilt = math.degrees(math.acos(max(-1.0, min(1.0, nz))))
    b = tilt if nx >= 0 else -tilt
    c = math.degrees(math.atan2(ny, nx)) % 360
    if b < -35 or b > 110:
        return None
    return {"bDegrees": round(b, 6), "cDegrees": round(c, 6)}


def analyze_step(path):
    imported = cq.importers.importStep(str(path))
    shape = imported.val()
    solids = shape.Solids()
    if not solids:
        raise ValueError("STEP file contains no solid bodies")

    bounds = shape.BoundingBox()
    faces = []
    orientation_keys = set()
    orientations = []

    for index, face in enumerate(shape.Faces()):
        center = face.Center()
        normal = face.normalAt()
        face_type = face.geomType()
        cylinder = None
        if face_type == "CYLINDER":
            surface = face._geomAdaptor()
            base = surface.BasisSurface() if hasattr(surface, "BasisSurface") else surface
            axis = base.Axis()
            axis_direction = (
                axis.Direction().X(),
                axis.Direction().Y(),
                axis.Direction().Z(),
            )
            axis_point = (
                axis.Location().X(),
                axis.Location().Y(),
                axis.Location().Z(),
            )
            surface_point = face.positionAt(0.5, 0.5)
            point = surface_point.toTuple()
            relative = tuple(point[i] - axis_point[i] for i in range(3))
            axial_projection = sum(
                relative[i] * axis_direction[i] for i in range(3)
            )
            radial = tuple(
                relative[i] - axial_projection * axis_direction[i] for i in range(3)
            )
            normal_tuple = normal.toTuple()
            orientation_dot = sum(normal_tuple[i] * radial[i] for i in range(3))
            cylinder = {
                "radiusMm": round(base.Radius(), 6),
                "axisDirection": vector(cq.Vector(*axis_direction)),
                "axisPointMm": vector(cq.Vector(*axis_point)),
                "internal": orientation_dot < 0,
            }
        candidate = legal_orientation(normal.toTuple()) if face_type == "PLANE" else None
        if candidate:
            key = (round(candidate["bDegrees"], 3), round(candidate["cDegrees"], 3))
            if key not in orientation_keys:
                orientation_keys.add(key)
                orientations.append(
                    {
                        "id": f"orientation-{len(orientations) + 1}",
                        "toolDirection": vector(normal),
                        **candidate,
                    }
                )
        face_bounds = face.BoundingBox()
        faces.append(
            {
                "id": f"face-{index + 1}",
                "surfaceType": face_type,
                "areaMm2": round(face.Area(), 6),
                "centerMm": vector(center),
                "normal": vector(normal),
                "edgeCount": len(face.Edges()),
                "boundsMm": {
                    "x": round(face_bounds.xlen, 6),
                    "y": round(face_bounds.ylen, 6),
                    "z": round(face_bounds.zlen, 6),
                },
                "candidateOrientation": candidate,
                "cylinder": cylinder,
            }
        )

    return {
        "kernel": "CadQuery/OCP/Open CASCADE",
        "units": "mm",
        "solidCount": len(solids),
        "faceCount": len(faces),
        "volumeMm3": round(sum(solid.Volume() for solid in solids), 6),
        "centerMm": vector(shape.Center()),
        "boundsMm": {
            "x": round(bounds.xlen, 6),
            "y": round(bounds.ylen, 6),
            "z": round(bounds.zlen, 6),
        },
        "faces": faces,
        "candidateOrientations": orientations,
        "warnings": [
            "Candidate orientations are topology evidence only; fixture, holder, occlusion, "
            "and collision analysis must complete before setup selection."
        ],
    }


class Handler(BaseHTTPRequestHandler):
    server_version = "AhHeGeometryWorker/0.1"

    def do_GET(self):
        if self.path == "/health":
            self.respond(200, {"status": "ok", "kernel": "CadQuery/OCP/Open CASCADE"})
            return
        self.respond(404, {"error": "not_found"})

    def do_POST(self):
        parsed = urlparse(self.path)
        if parsed.path != "/api/v1/geometry/analyze":
            self.respond(404, {"error": "not_found"})
            return

        content_length = int(self.headers.get("Content-Length", "0"))
        if content_length <= 0 or content_length > MAX_UPLOAD_BYTES:
            self.respond(400, {"error": "invalid_size"})
            return

        query = parse_qs(parsed.query)
        requested_name = Path(query.get("filename", ["part.step"])[0]).name
        suffix = Path(requested_name).suffix.lower()
        if suffix not in {".step", ".stp"}:
            self.respond(400, {"error": "unsupported_format", "message": "STEP/STP required"})
            return

        content = self.rfile.read(content_length)
        temp_path = None
        try:
            with tempfile.NamedTemporaryFile(suffix=suffix, delete=False) as temp_file:
                temp_file.write(content)
                temp_path = Path(temp_file.name)
            result = analyze_step(temp_path)
            self.respond(200, result)
        except Exception as error:
            self.respond(
                422,
                {
                    "error": "geometry_analysis_failed",
                    "message": f"{type(error).__name__}: {str(error)[:500]}",
                },
            )
        finally:
            if temp_path:
                temp_path.unlink(missing_ok=True)

    def log_message(self, format_string, *args):
        print(
            json.dumps(
                {
                    "client": self.client_address[0],
                    "message": format_string % args,
                }
            ),
            flush=True,
        )

    def respond(self, status, payload):
        body = json.dumps(payload, separators=(",", ":")).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)


if __name__ == "__main__":
    server = ThreadingHTTPServer(("0.0.0.0", PORT), Handler)
    print(json.dumps({"status": "starting", "port": PORT}), flush=True)
    server.serve_forever()
