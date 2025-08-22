package com.periziafacile.itemservice.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rappresenta la tipologia di un prodotto/Item nel catalogo.
 */
@Schema(description = "Ramo (tipologia) del prodotto nel catalogo")
public class Ramo {
    /**
     * Identificativo univoco del ramo.
     */
    @Schema(description = "Identificativo univoco del ramo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    /**
     * Nome del ramo.
     */
    @Schema(description = "Nome del ramo", example = "Veicoli", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    /**
     * Descrizione del ramo.
     */
    @Schema(description = "Descrizione del ramo", example = "Tipologie di servizi relativi a veicoli", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    public Ramo() {
    }

    public Ramo(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name cannot be null");

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException("description cannot be null");

        this.description = description;
    }

    @Override
    public String toString() {
        return "Ramo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}