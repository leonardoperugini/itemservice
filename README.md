# ItemService

![Build Status](https://github.com/leonardoperugini/itemservice/actions/workflows/ci.yml/badge.svg)
![CodeQL](https://github.com/leonardoperugini/itemservice/actions/workflows/codeql.yml/badge.svg)
[![codecov](https://codecov.io/gh/leonardoperugini/itemservice/branch/main/graph/badge.svg)](https://codecov.io/gh/leonardoperugini/itemservice)
![Docker Pulls](https://img.shields.io/docker/pulls/leonardoperugini2/itemservice)

---


## Descrizione

**ItemService** è un microservizio Java sviluppato secondo i principi di **Clean Architecture**, **Domain-Driven Design (DDD)** e pratiche **DevOps**.  
Il progetto ha lo scopo di offrire una base solida, scalabile e facilmente manutenibile per la gestione di item (prodotti/oggetti/entità di dominio).

---

## Architettura

Progetto strutturato secondo la Clean Architecture (Robert C. Martin):

```
┌─────────────────────────────┐
│     Presentation Layer      │  (Controller, API, Adapter)
├─────────────────────────────┤
│     Application Layer       │  (UseCase, Service)
├─────────────────────────────┤
│     Domain Layer            │  (Entity, ValueObject, Repository Interface)
├─────────────────────────────┤
│     Infrastructure Layer    │  (Repository Impl, External libs, DB, Framework)
└─────────────────────────────┘
```

- **DDD**: il dominio è centrale. Entità, Value Object, Aggregati, Repository e UseCase sono separati.
- **Port & Adapter**: separazione tra core e interfacce verso l’esterno.

Per approfondire:
- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design](https://domainlanguage.com/ddd/)

---

## Come avviare il progetto

### Prerequisiti
- Java 17+
- Docker (opzionale, per containerizzazione)
- Gradle (se non usi il wrapper)

### Esecuzione locale

```bash
./gradlew bootRun
# Oppure:
./gradlew build
java -jar build/libs/itemservice-*.jar
```

### Esecuzione con Docker

```bash
docker pull leonardoperugini/itemservice:latest
docker run -p 8080:8080 leonardoperugini/itemservice:latest
```

---

## Pipeline CI/CD

- Build & test automatici su GitHub Actions
- Analisi statica con CodeQL
- Build e push Docker automatico su DockerHub
- (Opzionale: Deploy automatico su cloud)

---

## Contribuire

1. Fai un fork del progetto
2. Crea un branch feature: `git checkout -b my-feature`
3. Fai commit delle tue modifiche
4. Apri una Pull Request

**Best practices:**  
- Segui i principi di Clean Architecture e DDD.
- Scrivi test per il nuovo codice.
- Aggiorna la documentazione se serve.

---

## Link utili

- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design Reference](https://domainlanguage.com/ddd/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [DockerHub - ItemService](https://hub.docker.com/r/leonardoperugini2/itemservice)

## Autore

Leonardo Perugini
