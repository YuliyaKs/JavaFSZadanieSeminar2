package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
        //return "home.html";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/users";
    }

    // Метод для удаления объекта по ID
    @GetMapping("/user-delete/{id}")
    public String deleteById(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    // Метод для изменения объекта
    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model) {
        List<User> users = new ArrayList<>(userService.findAll());
        User user = null;
        for (User us : users) {
            if (us.getId() == id) {
                user = us;
            }
        }
        model.addAttribute("user", user);
        return "user-update";
    }

    // Метод для обновления объекта
    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

}
