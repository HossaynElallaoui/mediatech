package com.estc.mediatech.repositories;

import com.estc.mediatech.models.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {
}
