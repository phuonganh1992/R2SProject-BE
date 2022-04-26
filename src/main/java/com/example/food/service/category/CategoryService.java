package com.example.food.service.category;

import com.example.food.advice.CommonException;
import com.example.food.domain.Category;
import com.example.food.domain.Merchant;
import com.example.food.dto.view.Response;
import com.example.food.repository.ICategoryRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> findAll() {

        Iterable<Category> categoryIterable = categoryRepository.findAll();
        List<Category> categories = IterableUtils.toList(categoryIterable);

        if(categories.isEmpty()){
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        return categories;
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean isExistsCategory(String name) {
        return categoryRepository.existsCategoryByName(name);
    }
}
