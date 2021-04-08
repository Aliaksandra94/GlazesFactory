package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
