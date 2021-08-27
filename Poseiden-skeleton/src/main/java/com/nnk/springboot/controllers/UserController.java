package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.exception.AlreadyExistException;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        List<UserDto> userDtoList = userService.getAllUsers();
        model.addAttribute("users", userDtoList);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                userService.createUser(userDto);
                model.addAttribute("users", userService.getAllUsers());
                return "redirect:/user/list";
            } catch (AlreadyExistException e) {
                ObjectError errorUsername = new ObjectError("username", e.getMessage());
                result.addError(errorUsername);
                model.addAttribute("userDto", userDto);
            }
        }
        model.addAttribute("userDto", userDto);
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            UserDto userDtoToUpdate = userService.getUserById(id);
            model.addAttribute("userDto", userDtoToUpdate);
            return "user/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                userService.updateUser(id, userDto);
                return "redirect:/user/list";
            }
            model.addAttribute("userDto", userDto);
            return "user/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }


    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}
