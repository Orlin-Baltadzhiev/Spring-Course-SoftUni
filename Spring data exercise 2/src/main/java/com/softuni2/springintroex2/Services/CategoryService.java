package com.softuni2.springintroex2.Services;

import com.softuni2.springintroex2.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    Category getCategoryById(Long id);
}
