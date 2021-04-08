package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Category;
import com.project.moroz.glazes_market.repository.CategoryDAO;
import com.project.moroz.glazes_market.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO;
@Autowired
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> returnAllCategories() {
        return categoryDAO.findAll();
    }
}
