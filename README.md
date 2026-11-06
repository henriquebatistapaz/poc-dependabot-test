# Dependabot Test Mini

Repo privado para testar o Dependabot (ecossistemas `maven` e `github-actions`).

- `pom.xml` usa `commons-lang3` 3.12.0 de propósito, para o Dependabot ter uma atualização para propor.
- `.github/dependabot.yml`:
  - agendamento semanal (segunda, 09:00, São Paulo) com cooldown de 3 dias;
  - updates `minor` e `patch` de Maven agrupados em um PR (dependências de teste em grupo separado);
  - updates de GitHub Actions agrupados em um PR;
  - prefixos de commit `build(deps)` e `ci(deps)`.
- `.github/workflows/dependabot-metadata.yml`: rotula PRs do Dependabot como `non-major` ou `major-update`. Não aprova nem faz merge.

## Testes

```bash
mvn test
```
