package com.ngandang.intern.controller;

import com.ngandang.intern.common.ECategory;
import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.reporitory.ScrapCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/category")
public class ScrapCategoryController {
    @Autowired
    private ScrapCategoryRepository categoryRepository;

    @GetMapping
    @ResponseBody
    public List<ScrapCategory> getAllCategory() {
        return categoryRepository.findAll();
    }

    @PostMapping("/refresh")
    @ResponseBody
    public List<ScrapCategory> refreshCategory() {
        categoryRepository.deleteAll();
        for (ECategory i : ECategory.values()) {
            categoryRepository.save(new ScrapCategory(i));
            }
        return categoryRepository.findAll();
    }

    @PostMapping("/add")
    @ResponseBody
    public ScrapCategory addCategory(@Valid @RequestBody ScrapCategory category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/update")
    @ResponseBody
    public ScrapCategory updateCategory(@Valid @RequestBody ScrapCategory categoryRequest) {
        return categoryRepository.findById(categoryRequest.getId()).map(category -> {
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryRequest.getId() + " not found"));
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseTransfer deletePost(@RequestParam Integer id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return new ResponseTransfer("Delete successfully", category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + id + " not found"));
    }
}
