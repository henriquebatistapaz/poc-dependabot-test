# SDD: qualidade-lombok-enforcer-jacoco

**Complexity:** S

> SDD retroativa: documenta o que já foi entregue (PR #12 (build: add lombok entity, enforcer and jacoco quality checks)). Escrita em 2026-11-06 a partir do código e do histórico do repo; status `Done`.

## 1. Context
Faltavam checagens de qualidade no build para o Dependabot ter plugins reais para atualizar.

## 2. Goal
Adicionar Lombok, maven-enforcer (Java >= 21) e relatório de cobertura JaCoCo.

## 3. Scope
### 3.1 In scope
- Entidade `Pessoa` com `@Value`/`@Builder` e teste.
- maven-enforcer-plugin exigindo Java [21,).
- jacoco-maven-plugin gerando relatório em `target/site/jacoco` (sem mínimo).

### 3.2 Out of scope
- Piso de cobertura obrigatório.

## 4. Requirements
### 4.1 Functional
- FR-01: Build falha em Java < 21.
- FR-02: `mvn verify` gera relatório JaCoCo.

### 4.2 Non-functional
- —

## 5. Architecture and Design
Plugins no `pom.xml`; Lombok como annotation processor no maven-compiler-plugin.

## 6. Implementation Plan
Já implementado. Principais pontos no código:
- `src/main/java/com/example/deptest/Pessoa.java`
- `PessoaTest`
- `pom.xml`

## 7. Acceptance Criteria
- AC-01: `PessoaTest` verde.
- AC-02: Relatório em `target/site/jacoco/index.html` após `mvn verify`.
- AC-03: Enforcer barra JDK < 21.

## 8. Observability and Operations
- Sem requisitos específicos além dos logs padrão da aplicação.

## 9. Risks and Mitigations
- —

## 10. Rollback
Reverter os commits/PRs listados nas referências.

## 11. References
- PR #12 (build: add lombok entity, enforcer and jacoco quality checks)
