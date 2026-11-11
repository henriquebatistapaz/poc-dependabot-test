# SDD: Atualizar poc-dependabot-test para Java 27

**Complexity:** M — Maven sem Spring, mas com instrumentação e duas rotas de CI
**Security-sensitive:** yes — workflows, imagem, Jenkins e integração GitOps

## 1. Contexto

O `pom.xml` usa release 21, Enforcer com piso 21, Lombok e JaCoCo. O Dockerfile e workflows de CI/CD/release usam Temurin 21; o Jenkinsfile chama Maven. Java 27 chegou à disponibilidade geral em 15/09/2026 (build 35) e é a versão estável mais recente em 10/11/2026. É uma versão sem suporte de longo prazo (LTS); acompanhar o prazo de suporte da distribuição e o próximo ciclo de atualização.

## 2. Objetivo

Compilar, testar e executar os componentes Java deste repositório no Java 27, preservando os fluxos e contratos atuais.

## 3. Escopo

### 3.1 Incluído

- `pom.xml`, `Dockerfile`, `Jenkinsfile` e workflows Java de CI/CD/release; verificar também o manifesto de dev em `deploy/k8s`.
- Checagem de compatibilidade, regressão e procedimento de retorno para este repositório.

### 3.2 Fora de escopo

- Mudança de regras de negócio, contratos públicos, dados ou esquema de banco.
- APIs preview, refatorações independentes e publicação de artefatos ou deploy em produção como parte desta migração.

## 4. Requisitos

### 4.1 Funcionais

- RF-01: O Java 27 é usado nas superfícies Java do escopo, com a versão efetiva demonstrada pelo ambiente.
- RF-02: Os fluxos existentes de poc-dependabot-test continuam operando nos mesmos contratos.

### 4.2 Não funcionais

- RNF-01: Usar uma distribuição estável e o patch 27.0.x suportado mais recente disponível para os alvos no momento da implementação. Registrar distribuição, patch e imagens ou instaladores usados como evidência.
- RNF-02: Preservar os gates existentes e comparar o resultado com o baseline de Java 21. Não aceitar regressões nem ignorar verificações para contornar incompatibilidades.

## 5. Arquitetura e desenho

Confirmar suporte do compilador, Enforcer quando usado, processadores, plugins de teste/cobertura e dependências ao JDK e ao bytecode 27. Registrar a menor atualização de dependências que destrave os gates. Alinhar o build, a versão efetiva do JDK no Maven e, quando houver, a JVM da imagem final. Fixar o release de compilação em 27 no POM efetivo; se uma biblioteca precisar publicar bytecode 21 durante a transição, registrar a exceção e verificar o consumidor até concluir a migração coordenada. Se não houver suporte comprovado para um alvo obrigatório, registrar o bloqueio com evidência e manter a configuração anterior nesse alvo até a resolução.

## 6. Plano de implementação

1. Registrar versões e resultados do baseline Java 21 em ambiente isolado.
2. Verificar o suporte ao Java 27 nos componentes do escopo e registrar as versões candidatas.
3. Atualizar somente os pontos identificados na seção 3, incluindo instruções de uso que ainda fixem a versão anterior.
4. Executar as verificações da seção 7 e coordenar a troca com os consumidores ou produtores vinculados, quando houver.

## 7. Critérios de aceite

- AC-01a: `java -version`, `mvn -v` e a versão dentro das imagens, quando aplicáveis, confirmam Java 27; conferir também o release de compilação no POM efetivo (27, ou exceção registrada na §5).
- AC-01b: `mvn --batch-mode --errors clean verify` com JDK 27; build e execução local da imagem; renderizar o overlay dev sem aplicar recursos e executar o job Jenkins localmente com o JDK 27 selecionado, que deve concluir com status SUCCESS e registrar `java -version` 27 no log.
- AC-02: Enforcer reflete a versão mínima definida, JaCoCo gera relatório, testes com Lombok passam e a imagem executa a aplicação de exemplo. As verificações não publicam imagem nem acionam deploy.
- AC-03: Revisar montagem, permissões, destinos de teste e ausência de publicação/exposição adicional nas verificações. A exposição é medida comparando portas, rotas e binds publicados com o baseline Java 21, com a lista anexada ao PR. Os logs e artefatos de verificação não contêm segredos ou dados pessoais reais.
- AC-04: Registrar como voltar para o build e runtime de Java 21 e confirmar que os contratos e dados não exigem conversão para esse retorno.

