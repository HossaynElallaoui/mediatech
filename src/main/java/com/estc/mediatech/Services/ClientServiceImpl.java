package com.estc.mediatech.Services;

import com.estc.mediatech.models.ClientEntity;
import com.estc.mediatech.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repo;

    public ClientServiceImpl(ClientRepository repo) {
        this.repo = repo;
    }

    @Override
    public ClientEntity create(ClientEntity client) {
        return repo.save(client);
    }

    @Override
    public ClientEntity update(int id, ClientEntity clientData) {
        ClientEntity client = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setNom(clientData.getNom());
        client.setPrenom(clientData.getPrenom());
        client.setTelephone(clientData.getTelephone());

        return repo.save(client);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public ClientEntity getById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<ClientEntity> getAll() {
        return repo.findAll();
    }
}
