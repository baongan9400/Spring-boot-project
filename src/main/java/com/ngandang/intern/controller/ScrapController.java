package com.ngandang.intern.controller;

import com.ngandang.intern.common.ECategory;
import com.ngandang.intern.entity.Scrap;
import com.ngandang.intern.model.request.RequestScrap;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.exception.ExistedRecordException;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.reporitory.ScrapCategoryRepository;
import com.ngandang.intern.reporitory.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/scrap")
public class ScrapController {
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private ScrapCategoryRepository categoryRepository;

    @GetMapping
    @ResponseBody
    public List<Scrap> getAllScrap() {
        return scrapRepository.findAll();
    }
    @GetMapping("/detail")
    @ResponseBody
    public Scrap getScrap(@RequestParam String name) {
        return scrapRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Scrap '"+ name+ "' is not found."));
    }

    @GetMapping("/{category}")
    @ResponseBody
    public List<Scrap> getScrapByCategory(@PathVariable String category) {
        return scrapRepository.findByCategoryId(this.getCategory(category).getId());
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody RequestScrap requestScrap)
    {
        if(scrapRepository.existsByName(requestScrap.getName().toUpperCase())){
            throw new ExistedRecordException("Scrap '" + requestScrap.getName()+"' is already taken!");
        }
        Scrap newScrap = new Scrap(
                requestScrap.getName(),
                requestScrap.getPrice(),
                getCategory(requestScrap.getCategory().toLowerCase()));

        scrapRepository.save(newScrap);
        return new ResponseTransfer("Add successfully",newScrap);
    }
    @PutMapping("/update")
    @ResponseBody
    public Scrap update(@Valid @RequestBody RequestScrap requestScrap,@RequestParam Integer id) {
        return scrapRepository.findById(id).map(scrap -> {
            scrap.setName(requestScrap.getName());
            scrap.setPrice(requestScrap.getPrice());
            scrap.setCategory(this.getCategory(requestScrap.getCategory().toLowerCase()));
            return scrapRepository.save(scrap);
        }).orElseThrow(() -> new ResourceNotFoundException("ScrapId " + id + " not found"));
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseTransfer delete(@RequestParam Integer id) {
        return scrapRepository.findById(id).map(item -> {
            scrapRepository.delete(item);
            return new ResponseTransfer("Delete successfully",item);
        }).orElseThrow(() -> new ResourceNotFoundException("ScrapId " + id + " not found"));
    }

    private ScrapCategory getCategory(String name){
        switch (name.toLowerCase()){
            case "paper":
                return categoryRepository.findByName(ECategory.PAPER);
            case "fabric":
                return categoryRepository.findByName(ECategory.FABRIC);
            case "plastic":
                return categoryRepository.findByName(ECategory.PLASTIC);
            default:
                return categoryRepository.findByName(ECategory.METAL);
        }
    }


}
