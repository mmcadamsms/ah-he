# Requirements

## Overview

Product requirements are organized by **functional area** — logical groupings of related features and capabilities. Each functional area has its own directory with requirement documents.

## Functional Areas

| Area | Description |
|------|-------------|
| [user-management](functional-areas/user-management/) | User accounts, roles, profiles, authentication |
| [approvals](functional-areas/approvals/) | Approval workflows, chains, delegation, escalation |
| [notifications](functional-areas/notifications/) | Email, push, in-app notification delivery |

> Add new functional areas as the product grows. Each area gets its own directory under `functional-areas/`.

## Agent Prompt Workflow

Requirements are translated into actionable **prompt files** that agents pick up and implement. See [prompts/README.md](prompts/README.md) for the full workflow.

## Writing Good Requirements

A good requirement document:
1. **States the problem** — what user need does this address?
2. **Defines acceptance criteria** — how do we know it's done?
3. **Lists constraints** — performance, security, compatibility
4. **References related requirements** — cross-link to dependencies
5. **Is agent-legible** — clear enough that an AI agent can implement it
