package com.ngandang.intern.reporitory;

import com.ngandang.intern.common.ECategory;
import com.ngandang.intern.entity.ScrapCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapCategoryRepository extends JpaRepository<ScrapCategory,Integer> {
    ScrapCategory findByName(ECategory name);
}
