package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService ;
    }

    // POST - Create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto registerCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(registerCategory, HttpStatus.CREATED);
    }
    // PUT - Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") int cat_id){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,cat_id);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    // DELETE - Delete Category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int cat_id){
        categoryService.deleteCategory(cat_id);
        return new ResponseEntity<>(new ApiResponse(String.format("Category With ID : %d Deleted Successfully",cat_id),true),HttpStatus.OK);
    }
    // GET - Get Category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") int cat_id){
        return new ResponseEntity<>(categoryService.getCategory(cat_id),HttpStatus.OK);
    }
    // GET - Get All Categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
}
