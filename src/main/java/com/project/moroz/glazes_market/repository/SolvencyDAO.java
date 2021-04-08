package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Solvency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvencyDAO extends JpaRepository<Solvency, Integer> {
    Solvency findByName(String name);
}
