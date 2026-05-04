# Contributing to ah-he

## Harness Engineering Workflow

This project follows [harness engineering](https://openai.com/index/harness-engineering/) principles. The primary workflow is:

1. **Humans architect** — define requirements, design systems, set standards
2. **Agents build** — pick up prompt files, implement code, write tests, submit PRs
3. **Mechanical enforcement** — CI/CD validates correctness, standards compliance

## How to Contribute a Requirement

1. Write a requirement in the appropriate `requirements/functional-areas/<area>/` directory
2. Create a prompt file from the [template](requirements/prompts/template.md)
3. Place the prompt in `requirements/prompts/new/`
4. An agent (or human) picks it up and moves it through the pipeline

## How to Contribute Code

1. Create a feature branch from `main`
2. Make changes following [coding standards](docs/standards/coding-standards.md)
3. Write tests per [testing standards](docs/standards/testing-standards.md)
4. Ensure all CI checks pass
5. Submit a pull request

## Commit Messages

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
feat(web-api): add approval endpoint
fix(worker): handle timeout in notification sender
docs(requirements): add user-management specs
```

## Branch Naming

```
feat/<area>/<short-description>
fix/<area>/<short-description>
docs/<short-description>
```
