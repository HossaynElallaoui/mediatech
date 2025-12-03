package com.estc.mediatech.Services;

import com.estc.mediatech.models.ProduitEntity;
import com.estc.mediatech.repositories.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository repo;

    public ProduitServiceImpl(ProduitRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProduitEntity create(ProduitEntity produit) {
        return repo.save(produit);
    }

    @Override
    public ProduitEntity update(int id, ProduitEntity produitData) {
        ProduitEntity produit = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));

        produit.setNom(produitData.getNom());
        produit.setPrix(produitData.getPrix());
        produit.setDescription(produitData.getDescription());

        return repo.save(produit);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public ProduitEntity getById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
    }

    @Override
    public List<ProduitEntity> getAll() {
        return repo.findAll();
    }
}
