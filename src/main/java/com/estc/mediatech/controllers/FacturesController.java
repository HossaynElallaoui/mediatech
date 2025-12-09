package com.estc.mediatech.controllers;

import com.estc.mediatech.Services.FacturesService;
import com.estc.mediatech.models.FacturesEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FacturesController {

    private final FacturesService service;

    public FacturesController(FacturesService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public FacturesEntity create(@RequestBody FacturesEntity facture) {
        return service.create(facture);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<FacturesEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public FacturesEntity getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<FacturesEntity> getByClientId(@PathVariable int clientId) {
        return service.getByClientId(clientId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public FacturesEntity update(@PathVariable int id, @RequestBody FacturesEntity facture) {
        return service.update(id, facture);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Facture deleted successfully!";
    }
}
