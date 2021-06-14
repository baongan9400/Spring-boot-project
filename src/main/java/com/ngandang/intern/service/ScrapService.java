package com.ngandang.intern.service;

import com.ngandang.intern.common.ECategory;
import com.ngandang.intern.entity.Scrap;
import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.exception.ExistedRecordException;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.model.request.RequestScrap;
import com.ngandang.intern.reporitory.ScrapCategoryRepository;
import com.ngandang.intern.reporitory.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ScrapService {
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private ScrapCategoryRepository categoryRepository;

    public List<Scrap> getAllScrap() {
        return scrapRepository.findAll();
    }
    public Scrap getScrap(String name) {
        return scrapRepository.findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("Error: Scrap '"+ name+ "' is not found."));
    }

    public Optional<Scrap> getScrapById(Integer id) {
        return scrapRepository.findById(id);
    }
    public Scrap save (RequestScrap requestScrap){
        if(scrapRepository.existsByName(requestScrap.getName().toUpperCase())){
            throw new ExistedRecordException("Scrap '" + requestScrap.getName()+"' is already taken!");
        }
        Scrap newScrap = new Scrap(
                requestScrap.getName(),
                requestScrap.getPrice(),
                getCategory(requestScrap.getCategory().toLowerCase()));

        scrapRepository.save(newScrap);
        return newScrap;
    }
    public Scrap update (RequestScrap requestScrap){
        return scrapRepository.findById(requestScrap.getId()).map(scrap -> {
            scrap.setName(requestScrap.getName());
            scrap.setPrice(requestScrap.getPrice());
            scrap.setCategory(this.getCategory(requestScrap.getCategory().toLowerCase()));
            return scrapRepository.save(scrap);
        }).orElseThrow(() -> new ResourceNotFoundException("Scrap Id " + requestScrap.getId() + " not found"));
    }
    public List<Scrap> getScrapByCategory(String category) {
        return scrapRepository.findByCategoryId(this.getCategory(category).getId());
    }
    public Scrap delete(@RequestParam Integer id) {
        return scrapRepository.findById(id).map(item -> {
            scrapRepository.delete(item);
            return item;
        }).orElseThrow(() -> new ResourceNotFoundException("Scrap Id " + id + " not found"));
    }
    public ScrapCategory getCategory(String name){
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
