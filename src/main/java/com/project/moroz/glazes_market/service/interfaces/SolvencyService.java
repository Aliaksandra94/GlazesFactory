package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.Solvency;

import java.util.List;

public interface SolvencyService {
    List<Solvency> returnAllSolvencies();
    Solvency returnSolvencyByID(int id);
    Solvency returnSolvencyByName(String name);
}
