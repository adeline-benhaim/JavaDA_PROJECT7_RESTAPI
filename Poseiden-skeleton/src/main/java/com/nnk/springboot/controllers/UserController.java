package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.AlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserRepository userRepository;
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
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                userDto.setPassword(encoder.encode(userDto.getPassword()));
                userService.createUser(userDto);
                model.addAttribute("users", userRepository.findAll());
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
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
