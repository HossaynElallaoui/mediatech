package com.estc.mediatech.Services;

import com.estc.mediatech.models.ProduitEntity;
import java.util.List;

public interface ProduitService {
    ProduitEntity create(ProduitEntity produit);

    ProduitEntity update(int id, ProduitEntity produit);

    void delete(int id);

    ProduitEntity getById(int id);

    List<ProduitEntity> getAll();
}
