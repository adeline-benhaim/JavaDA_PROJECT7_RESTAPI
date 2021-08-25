package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;

public interface UserService {

    /**
     * Create a new user
     *
     * @param userDto new user to save
     * @return user saved
     */
    User createUser(UserDto userDto);
}
