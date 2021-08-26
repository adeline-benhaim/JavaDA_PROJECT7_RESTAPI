package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

    /**
     * Create a new user
     *
     * @param userDto new user to save
     * @return user saved
     */
    User createUser(UserDto userDto);

    /**
     * Get all users
     *
     * @return a list of all users sorted by id desc
     */
    List<UserDto> getAllUsers();

    /**
     * Get a user by id
     *
     * @param id of the requested user
     * @return user found by id
     */
    UserDto getUserById(Integer id);

    /**
     * Update a user
     *
     * @param id user id to update
     * @param userDto user information to update
     * @return user updated
     */
    User updateUser(Integer id, UserDto userDto);

    /**
     * Delete a user by id
     *
     * @param id ID of user to delete
     */
    void deleteUser(Integer id);
}
