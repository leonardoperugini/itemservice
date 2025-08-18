# ItemService

**ItemService** è un microservizio Spring Boot per la gestione di un catalogo prodotti ("Item") pensato per la piattaforma PeriziaFacile.

## Funzionalità

- API RESTful per la gestione degli item (CRUD)
- Documentazione OpenAPI/Swagger (springdoc-openapi)
- Test unitari con JUnit 5 e Mockito
- Pronto per essere integrato in architetture a microservizi

## Requisiti

- Java 17+
- Gradle 8+
- (Facoltativo) Docker

## Come avviare il progetto

```bash
./gradlew bootRun
```

L'applicazione partirà di default su [http://localhost:8080](http://localhost:8080).

## API Documentation

Una volta avviato il servizio, la documentazione interattiva disponibile su:

```
http://localhost:8080/swagger-ui.html
```

## Esempi di endpoint

- `GET /items` - Recupera tutti gli item
- `GET /items/{id}` - Recupera un item per ID
- `POST /items` - Crea un nuovo item
- `PUT /items/{id}` - Aggiorna un item esistente
- `DELETE /items/{id}` - Elimina un item

## Eseguire i test

```bash
./gradlew test
```

## Struttura del progetto

```
src/
  main/
    java/
      com.periziafacile.itemservice/
        application/    # Servizi applicativi
        domain/         # Modelli e interfacce dominio
        presentation/   # Controller REST
    resources/
  test/
    java/
      com.periziafacile.itemservice/
        ItemServiceTest.java
        ...
```

## Tecnologie principali

- Spring Boot 3.5.x
- Spring Web
- Springdoc OpenAPI
- JUnit 5
- Mockito
- Gradle

## Autore

Leonardo Perugini
