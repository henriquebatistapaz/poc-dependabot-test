# AGENTS.md — Dependabot Test Mini

Guia operacional para agentes de IA (Copilot, Claude Code, Codex, Cursor etc.).
Contexto detalhado: @README.md, @SECURITY.md, @docs/ONBOARDING.md, @docs/sdd/constitution.md, @docs/wiki/index.md.

## O projeto em uma linha

Repo Java 27 / Maven mínimo (pacote `com.example.deptest`, sem Spring) usado como laboratório de Dependabot, CI/CD com GitHub Actions e Jenkins, imagem no Harbor e deploy GitOps (Kustomize + Argo CD); docs em pt-BR.

## Comandos essenciais

```bash
# Infraestrutura local
Nenhuma.

# Build completo + testes + enforcer + relatório JaCoCo (o mesmo que o CI roda)
mvn --batch-mode --errors verify

# Testes (sempre rodar antes de considerar uma tarefa concluída)
mvn test # doc-check: run

# Formatação / lint
Não configurado. O maven-enforcer-plugin exige Java >= 27.

# Rodar localmente
mvn -q package && java -cp target/classes com.example.deptest.App

# Imagem
docker build -t poc-dependabot-test .

# Manifests (renderizar o overlay dev)
kubectl kustomize deploy/k8s/overlays/dev
```

## Convenções obrigatórias

- Algumas versões são antigas DE PROPÓSITO para o Dependabot ter o que propor; não "conserte" isso fora de um PR do Dependabot.
- `.github/dependabot.yml`: semanal (segunda 09:00, São Paulo), cooldown de 3 dias, Maven minor/patch agrupado, testes em grupo separado, Actions agrupadas, prefixos `build(deps)` e `ci(deps)`.
- O workflow `.github/workflows/dependabot-metadata.yml` só rotula (`non-major`/`major-update`); nunca aprova nem faz merge.
- `.github/workflows/cicd.yml` e `Jenkinsfile` são equivalentes: mudou um, mude o outro. O job do Harbor é pulado quando `vars.HARBOR_REGISTRY` não está configurada.
- GitOps: o pipeline só atualiza a tag da imagem em `deploy/k8s/overlays/dev`; o Argo CD (`deploy/argocd/application-dev.yaml`) sincroniza o cluster.
- Lombok (`@Value`, `@Builder`) para entidades de exemplo (`Pessoa`); testes JUnit 5 em `src/test/java/com/example/deptest`.
- Segredos só em GitHub Secrets / credenciais do Jenkins; nunca em YAML versionado.

## Project skills

Procedimentos recorrentes deste projeto em `.github/skills/`: `review-dependabot-pr`.

## Workflow (Spec-Driven Development)

Plataforma: GitHub (`gh` CLI). Branch de integração: `develop`; `main` recebe releases.

Toda spec nasce em `docs/sdd/specs/<YYYYMMDD>-<slug-kebab>/spec.md` (`<YYYYMMDD>` = data da criação, nunca renomeada; `<slug-kebab>` = nome curto) e é registrada no `docs/sdd/INDEX.md`. Specs antigas no formato `docs/sdd/<nome>/` ficaram como ponteiros de compatibilidade.

O trabalho entra por uma issue (`/spec-from-issue <url>`), um pedido direto, ou um achado de `/tech-debt`/`/retro`/`/metrics` (`/new-spec`) — bug trivial pula a spec (teste falhando → correção na raiz → validar).

A partir da spec, um funil só: revisão `@qa` + aprovação humana → `/test-plan` → `/implement-spec` → `/validate-implementation` (gate: ACs, build, `@reviewer`/`@qa`/`@architect`) → `/open-mr` (branch + PR) → CI → revisão humana → merge.

Veredito NOT READY (ou findings bloqueantes)? `/fix-findings` aplica — na raiz, mantendo a spec em sincronia — e o gate roda de novo. Agentes de revisão apontam; nunca editam.

Atalho: `/next` detecta o estado atual e executa o passo adequado, parando nos gates humanos.

Correções de bug: causa raiz, não sintoma — veja a skill `fix-root-cause` antes de escrever qualquer correção.

1. **Spec primeiro**: toda feature não trivial começa com uma SDD em `docs/sdd/<nome>/spec.md`, seguindo `docs/sdd/TEMPLATE.sdd.md`, registrada em `docs/sdd/INDEX.md`. O que já existia antes do ai-init está documentado em SDDs retroativas com status `Done`.
2. **Princípios**: respeite `docs/sdd/constitution.md` em qualquer plano ou implementação.
3. **Implementação**: siga o plano da SDD; valide contra os critérios de aceite antes de terminar (`/implement-spec`).
4. **Entrega**: `/validate-implementation` precisa retornar READY TO SHIP antes de `/open-mr`.

## Model routing (controle de custo)

Tiers disponíveis: use o seletor de modelos — rápido para edições rotineiras de SDD, padrão por padrão, raciocínio mais forte para design transversal ou sensível a segurança. Sem nomes fixos de modelo.

| Etapa | S | M | L |
|---|---|---|---|
| Escrita de spec / test plan | light | standard | standard |
| Implementação | standard | standard | **heavy** |
| Validação / reviews (@qa @reviewer @architect) | light | standard | **heavy** |
| Fix findings / causa raiz | standard | standard | **heavy** |
| Periódicos (/metrics /tech-debt /retro) | standard | standard | standard |

Escale por RACIOCÍNIO (design, causa raiz, contratos), não por volume; nunca revise abaixo do tier que implementou; na dúvida, um tier acima.

## Armadilhas (aprendidas do jeito difícil)

Problemas do projeto que já custaram uma correção. **Leia antes de planejar ou codar** — se a mudança tocar uma área listada aqui, siga a regra e diga isso no plano. Entradas são adicionadas pelo `/fix-findings` e pelo `/retro`.

<!-- Uma linha cada: - **<área>** — <o que quebra e por quê>. Regra: <o que fazer>. (fonte: <spec ou MR/PR>, <AAAA-MM-DD>) -->
_Nenhuma ainda._

## Não faça

- Não salve specs fora de `docs/sdd/`.
- Não comite segredos ou credenciais (`.env` fica fora do git).
- Não introduza dependências sem justificar na SDD correspondente.
- Não considere uma tarefa concluída com testes falhando ou build quebrado.
- Não deixe `docs/wiki/`, `README.md` ou este arquivo desatualizados quando uma mudança alterar o que descrevem.
- Os arquivos do ai-init (`.ai-init.json`, `AGENTS.md`, `CLAUDE.md`, `.ai-init`, `.github/copilot-instructions.md`, `.github/prompts|agents|instructions|skills`) e os docs do ciclo (`docs/sdd`, `docs/wiki`, `docs/ONBOARDING.md`, `docs/tech-debt`) são links para `../ai-init-internal/<projeto>/` e NÃO são versionados neste repo. Edite normalmente; o commit vai no repo `ai-init-internal`.
- Não aprove nem faça merge automático de PRs do Dependabot por workflow.
- Não habilite CodeQL: exige GitHub Advanced Security em repo privado (foi removido por isso).
