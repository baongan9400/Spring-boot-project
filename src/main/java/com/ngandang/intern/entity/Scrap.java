package com.ngandang.intern.entity;

import javax.persistence.*;
@Entity
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ScrapCategory category;

    public Scrap() {
    }
    public Scrap(String name, Integer price,ScrapCategory category) {
        this.name = name;
        this.price = price;
        this.category =category;
    }

    public ScrapCategory getCategory() {
        return category;
    }

    public void setCategory(ScrapCategory category) {
        this.category = category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price ;
    }
}

