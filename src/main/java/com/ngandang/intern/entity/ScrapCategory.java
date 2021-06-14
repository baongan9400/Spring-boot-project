package com.ngandang.intern.entity;

import com.ngandang.intern.common.ECategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScrapCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ECategory name;

    public ScrapCategory(ECategory name) {
        this.name = name;
    }
}