## 8. Observabilidade e operação

Registrar a versão do JDK e o resultado das verificações no PR de implementação. Quando houver serviço, acompanhar erros de inicialização, saúde e a primeira operação funcional; manter a referência anterior até o smoke passar.

## 9. Riscos e mitigações

- JaCoCo 0.8.11 e Lombok da linha atual precisam de checagem de suporte; atualizar apenas os plugins ou dependências necessários, mantendo os gates ativos.
- Java 27 tem janela de suporte mais curta; registrar quando reavaliar a próxima versão e confirmar o calendário da distribuição antes da promoção.
- Cenários negativos: banco indisponível, dependência fora do ar e entrada malformada devem falhar com o mesmo código e mensagem do baseline Java 21, verificados pelos testes do AC-02.

### 9.1 Ameaças

| Questão | Ameaça | Mitigação | Verificação |
|---|---|---|---|
| Entrada não confiável | Artefato de origem inesperada no build | Fixar versões de ferramentas e confirmar a origem da imagem candidata. | AC-02, AC-03 |
| Autorização e exposição | Publicação ou deploy por uma checagem local | Renderizar manifestos e validar o job de build sem credenciais de publicação. | AC-03 |
| Dados pessoais | Dados reais podem entrar em evidências ou logs de smoke. | Usar apenas fixtures e ambientes isolados; revisar os logs. | AC-03 |
| Segredos | Credenciais podem vazar nos comandos ou artefatos de verificação. | Manter credenciais de teste fora dos arquivos versionados e omitir valores das evidências. | AC-03 |

## 10. Retorno

Reverter o commit da migração e voltar para o build e runtime de Java 21, incluindo toolchain, imagem ou artefato anterior quando aplicável. Nos consumidores, restaurar o conjunto compatível de versões antes de reiniciar e repetir a checagem funcional. Preservar os dados.

## 11. Referências

- Origem: solicitação direta de atualizar os projetos de `github-hp` para a versão estável mais recente.
- [Disponibilidade do Java 27 (Oracle)](https://docs.oracle.com/en-us/iaas/releasenotes/java-management/jdk-27-release-note.htm) e [calendário de suporte Java SE](https://www.oracle.com/technetwork/java/javase/tech/eol-135779.html).
- [Eclipse Temurin 27](https://adoptium.net/news/2026/10/eclipse-temurin-27-available).

## Registro da implementação (2026-11-11)

- JDK: Homebrew OpenJDK `27.0.1.0.3`; Maven `3.10.0`.
- `mvn -B clean verify`: passou, 13 testes, 0 falhas/erros; Enforcer e relatório JaCoCo executaram.
- Compilação do POM efetivo usa release 27. Lombok foi atualizado para `1.18.48`; JaCoCo para `0.8.16`, release que declara suporte oficial a Java 27. Maven Compiler Plugin `3.15.0` e Enforcer `3.6.2` preservam os gates.
- Dockerfile e imagens Jenkins foram atualizados para Maven `3.10.0-eclipse-temurin-27` e runtime `eclipse-temurin:27-jre`; GitHub Actions CI/CD/release usam setup-java Temurin 27.
- A aplicação empacotada foi executada na porta 18080: `/health` retornou `ok`, `/greet?name=codex` retornou `Olá, Codex!`.
- `kubectl kustomize deploy/k8s/overlays/dev` renderizou com sucesso; overlay manteve uma réplica, porta 8080 e a mesma imagem Harbor configurada.
- Não foi possível construir a imagem nem executar o Jenkinsfile localmente: Docker CLI está instalado, mas não há daemon/socket Docker ativo; não foi encontrado Jenkins local. Nenhum manifesto foi aplicado e nenhum artefato foi publicado.
