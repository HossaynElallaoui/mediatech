package com.estc.mediatech.repositories;

import com.estc.mediatech.models.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {}
