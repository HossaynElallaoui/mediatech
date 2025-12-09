package com.estc.mediatech.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "factures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String ref;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "client_id") // the FK is here
    private ClientEntity client;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProduitEntity> produits = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<ProduitEntity> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitEntity> produits) {
        this.produits = produits;
    }
}
