package com.ngandang.intern.entity;

import com.ngandang.intern.common.ECategory;
import javax.persistence.*;

@Entity
public class ScrapCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ECategory name;

    public ScrapCategory() {
    }

    public ScrapCategory(ECategory name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ECategory getName() {
        return name;
    }

    public void setName(ECategory name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ScrapCategory{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
