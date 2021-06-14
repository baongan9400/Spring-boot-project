package com.ngandang.intern.controller;

import com.ngandang.intern.entity.Scrap;
import com.ngandang.intern.model.request.RequestScrap;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/scrap")
public class ScrapController {
    @Autowired
    private ScrapService scrapService;

    @GetMapping
    @ResponseBody
    public List<Scrap> allScrap() {
        return scrapService.getAllScrap();
    }
    @GetMapping("/detail")
    @ResponseBody
    public Scrap getScrap(@RequestParam String name) {
        return scrapService.getScrap(name);
    }

    @GetMapping("/{category}")
    @ResponseBody
    public List<Scrap> getScrapByCategory(@PathVariable String category) {
        return scrapService.getScrapByCategory(category);
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody RequestScrap requestScrap){
        return new ResponseTransfer("Add successfully",scrapService.save(requestScrap));
    }

    @PutMapping("/update")
    @ResponseBody
    public Scrap update(@Valid @RequestBody RequestScrap requestScrap) {
            return scrapService.update(requestScrap);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseTransfer delete(@RequestParam Integer id) {
            return new ResponseTransfer("Delete successfully",scrapService.delete(id));
    }
}
