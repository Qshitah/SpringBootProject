package com.login.practice.repository;

import com.login.practice.Entity.Category;
import com.login.practice.Entity.ParentCategory;
import com.login.practice.signUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByNameAndParentCategory_Name(String name, String parentCategory_name);
    List<Category> findByParentCategory_Id(int parentId);

}
