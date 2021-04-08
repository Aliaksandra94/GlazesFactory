package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Solvency;
import com.project.moroz.glazes_market.repository.SolvencyDAO;
import com.project.moroz.glazes_market.service.interfaces.SolvencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolvencyServiceImpl implements SolvencyService {
    private SolvencyDAO solvencyDAO;

    @Autowired
    public void setSolvencyDAO(SolvencyDAO solvencyDAO) {
        this.solvencyDAO = solvencyDAO;
    }

    @Override
    public List<Solvency> returnAllSolvencies() {
        return solvencyDAO.findAll();
    }

    @Override
    public Solvency returnSolvencyByID(int id) {
        return solvencyDAO.getOne(id);
    }

    @Override
    public Solvency returnSolvencyByName(String name) {
        return solvencyDAO.findByName(name);
    }
}
