import { useEffect, useRef } from "react";
import * as THREE from "three";
import type { Dimensions, MachineState } from "./types";

type Props = {
  stock?: Dimensions;
  part?: Dimensions;
  machine: MachineState;
};

export function MachineScene({ stock, part, machine }: Props) {
  const container = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!container.current) {
      return;
    }

    const width = container.current.clientWidth;
    const height = 320;
    const scene = new THREE.Scene();
    scene.background = new THREE.Color("#071018");
    const camera = new THREE.PerspectiveCamera(38, width / height, 0.1, 2000);
    camera.position.set(150, 125, 170);
    camera.lookAt(0, 0, 0);

    const renderer = new THREE.WebGLRenderer({ antialias: true });
    renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
    renderer.setSize(width, height);
    container.current.replaceChildren(renderer.domElement);

    scene.add(new THREE.AmbientLight(0xffffff, 1.8));
    const keyLight = new THREE.DirectionalLight(0x9adfff, 3);
    keyLight.position.set(100, 140, 80);
    scene.add(keyLight);
    scene.add(new THREE.GridHelper(300, 20, 0x244354, 0x172b38));

    const stockDimensions = stock ?? { xMm: 100, yMm: 70, zMm: 35 };
    const partDimensions = part ?? {
      xMm: stockDimensions.xMm - 10,
      yMm: stockDimensions.yMm - 10,
      zMm: stockDimensions.zMm - 7,
    };
    const scale = Math.min(
      1,
      130 / Math.max(stockDimensions.xMm, stockDimensions.yMm),
    );

    const stockGeometry = new THREE.BoxGeometry(
      stockDimensions.xMm * scale,
      stockDimensions.zMm * scale,
      stockDimensions.yMm * scale,
    );
    const stockMaterial = new THREE.MeshStandardMaterial({
      color: 0x6f8792,
      transparent: true,
      opacity: Math.max(0.18, 0.68 - machine.materialRemovedPercent / 180),
      wireframe: false,
    });
    const stockMesh = new THREE.Mesh(stockGeometry, stockMaterial);
    stockMesh.position.y = (stockDimensions.zMm * scale) / 2;
    scene.add(stockMesh);
    scene.add(
      new THREE.LineSegments(
        new THREE.EdgesGeometry(stockGeometry),
        new THREE.LineBasicMaterial({ color: 0x9dc0ce }),
      ),
    );

    const partGeometry = new THREE.BoxGeometry(
      partDimensions.xMm * scale,
      partDimensions.zMm * scale,
      partDimensions.yMm * scale,
    );
    const partMesh = new THREE.Mesh(
      partGeometry,
      new THREE.MeshStandardMaterial({
        color: 0x36d399,
        transparent: true,
        opacity: machine.materialRemovedPercent / 100,
      }),
    );
    partMesh.position.y = (partDimensions.zMm * scale) / 2;
    scene.add(partMesh);

    const tool = new THREE.Group();
    const holder = new THREE.Mesh(
      new THREE.CylinderGeometry(8, 11, 28, 24),
      new THREE.MeshStandardMaterial({ color: 0x17242b }),
    );
    const cutter = new THREE.Mesh(
      new THREE.CylinderGeometry(3, 3, 42, 20),
      new THREE.MeshStandardMaterial({ color: 0xffc857, metalness: 0.7 }),
    );
    cutter.position.y = -32;
    tool.add(holder, cutter);
    tool.position.set(
      machine.axes.x * scale,
      Math.max(45, stockDimensions.zMm * scale + machine.axes.z * 0.2),
      machine.axes.y * scale,
    );
    tool.rotation.z = THREE.MathUtils.degToRad(machine.axes.b);
    tool.rotation.y = THREE.MathUtils.degToRad(machine.axes.c);
    scene.add(tool);

    const table = new THREE.Mesh(
      new THREE.CylinderGeometry(88, 88, 8, 64),
      new THREE.MeshStandardMaterial({ color: 0x213643, metalness: 0.6 }),
    );
    table.position.y = -5;
    table.rotation.y = THREE.MathUtils.degToRad(machine.axes.c);
    scene.add(table);

    renderer.render(scene, camera);

    const resize = () => {
      if (!container.current) {
        return;
      }
      const nextWidth = container.current.clientWidth;
      renderer.setSize(nextWidth, height);
      camera.aspect = nextWidth / height;
      camera.updateProjectionMatrix();
      renderer.render(scene, camera);
    };
    window.addEventListener("resize", resize);

    return () => {
      window.removeEventListener("resize", resize);
      renderer.dispose();
      stockGeometry.dispose();
      partGeometry.dispose();
    };
  }, [machine, part, stock]);

  return (
    <div
      className="machine-scene"
      ref={container}
      aria-label="Three-dimensional UMC-750 process visualization"
    />
  );
}

