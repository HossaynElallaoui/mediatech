package com.estc.mediatech.controllers;

import com.estc.mediatech.Services.ClientService;
import com.estc.mediatech.models.ClientEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientEntity create(@RequestBody ClientEntity client) {
        return service.create(client);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ClientEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ClientEntity getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientEntity update(@PathVariable int id, @RequestBody ClientEntity client) {
        return service.update(id, client);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Client deleted successfully!";
    }
}
