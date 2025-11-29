package com.estc.mediatech.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="factures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String ref;

    @Column(nullable=false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "client_id")  // the FK is here
    private ClientEntity client;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ProduitEntity> produits = new java.util.ArrayList<>();
}
