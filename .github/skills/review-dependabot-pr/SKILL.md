---
name: review-dependabot-pr
description: Revisar um PR aberto pelo Dependabot no poc-dependabot-test (update de Maven ou GitHub Actions) antes do merge humano.
---

# Revisar um PR do Dependabot

1. Leia o rótulo aplicado por `dependabot-metadata.yml`: `major-update` exige ler o changelog/notas de breaking changes da dependência.
2. Confira se o CI (`ci.yml`, `mvn verify`) está verde; em update de Actions, confira também `cicd.yml` e se o `Jenkinsfile` precisa do mesmo ajuste.
3. Para Maven: rode `mvn --batch-mode --errors verify` localmente na branch do PR se o update for major.
4. Lembre que algumas versões antigas são propositais (README): não faça upgrades manuais fora do PR do Dependabot.
5. Nunca aprove/mergeie por automação — a decisão é humana.
