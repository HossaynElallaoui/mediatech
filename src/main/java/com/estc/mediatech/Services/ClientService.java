package com.estc.mediatech.Services;

import com.estc.mediatech.models.ClientEntity;
import java.util.List;

public interface ClientService {
    ClientEntity create(ClientEntity client);
    ClientEntity update(int id, ClientEntity client);
    void delete(int id);
    ClientEntity getById(int id);
    List<ClientEntity> getAll();
}
