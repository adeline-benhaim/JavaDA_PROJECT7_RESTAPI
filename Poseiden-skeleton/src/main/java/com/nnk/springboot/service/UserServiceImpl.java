package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.AlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

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
                .password(userDto.getPassword())
                .fullname(userDto.getFullname())
                .role(userDto.getRole())
                .build();
        logger.info("New user created");
        return userRepository.save(newUser);
    }

}
