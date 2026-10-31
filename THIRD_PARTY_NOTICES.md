# Third-Party Notices

## Included sample model

`clients/web/public/samples/T8_housing_bracket.step`

- Work: T8 housing bracket
- Author: GitHub user `hasecilu`
- Source:
  <https://github.com/FreeCAD/FreeCAD-library/blob/f3b1956f06115e53e465b1411b5364d78a32b8b6/Mechanical%20Parts/Mountings/T8_housing_bracket/T8_housing_bracket.step>
- License: Creative Commons Attribution 3.0
  (<https://creativecommons.org/licenses/by/3.0/>)
- Change: file name normalized for use as the local portal sample; model
  contents are unchanged.
- SHA-256:
  `A40963B851C4D64DAE6D251461883A02C3650B4467E4087B9250A65BB99F1B74`

## Runtime libraries

The web portal uses pinned React, Vite, and Three.js versions through their
published npm packages. `clients/web/package-lock.json` now records reproducible
resolved dependencies; the operational portal is built from these packages.

The Docker-hosted prototype portal does not load those packages. It uses local
HTML, CSS, and JavaScript so it can run under the current npm content policy.

No CAMotics, FreeCAD, OpenCAMLib, LinuxCNC, or Haas code is copied or linked
into the prototype. Those projects and Haas documentation are research
references only.

## Operational Platform Components

- Azure SDK for Java (BOM 1.3.9), Spring Boot and OpenTelemetry Collector contrib
  0.164.0: Apache License 2.0; retain upstream notices in distributed components.
- Logstash Logback encoder 7.4: Apache License 2.0.
- Azurite 3.35.0 and React: MIT; Azurite is a local test tool, not production
  storage.
- Grafana Loki 3.7.9: **GNU Affero General Public License v3.0**.
  Review deployment/modification/source-distribution obligations before release.
  This notice does not assert that using Loki relicenses unrelated application
  code. No Loki code is copied into the shared domain library.
- Operational Nginx unprivileged image 1.31.7-alpine is pinned by published image
  digest; preserve Nginx/container dependency licenses and notices.

Configuration validation does not replace dependency vulnerability or legal
review. Grafana UI is an external authenticated integration, not bundled here.
