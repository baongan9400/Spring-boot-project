package com.ngandang.intern.reporitory;

import com.ngandang.intern.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Integer> {
    List<Scrap> findAll();
    Optional<Scrap> findById(Integer id);
    Optional<Scrap> findByName(String name);
    List<Scrap> findByCategoryId(Integer categoryId);
    Boolean existsByName(String name);
}
