# Dependabot

> Configuração e regras de revisão dos PRs automáticos.

## Business rules

- Semanal, segunda 09:00 (America/Sao_Paulo), cooldown de 3 dias.
- Maven minor+patch num PR; dependências de teste em grupo separado; Actions num PR.
- Rótulo `major-update` pede revisão humana cuidadosa; `non-major` ainda passa pelo CI.

## Related

- `docs/sdd/specs/20261106-dependabot-test/spec.md`, `docs/sdd/specs/20261106-grupos-e-revisao-dependabot/spec.md`
