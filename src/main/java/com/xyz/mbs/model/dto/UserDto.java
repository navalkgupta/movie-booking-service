package com.xyz.mbs.model.dto;

import com.xyz.mbs.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String mobile;
    private UserType userType;
}
