package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int userId ;
    @NotBlank
    @Size(min = 4 , message = "Username must be min of 4 characters")
    private String name ;
    @Email(message = "Email address is not valid")
    private String email ;
    @NotBlank
    @Size(min = 4 , max = 10 , message = "Password must be min of 4 characters and max of 10 characters")
    private String password ;
    @NotBlank
    private String about ;
    private List<RoleDto> roles = new ArrayList<>();

}
