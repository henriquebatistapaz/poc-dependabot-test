# SDD Index

The **Status column is the state machine of the cycle** — prompts read it to decide what runs, and `/implement-spec` refuses specs that aren't `Approved`. Keep it accurate.

| Status | Meaning | Who sets it |
|---|---|---|
| `Draft` | Spec written, not approved — may still change | `/new-spec` · `/spec-from-issue` |
| `Approved` | **Gate 1 passed — the human approval act IS changing this cell** (edit it yourself, or tell the agent "approved" and it updates + commits) | **Human only** |
| `In progress` | Being implemented | `/implement-spec` (on start) |
| `Shipped` | MR/PR open, waiting CI + human review (Gate 2) | `/open-mr` |
| `Done` | Merged | `/next` (on detecting the merge) or human |
| `Blocked` | Waiting on something external — say what in Notes | Anyone (state the reason) |

Rules: agents NEVER set `Approved`. A spec that needs rework after approval goes back to `Draft` (approval is re-required). Every spec directory must have a row here — an unlisted spec doesn't exist for the cycle.

| Spec | Issue | Status | Notes |
|------|-------|--------|-------|
| [`20261106-dependabot-test/`](specs/20261106-dependabot-test/spec.md) | — | Done | SDD original: repo de teste do Dependabot (Maven + Actions) |
| [`20261106-grupos-e-revisao-dependabot/`](specs/20261106-grupos-e-revisao-dependabot/spec.md) | — | Done | Agrupamento, agenda, cooldown e rotulagem dos PRs do Dependabot |
| [`20261106-release-e-ci-endurecido/`](specs/20261106-release-e-ci-endurecido/spec.md) | — | Done | Workflow de release e CI com mvn verify |
| [`20261106-qualidade-lombok-enforcer-jacoco/`](specs/20261106-qualidade-lombok-enforcer-jacoco/spec.md) | — | Done | Entidade Lombok, enforcer Java 21 e relatório JaCoCo |
| [`20261106-pipeline-harbor-k8s-argocd/`](specs/20261106-pipeline-harbor-k8s-argocd/spec.md) | — | Done | CI/CD com imagem no Harbor e deploy GitOps (Kustomize + Argo CD), Jenkins equivalente |
| [`20261106-ai-init-adocao-v2/`](specs/20261106-ai-init-adocao-v2/spec.md) | — | In progress | `fc05cc8` + `.ai-init`, workflows e contexto agora versionados na branch local; doc-check, guard `.env` e agent-setup verificados; GitHub CI aguarda publicação e revisão por IA depende de `ANTHROPIC_API_KEY` |
| [`20261110-atualizar-java-27/`](specs/20261110-atualizar-java-27/spec.md) | — | In progress | JDK 27.0.1.0.3; `mvn -B clean verify` passou com 13 testes em 2026-11-11; Docker daemon indisponível impede build da imagem e execução Jenkins via agente Docker |
| [`20261119-padronizar-maven-wrapper/`](specs/20261119-padronizar-maven-wrapper/spec.md) | — | Done | Spec filha da macro 20261119-padronizar-maven-wrapper; wrapper Maven para 1 POM(s), sem alterar JDK ou comportamento. |
