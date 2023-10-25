package com.blog.services.Implementation;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository ;
    private final ModelMapper modelMapper ;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository , ModelMapper modelMapper){
        this.categoryRepository = categoryRepository ;
        this.modelMapper = modelMapper ;
    }
    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return this.categoryToCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(categoryId)));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(category);
        return this.categoryToCategoryDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(categoryId)));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(categoryId)));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::categoryToCategoryDto).collect(Collectors.toList());
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto,Category.class);
    }

    public CategoryDto categoryToCategoryDto(Category category){
        return modelMapper.map(category,CategoryDto.class);
    }

}
