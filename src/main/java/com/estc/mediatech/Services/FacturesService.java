package com.estc.mediatech.Services;

import com.estc.mediatech.models.FacturesEntity;
import java.util.List;

public interface FacturesService {
    FacturesEntity create(FacturesEntity facture);

    FacturesEntity update(int id, FacturesEntity facture);

    void delete(int id);

    FacturesEntity getById(int id);

    List<FacturesEntity> getAll();

    List<FacturesEntity> getByClientId(int clientId);
}
