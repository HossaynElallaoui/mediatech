package com.estc.mediatech.Services;

import com.estc.mediatech.models.FacturesEntity;
import com.estc.mediatech.repositories.FacturesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturesServiceImpl implements FacturesService {

    private final FacturesRepository repository;

    public FacturesServiceImpl(FacturesRepository repository) {
        this.repository = repository;
    }

    @Override
    public FacturesEntity create(FacturesEntity facture) {
        return repository.save(facture);
    }

    @Override
    public FacturesEntity update(int id, FacturesEntity facture) {
        FacturesEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found with id: " + id));

        existing.setRef(facture.getRef());
        existing.setDate(facture.getDate());
        existing.setClient(facture.getClient());
        existing.setProduits(facture.getProduits());

        return repository.save(existing);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public FacturesEntity getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found with id: " + id));
    }

    @Override
    public List<FacturesEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public List<FacturesEntity> getByClientId(int clientId) {
        return repository.findByClientId(clientId);
    }
}
