# Pipeline CI/CD e GitOps

> Do push ao cluster dev sem `kubectl apply` manual.

## Business rules

- Sem `vars.HARBOR_REGISTRY` o job de imagem é pulado (o CI continua verde em forks/ambientes sem Harbor).
- A tag da imagem é o commit (12 caracteres no Jenkins).
- Runner configurável por `vars.RUNNER_LABEL` (padrão `ubuntu-latest`).

## Gotchas

- Mudou `.github/workflows/cicd.yml`? Espelhe no `Jenkinsfile`.

## Related

- `docs/sdd/specs/20261106-pipeline-harbor-k8s-argocd/spec.md`
