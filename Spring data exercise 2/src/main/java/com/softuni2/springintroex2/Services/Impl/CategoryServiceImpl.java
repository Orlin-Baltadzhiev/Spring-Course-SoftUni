package com.softuni2.springintroex2.Services.Impl;

import com.softuni2.springintroex2.Constants.GlobalConstants;
import com.softuni2.springintroex2.Repositories.CategoryRepository;
import com.softuni2.springintroex2.Services.CategoryService;
import com.softuni2.springintroex2.Utils.FileUtil;
import com.softuni2.springintroex2.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static com.softuni2.springintroex2.Constants.GlobalConstants.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedCategories() throws IOException {
        if(this.categoryRepository.count() != 0){
            return;
        }

        String [] fileContent = this.fileUtil
                .readFileContent(CATEGORIES_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach( r ->{
            Category category = new Category(r);
            this.categoryRepository.saveAndFlush(category);
                });
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.getOne(id);
    }
}
