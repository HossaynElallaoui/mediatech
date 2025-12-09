package com.estc.mediatech.repositories;

import com.estc.mediatech.models.FacturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturesRepository extends JpaRepository<FacturesEntity, Integer> {
    List<FacturesEntity> findByClientId(int clientId);
}
