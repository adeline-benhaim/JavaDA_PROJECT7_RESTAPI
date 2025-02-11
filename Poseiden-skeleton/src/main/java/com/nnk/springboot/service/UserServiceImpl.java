package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.AlreadyExistException;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Create a new user
     *
     * @param userDto new user to save
     * @return user saved
     */
    @Override
    public User createUser(UserDto userDto) {
        logger.info("Create a new user");
        if(userRepository.existsByUsername(userDto.getUsername())){
            logger.error("Cannot create a new user because username : "+ userDto.getUsername() + " already exists");
            throw new AlreadyExistException("Cannot create new user because username : "+ userDto.getUsername() + " already exists");
        }
        User newUser = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fullname(userDto.getFullname())
                .role(userDto.getRole())
                .build();
        logger.info("New user created");
        return userRepository.save(newUser);
    }

    /**
     * Get all users
     *
     * @return a list of all users sorted by id desc
     */
    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Get all users");
        List<User> userList = userRepository.findAllByOrderByIdDesc();
        return userList
                .stream().map(MapperDto::convertToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a user by id
     *
     * @param id of the requested user
     * @return user found by id
     */
    @Override
    public UserDto getUserById(Integer id) {
        logger.info("Get a user by ID");
        if (!userRepository.existsById(id)) {
            logger.error("Unable to get user by id : " + id + " because doesn't exist");
            throw new NotFoundException("User id doesn't exist");
        }
        User user = userRepository.getById(id);
        return MapperDto.convertToUserDto(user);
    }

    /**
     * Update a user
     *
     * @param id user id to update
     * @param userDto user information to update
     * @return user updated
     */
    @Override
    @Transactional
    public User updateUser(Integer id, UserDto userDto) {
        logger.info("Get a user by ID");
        if (!userRepository.existsById(id)) {
            logger.error("Unable to get user by id : " + id + " because doesn't exist");
            throw new NotFoundException("User id doesn't exist");
        }
        User userToUpdate = userRepository.getById(id);
        userToUpdate.setFullname(userDto.getFullname());
        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userToUpdate.setRole(userDto.getRole());
        logger.info("User updated");
        return userRepository.save(userToUpdate);
    }

    /**
     * Delete a user by id
     *
     * @param id ID of user to delete
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        logger.info("Delete user : " + id);
        if (!userRepository.existsById(id)) {
            logger.error("Unable to delete user by id : " + id + " because doesn't exist");
            throw new NotFoundException("User id doesn't exist");
        }
        logger.info("User " + id + " deleted");
        userRepository.deleteById(id);
    }
}
