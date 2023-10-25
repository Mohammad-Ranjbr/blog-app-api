package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private int categoryId ;
    @NotBlank
    @Size(min = 4 , max = 50 , message = "Category title must be min of 4 characters and max of 50 characters")
    private String categoryTitle ;
    @NotBlank
    @Size(min = 10 , max = 250 , message = "Category description must be min of 10 characters and max of 250 characters")
    private String categoryDescription ;

}
