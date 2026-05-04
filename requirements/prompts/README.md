# Agent Prompt Workflow

## Overview

This directory implements an **agent-driven development pipeline**. Requirements are written as prompt files (`.md`) and flow through stages as agents implement them.

## Pipeline Stages

```
new/  →  in-progress/  →  deploying/  →  validating/  →  completed/
```

| Stage | Meaning | Who acts |
|-------|---------|----------|
| `new/` | Ready for an agent to pick up | Human places here |
| `in-progress/` | Agent is actively working on this | Agent moves here |
| `deploying/` | Implementation is being deployed | Agent/CI moves here |
| `validating/` | Deployed and under validation | Agent/CI moves here |
| `completed/` | Successfully validated and done | Agent/Human moves here |

## How It Works

### For Humans (Architects)
1. Write a requirement in `requirements/functional-areas/<area>/`
2. Create a prompt file using the [template](template.md)
3. Place the prompt in `new/`
4. The agent picks it up automatically

### For Agents
1. Check `new/` for available work
2. Move the prompt file to `in-progress/`
3. Read the linked requirement and implement the changes
4. Submit a PR and move the prompt to `deploying/`
5. After deployment, move to `validating/`
6. After validation passes, move to `completed/`

## File Naming

```
YYYYMMDD-<area>-<short-description>.md
```

Example: `20260504-approvals-create-request-endpoint.md`

## Template

See [template.md](template.md) for the standard prompt file format.
