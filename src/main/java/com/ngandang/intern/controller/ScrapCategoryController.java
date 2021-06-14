package com.ngandang.intern.controller;

import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.reporitory.ScrapCategoryRepository;
import com.ngandang.intern.service.ScrapCategoryService;
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

    @Autowired
    private ScrapCategoryService categoryService;

    @GetMapping
    @ResponseBody
    public List<ScrapCategory> getAllCategory() {
        return categoryRepository.findAll();
    }

    @PostMapping("/refresh")
    @ResponseBody
    public List<ScrapCategory> refreshCategory() {
        return categoryService.refreshCategory();
    }

    @PostMapping("/add")
    @ResponseBody
    public ScrapCategory addCategory(@Valid @RequestBody ScrapCategory category) {
        return categoryService.save(category);
    }

    @PutMapping("/update")
    @ResponseBody
    public ScrapCategory updateCategory(@Valid @RequestBody ScrapCategory categoryRequest) {
        return categoryService.update(categoryRequest);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseTransfer deleteCategory(@RequestParam Integer id) {
        return new ResponseTransfer("Delete successfully", categoryService.delete(id));
    }
}
