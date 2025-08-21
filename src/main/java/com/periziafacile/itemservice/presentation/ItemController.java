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

import com.periziafacile.itemservice.application.usecase.CreateItem;
import com.periziafacile.itemservice.application.usecase.DeleteItem;
import com.periziafacile.itemservice.application.usecase.GetItem;
import com.periziafacile.itemservice.application.usecase.ListItems;
import com.periziafacile.itemservice.application.usecase.UpdateItem;
import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/items")
@Tag(name = "Item", description = "Gestione catalogo prodotti")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final GetItem getItem;
    private final ListItems listItems;
    private final CreateItem createItem;
    private final UpdateItem updateItem;
    private final DeleteItem deleteItem;

    public ItemController(ItemRepository itemRepository) {
        this.getItem = new GetItem(itemRepository);
        this.listItems = new ListItems(itemRepository);
        this.createItem = new CreateItem(itemRepository);
        this.updateItem = new UpdateItem(itemRepository);
        this.deleteItem = new DeleteItem(itemRepository);
    }

    @Operation(
        summary = "Lista di tutti i prodotti",
        description = "Restituisce tutti i prodotti presenti a catalogo",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista di prodotti",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            )
        }
    )
    @GetMapping
    public List<Item> getAll() {
        log.info("Richiesta lista di tutti gli item");
        return listItems.execute();
    }

    @Operation(
        summary = "Ottieni un prodotto per id",
        description = "Restituisce il prodotto dato il suo id",
        parameters = {
            @Parameter(name = "id", description = "ID del prodotto", example = "1")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Prodotto trovato",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            ),
            @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        log.info("Richiesta item con id={}", id);
        return getItem.execute(id)
                .map(item -> {
                    log.debug("Item trovato: {}", item);
                    return ResponseEntity.ok(item);
                })
                .orElseThrow(() -> {
                    log.warn("Item non trovato con id={}", id);
                    return new NoSuchElementException("Item non trovato con id " + id);
                });
    }

    @Operation(
        summary = "Crea un nuovo prodotto",
        description = "Aggiunge un nuovo prodotto al catalogo",
        requestBody = @RequestBody(
            description = "Dati del nuovo prodotto",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Item.class),
                examples = @ExampleObject(
                    value = "{\"name\": \"Perizia Medica\", \"description\": \"Servizio di Perizia Medica su documentazione.\", \"price\": 89.99}"
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Prodotto creato",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            )
        }
    )
    @PostMapping
    public ResponseEntity<Item> create(
            @org.springframework.web.bind.annotation.RequestBody Item item,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Creazione nuovo item: {}", item);
        Item created = createItem.execute(item);
        URI location = uriBuilder.path("/items/{id}").buildAndExpand(created.getId()).toUri();
        log.info("Item creato con id={}", created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(
        summary = "Aggiorna un prodotto",
        description = "Aggiorna i dati di un prodotto esistente",
        parameters = {
            @Parameter(name = "id", description = "ID del prodotto", example = "1")
        },
        requestBody = @RequestBody(
            description = "Dati aggiornati del prodotto",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Item.class),
                examples = @ExampleObject(
                    value = "{\"name\": \"Perizia Medica On-Site\", \"description\": \"Servizio di Perizia Medica con sopralluogo.\", \"price\": 149.99}"
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Prodotto aggiornato",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            ),
            @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Item> update(
            @PathVariable Long id,
            @org.springframework.web.bind.annotation.RequestBody Item item
    ) {
        log.info("Richiesta aggiornamento item con id={}", id);
        return updateItem.execute(id, item)
                .map(updated -> {
                    log.info("Item aggiornato: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseThrow(() -> {
                    log.warn("Tentativo di aggiornare item non trovato con id={}", id);
                    return new NoSuchElementException("Item non trovato con id " + id);
                });
    }

    @Operation(
        summary = "Cancella un prodotto",
        description = "Elimina un prodotto dal catalogo tramite id",
        parameters = {
            @Parameter(name = "id", description = "ID del prodotto", example = "1")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Prodotto cancellato"),
            @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Richiesta cancellazione item con id={}", id);
        if (getItem.execute(id).isPresent()) {
            deleteItem.execute(id);
            log.info("Item cancellato con id={}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Tentativo di cancellare item non trovato con id={}", id);
            throw new NoSuchElementException("Item non trovato con id " + id);
        }
    }
}