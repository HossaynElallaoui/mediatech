package com.estc.mediatech.controllers;

import com.estc.mediatech.models.ClientEntity;

import Services.ClientService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ClientEntity create(@RequestBody ClientEntity client) {
        return service.create(client);
    }

    @GetMapping
    public List<ClientEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ClientEntity getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ClientEntity update(@PathVariable int id, @RequestBody ClientEntity client) {
        return service.update(id, client);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "Client deleted successfully!";
    }
}
