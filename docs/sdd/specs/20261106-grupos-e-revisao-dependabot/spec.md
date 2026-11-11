# SDD: grupos-e-revisao-dependabot

**Complexity:** S

> SDD retroativa: documenta o que já foi entregue (PR #6 (build(deps): group dependabot updates and add review workflows) e PR #11). Escrita em 2026-11-06 a partir do código e do histórico do repo; status `Done`.

## 1. Context
Depois do primeiro teste (spec `dependabot-test`), o Dependabot abria um PR por dependência, gerando ruído.

## 2. Goal
Reduzir ruído agrupando updates e facilitar a revisão com rótulos automáticos.

## 3. Scope
### 3.1 In scope
- Grupos Maven minor/patch e de teste; grupo de Actions.
- Agenda semanal com cooldown de 3 dias e prefixos de commit.
- Workflow de metadados que rotula `non-major`/`major-update`.
- Template de PR e SECURITY.md.

### 3.2 Out of scope
- Auto-merge.
- CodeQL (removido: exige GHAS).

## 4. Requirements
### 4.1 Functional
- FR-01: Updates minor/patch de Maven chegam em um PR por semana.
- FR-02: PRs do Dependabot recebem rótulo conforme o tipo de update.
- FR-03: Nenhum workflow aprova ou faz merge.

### 4.2 Non-functional
- —

## 5. Architecture and Design
Configuração declarativa em `.github/dependabot.yml` + workflow `dependabot-metadata.yml` usando `dependabot/fetch-metadata`.

## 6. Implementation Plan
Já implementado. Principais pontos no código:
- `.github/dependabot.yml`
- `.github/workflows/dependabot-metadata.yml`
- `.github/pull_request_template.md`
- `SECURITY.md`

## 7. Acceptance Criteria
- AC-01: PRs agrupados (#7, #8) foram abertos pelo Dependabot.
- AC-02: PRs recebem rótulo `non-major`/`major-update`.
- AC-03: Nenhum PR é mergeado sem ação humana.

## 8. Observability and Operations
- Sem requisitos específicos além dos logs padrão da aplicação.

## 9. Risks and Mitigations
- —

## 10. Rollback
Reverter os commits/PRs listados nas referências.

## 11. References
- PR #6 (build(deps): group dependabot updates and add review workflows) e PR #11
