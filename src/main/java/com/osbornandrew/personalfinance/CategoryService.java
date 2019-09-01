package com.osbornandrew.personalfinance;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public Category findByName(String name){
        return repo.findByName(name);
    }

    public Category save(Category category) {
        return repo.save(category);
    }
}