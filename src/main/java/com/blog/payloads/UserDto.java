package com.blog.payloads;

import com.blog.config.ApplicationConstants;
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
    @NotBlank // for String
    @Size(min = 4 , message = ApplicationConstants.USER_NAME_SIZE)
    private String name ;
    @Email(message = ApplicationConstants.USER_EMAIL_ERROR)
    private String email ;
    @NotBlank
    @Size(min = 4 , max = 10 , message = ApplicationConstants.USER_PASSWORD_SIZE)
    private String password ;
    @NotBlank
    private String about ;
    private List<RoleDto> roles = new ArrayList<>();

}
