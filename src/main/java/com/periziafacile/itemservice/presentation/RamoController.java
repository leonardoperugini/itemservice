package com.periziafacile.itemservice.presentation;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.periziafacile.itemservice.application.usecase.ramo.CreateRamo;
import com.periziafacile.itemservice.application.usecase.ramo.DeleteRamo;
import com.periziafacile.itemservice.application.usecase.ramo.GetRamo;
import com.periziafacile.itemservice.application.usecase.ramo.ListRami;
import com.periziafacile.itemservice.application.usecase.ramo.UpdateRamo;
import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rami")
@Tag(name = "Rami", description = "Gestione rami dei prodotti")
public class RamoController {
    private static final Logger log = LoggerFactory.getLogger(RamoController.class);
    private final GetRamo getRamo;
    private final ListRami listRami;
    private final CreateRamo createRamo;
    private final UpdateRamo updateRamo;
    private final DeleteRamo deleteRamo;

    public RamoController(RamoRepository ramoRepository) {
        this.getRamo = new GetRamo(ramoRepository);
        this.listRami = new ListRami(ramoRepository);
        this.createRamo = new CreateRamo(ramoRepository);
        this.updateRamo = new UpdateRamo(ramoRepository);
        this.deleteRamo = new DeleteRamo(ramoRepository);
    }

    @Operation(summary = "Lista di tutti i rami", description = "Restituisce rami dei prodotti presenti a catalogo", responses = {
            @ApiResponse(responseCode = "200", description = "Lista di rami", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ramo.class)))
    })
    @GetMapping
    public List<Ramo> getAll() {
        log.info("Richiesta lista di tutti i rami");
        return listRami.execute();
    }

    @Operation(summary = "Ottieni un ramo per id", description = "Restituisce il ramo dato il suo id", parameters = {
            @Parameter(name = "id", description = "ID del ramo", example = "1")
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Ramo trovato", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ramo.class))),
            @ApiResponse(responseCode = "404", description = "Ramo non trovato")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ramo> getById(@PathVariable Long id) {
        log.info("Richiesta ramo con id={}", id);
        return getRamo.execute(id)
                .map(item -> {
                    log.debug("Ramo trovato: {}", item);
                    return ResponseEntity.ok(item);
                })
                .orElseThrow(() -> {
                    log.warn("Ramo non trovato con id={}", id);
                    return new NoSuchElementException("Ramo non trovato con id " + id);
                });
    }

    @Operation(summary = "Crea un nuovo ramo", description = "Aggiunge un nuovo ramo al catalogo", requestBody = @RequestBody(description = "Dati del nuovo ramo", required = true, content = @Content(schema = @Schema(implementation = Ramo.class), examples = @ExampleObject(value = "{\"name\": \"Ramo Vita\", \"description\": \"Ramo assicurativo vita.\"}"))), responses = {
            @ApiResponse(responseCode = "201", description = "Ramo creato", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ramo.class)))
    })
    @PostMapping
    public ResponseEntity<Ramo> create(
            @org.springframework.web.bind.annotation.RequestBody Ramo ramo,
            UriComponentsBuilder uriBuilder) {
        log.info("Creazione nuovo ramo: {}", ramo);
        Ramo created = createRamo.execute(ramo);
        URI location = uriBuilder.path("/rami/{id}").buildAndExpand(created.getId()).toUri();
        log.info("Ramo creato con id={}", created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Aggiorna un ramo", description = "Aggiorna i dati di un ramo esistente", parameters = {
            @Parameter(name = "id", description = "ID del ramo", example = "1")
    }, requestBody = @RequestBody(description = "Dati aggiornati del ramo", required = true, content = @Content(schema = @Schema(implementation = Ramo.class), examples = @ExampleObject(value = "{\"name\": \"Ramo Vita\", \"description\": \"Ramo assicurativo vita.\"}"))), responses = {
            @ApiResponse(responseCode = "200", description = "Ramo aggiornato", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ramo.class))),
            @ApiResponse(responseCode = "404", description = "Ramo non trovato")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ramo> update(
            @PathVariable Long id,
            @org.springframework.web.bind.annotation.RequestBody Ramo ramo) {
        log.info("Richiesta aggiornamento ramo con id={}", id);
        return updateRamo.execute(id, ramo)
                .map(updated -> {
                    log.info("Ramo aggiornato: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseThrow(() -> {
                    log.warn("Tentativo di aggiornare ramo non trovato con id={}", id);
                    return new NoSuchElementException("Ramo non trovato con id " + id);
                });
    }

    @Operation(summary = "Cancella un ramo", description = "Elimina un ramo dal catalogo tramite id", parameters = {
            @Parameter(name = "id", description = "ID del ramo", example = "1")
    }, responses = {
            @ApiResponse(responseCode = "204", description = "Ramo cancellato"),
            @ApiResponse(responseCode = "404", description = "Ramo non trovato")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Richiesta cancellazione ramo con id={}", id);
        if (getRamo.execute(id).isPresent()) {
            deleteRamo.execute(id);
            log.info("Ramo cancellato con id={}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Tentativo di cancellare ramo non trovato con id={}", id);
            throw new NoSuchElementException("Ramo non trovato con id " + id);
        }
    }

}
