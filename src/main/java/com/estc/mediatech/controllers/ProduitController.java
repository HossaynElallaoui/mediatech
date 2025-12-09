package com.estc.mediatech.controllers;

import com.estc.mediatech.Services.ProduitService;
import com.estc.mediatech.models.ProduitEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService service;

    public ProduitController(ProduitService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProduitEntity create(@RequestBody ProduitEntity produit) {
        return service.create(produit);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<ProduitEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ProduitEntity getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProduitEntity update(@PathVariable int id, @RequestBody ProduitEntity produit) {
        return service.update(id, produit);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Produit deleted successfully!";
    }
}
