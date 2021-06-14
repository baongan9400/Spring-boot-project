package com.ngandang.intern.entity;

import com.ngandang.intern.model.AuditModel;
import lombok.*;

import javax.persistence.*;
@Entity
@Getter @Setter
@NoArgsConstructor
public class Scrap extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ScrapCategory category;

    public Scrap(String name, Integer price,ScrapCategory category) {
        this.name = name;
        this.price = price;
        this.category =category;
    }

}

