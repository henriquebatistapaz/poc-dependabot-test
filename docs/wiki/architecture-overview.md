# Architecture overview

> Repo laboratório: um projeto Maven mínimo cercado de automação — Dependabot propõe updates, o CI valida, o CD publica imagem e atualiza o manifest que o Argo CD aplica.

## Flow

```mermaid
flowchart LR
    DB[Dependabot] -->|PRs agrupados| PR[Pull request]
    PR --> CI[ci.yml: mvn verify]
    PR --> MD[dependabot-metadata.yml: rótulos]
    M[merge em develop/main] --> CD[cicd.yml ou Jenkinsfile]
    CD -->|docker build/push| H[(Harbor)]
    CD -->|atualiza tag| K[deploy/k8s/overlays/dev]
    K --> A[Argo CD] --> C[(Cluster dev)]
```

## Key classes / entry points

| Arquivo | Papel |
|-------|------|
| `.github/dependabot.yml` | Agenda, grupos e prefixos dos updates |
| `.github/workflows/ci.yml` | `mvn verify` em PRs |
| `.github/workflows/cicd.yml` / `Jenkinsfile` | Testa → imagem no Harbor → atualiza manifest dev |
| `.github/workflows/release.yml` | Release |
| `deploy/k8s/`, `deploy/argocd/` | Manifests Kustomize e Application do Argo CD |

## Related

- [[pipeline-cicd-gitops]], [[dominio-dependabot]], [[decision-sem-codeql]]
