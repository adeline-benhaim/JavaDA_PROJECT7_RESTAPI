package com.nnk.springboot.domain.Dto;

import com.nnk.springboot.config.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "125 characters maximum allowed")
    private String username;

    @ValidPassword
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "125 characters maximum allowed")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 125, message = "125 characters maximum allowed")
    private String role;
}
