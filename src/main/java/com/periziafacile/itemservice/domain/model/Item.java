package com.periziafacile.itemservice.domain.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rappresenta un prodotto/Item nel catalogo.
 */
@Schema(description = "Prodotto nel catalogo")
public class Item {
    /**
     * Identificativo univoco dell'item.
     */
    @Schema(description = "Identificativo univoco dell'item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    /**
     * Nome del prodotto.
     */
    @Schema(description = "Nome del prodotto", example = "Perizia Medica", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    /**
     * Descrizione del prodotto.
     */
    @Schema(description = "Descrizione del prodotto", example = "Servizio di perizia medica su documentazione", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    /**
     * Prezzo del prodotto.
     */
    @Schema(description = "Prezzo del prodotto", example = "89.99", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    /**
     * Ramo di appartenenza del prodotto.
     */
    @Schema(description = "Ramo di appartenenza del prodotto", example = "Veicoli")
    private Ramo ramo;

    public Item() {
    }

    public Item(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Item(Long id, String name, String description, BigDecimal price, Ramo ramo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ramo = ramo;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null)
            throw new NullPointerException("price cannot be null");

        this.price = price;
    }

    public Ramo getRamo() {
        return ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price + '\'' +
                ", ramo='" + ramo + '\'' +
                '}';
    }
}