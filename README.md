# Dependabot Test Mini

Repo privado para testar o Dependabot (ecossistemas `maven` e `github-actions`).

- `pom.xml` usa `commons-lang3` 3.12.0 de propósito, para o Dependabot ter uma atualização para propor.
- `.github/dependabot.yml` define atualizações semanais.

## Testes

```bash
mvn test
```
