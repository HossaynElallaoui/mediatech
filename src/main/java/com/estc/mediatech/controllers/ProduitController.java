package com.estc.mediatech.controllers;

import com.estc.mediatech.Services.ProduitService;
import com.estc.mediatech.models.ProduitEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin("*")
public class ProduitController {

    private final ProduitService service;

    public ProduitController(ProduitService service) {
        this.service = service;
    }

    @PostMapping
    public ProduitEntity create(@RequestBody ProduitEntity produit) {
        return service.create(produit);
    }

    @GetMapping
    public List<ProduitEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProduitEntity getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ProduitEntity update(@PathVariable int id, @RequestBody ProduitEntity produit) {
        return service.update(id, produit);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Produit deleted successfully!";
    }
}
