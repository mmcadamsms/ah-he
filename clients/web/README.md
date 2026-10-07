# Web Client

React/TypeScript customer portal for visible manufacturing planning and
simulation.

Docker serves a dependency-free browser implementation of the same API flow so
the prototype can run on workstations where organization policy blocks npm.
The React/Three.js implementation remains the target UX under `src/`.

## Tech Stack
- React 18
- TypeScript
- Vite
- Three.js

## Running Locally

```bash
npm install
npm run dev
```

Available at `http://localhost:3000`

## Docker

```bash
docker build -t ahhe-web-ux .
docker run -p 3000:3000 ahhe-web-ux
```
