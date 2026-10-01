# Geometry/CAM Worker

Local STEP topology service built on CadQuery, OCP, and Open CASCADE.

The worker accepts raw STEP bytes and returns exact solid bounds, volume, face
topology, face normals, and legal first-pass UMC orientation candidates. It
does not yet claim that a candidate is machinable; fixture, tool/holder reach,
occlusion, and collision analysis must complete before setup selection.

```text
POST /api/v1/geometry/analyze?filename=part.step
Content-Type: application/octet-stream
```

The container image is pinned by digest to the CadQuery project image. Uploaded
files are treated as data, written to a temporary file, imported by Open
CASCADE, and deleted after analysis.

