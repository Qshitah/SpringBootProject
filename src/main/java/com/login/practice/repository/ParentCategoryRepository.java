package com.login.practice.repository;

import com.login.practice.Entity.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "parentCategories")
public interface ParentCategoryRepository extends JpaRepository<ParentCategory,Integer> {

    ParentCategory findByName(String name);
}
