package com.estc.mediatech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Simple product entity, not linked to other entities yet.
 */
@Entity
@Table(name = "produits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private BigDecimal prix;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private FacturesEntity facture;

    // Explicit getters/setters to avoid relying solely on Lombok during compile
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
