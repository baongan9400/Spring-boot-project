package com.ngandang.intern.service;

import com.ngandang.intern.common.ECategory;
import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.reporitory.ScrapCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapCategoryService {
    @Autowired
    private ScrapCategoryRepository categoryRepository;
    public List<ScrapCategory> refreshCategory() {
        categoryRepository.deleteAll();
        for (ECategory i : ECategory.values()) {
            categoryRepository.save(new ScrapCategory(i));
        }
        return categoryRepository.findAll();
    }
    public ScrapCategory save(ScrapCategory category) {
        return categoryRepository.save(category);
    }
    public ScrapCategory update(ScrapCategory categoryRequest) {
        return categoryRepository.findById(categoryRequest.getId()).map(category -> {
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryRequest.getId() + " not found"));
    }
    public ScrapCategory delete(Integer id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return category;
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + id + " not found"));
    }
}
