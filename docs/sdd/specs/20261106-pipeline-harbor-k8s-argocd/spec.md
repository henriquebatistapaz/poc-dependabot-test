# SDD: pipeline-harbor-k8s-argocd

**Complexity:** M

> SDD retroativa: documenta o que já foi entregue (PRs #13 e #14 (pipeline Harbor, manifests Kubernetes, Argo CD, Jenkinsfile; skip sem Harbor)). Escrita em 2026-11-06 a partir do código e do histórico do repo; status `Done`.

## 1. Context
O laboratório precisava cobrir também o caminho de entrega: imagem, manifests e sincronização com o cluster.

## 2. Goal
Fluxo testa → imagem no Harbor → atualiza manifest dev → Argo CD sincroniza, em GitHub Actions e em Jenkins.

## 3. Scope
### 3.1 In scope
- `cicd.yml` e `Jenkinsfile` equivalentes.
- Dockerfile.
- `deploy/k8s` (base + overlay dev) e `deploy/argocd/application-dev.yaml`.
- Pular o job de imagem quando o Harbor não está configurado (#14).

### 3.2 Out of scope
- Ambientes além de dev.
- Provisionamento do cluster/Argo CD.

## 4. Requirements
### 4.1 Functional
- FR-01: Push em develop/main publica a imagem com tag do commit no Harbor.
- FR-02: O pipeline atualiza a tag no overlay dev.
- FR-03: Sem `vars.HARBOR_REGISTRY`, o job de imagem é pulado sem falhar o workflow.

### 4.2 Non-functional
- Segredos só em GitHub Secrets / credenciais Jenkins.
- Concorrência por ref com cancelamento.

## 5. Architecture and Design
GitOps: o pipeline nunca aplica no cluster; só versiona a mudança de tag. Runner configurável por variável.

## 6. Implementation Plan
Já implementado. Principais pontos no código:
- `.github/workflows/cicd.yml`
- `Jenkinsfile`
- `Dockerfile`
- `deploy/k8s/**`
- `deploy/argocd/application-dev.yaml`

## 7. Acceptance Criteria
- AC-01: `kubectl kustomize deploy/k8s/overlays/dev` renderiza sem erro.
- AC-02: Sem Harbor configurado, o workflow termina verde com o job de imagem pulado.
- AC-03: Com Harbor, a imagem é publicada e o manifest dev atualizado.

## 8. Observability and Operations
- Sem requisitos específicos além dos logs padrão da aplicação.

## 9. Risks and Mitigations
- Credenciais do robô Harbor vazarem → só em secrets, permissões mínimas.

## 10. Rollback
Reverter os commits/PRs listados nas referências.

## 11. References
- PRs #13 e #14 (pipeline Harbor, manifests Kubernetes, Argo CD, Jenkinsfile; skip sem Harbor)
