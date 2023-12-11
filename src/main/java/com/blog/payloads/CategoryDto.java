package com.blog.payloads;

import com.blog.config.ApplicationConstants;
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
    @Size(min = 4 , max = 50 , message = ApplicationConstants.CATEGORY_TITLE_SIZE)
    private String categoryTitle ;
    @NotBlank
    @Size(min = 10 , max = 250 , message = ApplicationConstants.CATEGORY_DESCRIPTION_SIZE)
    private String categoryDescription ;

}
