package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.AlreadyExistException;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)

public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init() {
        dataSourceTest.clearUserListMocked();
        dataSourceTest.createUserListMocked();
    }

    @Test
    @DisplayName("Create new user")
    void createUserTest() {

        //GIVEN
        UserDto userDto = UserDto.builder().id(4).username("username4").fullname("fullname4").password("Password4*").role("USER").build();
        Mockito.when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);

        //WHEN
        userService.createUser(userDto);

        //THEN
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Try to create a user already present in database throw AlreadyExistException")
    void createAlreadyExistUserTest() {

        //GIVEN
        UserDto userDto = UserDto.builder().id(1).username("username1").fullname("fullname1").password("Password1*").role("USER").build();
        Mockito.when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        //WHEN

        //THEN
        assertThrows(AlreadyExistException.class, () -> userService.createUser(userDto));    }

    @Test
    @DisplayName("Get all users")
    void getAllUsersTest() {

        //GIVEN
        List<User> userList = dataSourceTest.getUserListMocked();
        Mockito.when(userRepository.findAllByOrderByIdDesc()).thenReturn(userList);

        //THEN
        List<UserDto> userDtoList = userService.getAllUsers();

        //WHEN
        assertEquals(userList.size(), userDtoList.size());
        assertEquals(userList.get(0).getUsername(), userDtoList.get(0).getUsername());
    }

    @Test
    @DisplayName("Get existing user by id")
    void getExistingUserByIdTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        User user = dataSourceTest.getUserListMocked().get(0);
        Mockito.when(userRepository.getById(1)).thenReturn(user);

        //THEN
        UserDto userDto = userService.getUserById(1);

        //WHEN
        assertEquals(user.getUsername(), userDto.getUsername());
    }

    @Test
    @DisplayName("Get unknown user by id throw NotFoundException")
    void getUnknownUserByIdTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> userService.getUserById(5));
    }

    @Test
    @DisplayName("Update an existing user")
    void updateExistingUserTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        User userToUpdate = dataSourceTest.getUserListMocked().get(0);
        Mockito.when(userRepository.getById(1)).thenReturn(userToUpdate);
        UserDto userDto = UserDto.builder().id(1).username("username1Updated").fullname("fullname1Updated").password("Password1*Updated").role("USER").build();

        //THEN
        userService.updateUser(1, userDto);

        //WHEN
        Mockito.verify(userRepository, Mockito.times(1)).save(userToUpdate);
        assertEquals("username1Updated", userToUpdate.getUsername());
    }

    @Test
    @DisplayName("Try to update an unknown user throw NotFoundException")
    void updateUnknownUserTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(5)).thenReturn(false);
        UserDto userDto = UserDto.builder().id(1).username("username1Updated").fullname("fullname1Updated").password("Password1*Updated").role("USER").build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> userService.updateUser(5, userDto));
    }

    @Test
    @DisplayName("Delete a user")
    void deleteUserTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(1)).thenReturn(true);

        //WHEN
        userService.deleteUser(1);

        //THEN
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Try to delete an unknown user throw NotFoundException")
    void deleteUnknownUserTest() {

        //GIVEN
        Mockito.when(userRepository.existsById(5)).thenReturn(false);

        //WHEN

        //THEN
        assertThrows(NotFoundException.class, () -> userService.deleteUser(5));
    }

}
