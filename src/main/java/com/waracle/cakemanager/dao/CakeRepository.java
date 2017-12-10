package com.waracle.cakemanager.dao;

import com.waracle.cakemanager.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<CakeEntity, Long> {
    CakeEntity findByTitle(String title);
}
