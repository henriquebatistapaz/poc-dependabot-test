# SDD: dependabot-test

**Complexity:** S

## 1. Context
Precisamos de um repositório privado mínimo para validar o comportamento do Dependabot
(atualizações de versão do Maven e de Actions) antes de habilitar em projetos reais.

## 2. Goal
Ter um projeto Maven de referência com dependências propositalmente antigas e verificar
que o Dependabot abre PRs de atualização para `maven` e `github-actions`.

## 3. Scope
### 3.1 In scope
- `pom.xml` com `commons-lang3` 3.12.0 (desatualizada de propósito).
- `.github/dependabot.yml` com os ecossistemas `maven` e `github-actions`, semanal.
- Workflow de CI com `actions/checkout@v3` (desatualizada de propósito).
- Testes JUnit 5 da classe `Greeter`.

### 3.2 Out of scope
- Dependabot security updates e alertas (exigem configuração no nível do repo).
- Publicação de artefatos.

## 4. Requirements
### 4.1 Functional
- FR-01: `mvn test` passa localmente.
- FR-02: O Dependabot lê `.github/dependabot.yml` e abre PRs de atualização.

### 4.2 Non-functional
- NFR-01: Repositório privado.

## 5. Architecture and Design
Projeto Java 21 / Maven com uma classe utilitária e testes. Sem persistência nem rede.

## 6. Implementation Plan
1. Criar repo privado.
2. Adicionar pom, código, testes, dependabot.yml e CI.
3. Rodar `mvn test`.
4. Publicar na `main` e verificar os PRs do Dependabot.

## 7. Acceptance Criteria
- AC-01: `mvn test` passa.
- AC-02: Dependabot abre pelo menos um PR de Maven (`commons-lang3`).
- AC-03: Dependabot abre pelo menos um PR de `github-actions` (`actions/checkout`).

## 8. Observability and Operations
- Verificar a aba Pull requests e a aba Insights > Dependency graph > Dependabot.

## 9. Risks and Mitigations
- Dependabot pode não abrir PR se o grafo de dependências estiver desabilitado -> habilitar em Settings > Code security.

## 10. Rollback
Apagar o repo de teste.

## 11. References
- Dependabot docs: https://docs.github.com/code-security/dependabot
