package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName ;
    private String fieldName ;
    private String fieldValue ;

    public ResourceNotFoundException(String resourceName, String filedName, String fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,filedName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = filedName;
        this.fieldValue = fieldValue;
    }

}
