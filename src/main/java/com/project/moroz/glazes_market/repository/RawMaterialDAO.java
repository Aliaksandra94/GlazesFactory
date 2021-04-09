package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialDAO extends JpaRepository<RawMaterial, Integer> {
    List<RawMaterial> findByCategoryId(int categoryId);
    RawMaterial findByRawMaterialID(int id);
}
