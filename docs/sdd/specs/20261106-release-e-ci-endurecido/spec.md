# SDD: release-e-ci-endurecido

**Complexity:** S

> SDD retroativa: documenta o que já foi entregue (commit 769fc72 (ci: add release workflow, harden CI and drop dependency-review)). Escrita em 2026-11-06 a partir do código e do histórico do repo; status `Done`.

## 1. Context
O CI inicial usava uma action desatualizada de propósito e não havia release.

## 2. Goal
Ter CI confiável (`mvn verify`) e um workflow de release.

## 3. Scope
### 3.1 In scope
- `ci.yml` com `mvn --batch-mode --errors verify`.
- `release.yml`.
- Remoção do dependency-review (exige recurso não disponível).

### 3.2 Out of scope
- Publicação em repositório Maven.

## 4. Requirements
### 4.1 Functional
- FR-01: Todo PR roda `mvn verify`.
- FR-02: Release gerada pelo workflow `release.yml`.

### 4.2 Non-functional
- —

## 5. Architecture and Design
Workflows GitHub Actions com permissões mínimas (`contents: read`).

## 6. Implementation Plan
Já implementado. Principais pontos no código:
- `.github/workflows/ci.yml`
- `.github/workflows/release.yml`

## 7. Acceptance Criteria
- AC-01: CI verde em PRs.
- AC-02: Workflow de release executa `mvn verify` antes de publicar.

## 8. Observability and Operations
- Sem requisitos específicos além dos logs padrão da aplicação.

## 9. Risks and Mitigations
- —

## 10. Rollback
Reverter os commits/PRs listados nas referências.

## 11. References
- commit 769fc72 (ci: add release workflow, harden CI and drop dependency-review)
