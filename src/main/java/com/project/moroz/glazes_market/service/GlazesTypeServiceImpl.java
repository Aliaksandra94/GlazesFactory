package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.repository.GlazesTypeDAO;
import com.project.moroz.glazes_market.service.interfaces.GlazesTypeService;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class GlazesTypeServiceImpl implements GlazesTypeService {
    private GlazesTypeDAO glazesTypeDAO;

    @Autowired
    public void setGlazesTypeDAO(GlazesTypeDAO glazesTypeDAO) {
        this.glazesTypeDAO = glazesTypeDAO;
    }

    @Override
    public List<GlazesType> returnAllGlazesType() {
        return glazesTypeDAO.findAll();
    }

    @Override
    public GlazesType returnGlazesTypeByName(String name) {
        return glazesTypeDAO.findByName(name);
    }

    @Override
    public GlazesType returnGlazesTypeById(int id) {
        return glazesTypeDAO.getOne(id);
    }

    @Override
    public void updateComposition(int id, GlazesType glazesType) {
        GlazesType glazesType1 = glazesTypeDAO.getOne(id);
        if(glazesType.getRawMaterialItems().size() == glazesType1.getRawMaterialItems().size()) {
            double cost = 0;
            for (int i = 0; i < glazesType1.getRawMaterialItems().size(); i++){
                glazesType1.getRawMaterialItems().get(i).setQuantity(glazesType.getRawMaterialItems().get(i).getQuantity());
                cost += glazesType1.getRawMaterialItems().get(i).getRawMaterial().getPrice()*glazesType1.getRawMaterialItems().get(i).getQuantity();
            }
            glazesType1.setCost(Precision.round(cost, 2));
        }
        glazesTypeDAO.save(glazesType1);
    }
}
