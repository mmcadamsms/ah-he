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
published npm packages. A dependency lock file must be generated from an
organization-approved npm registry before release; this workstation's policy
blocked npm package downloads during prototype validation.

The Docker-hosted prototype portal does not load those packages. It uses local
HTML, CSS, and JavaScript so it can run under the current npm content policy.

No CAMotics, FreeCAD, OpenCAMLib, LinuxCNC, or Haas code is copied or linked
into the prototype. Those projects and Haas documentation are research
references only.
