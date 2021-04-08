package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GlazesTypeService {
    List<GlazesType> returnAllGlazesType();

    GlazesType returnGlazesTypeByName(String name);

    GlazesType returnGlazesTypeById(int id);

    void updateComposition(int id, GlazesType glazesType);
}
