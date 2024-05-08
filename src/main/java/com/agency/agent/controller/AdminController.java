package com.agency.agent.controller;

import com.agency.agent.model.Roles;
import com.agency.agent.model.Users;
import com.agency.agent.repository.UserRepository;
import com.agency.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository usersRepository;

    @GetMapping("/admin")
    public String userList(@AuthenticationPrincipal Users user,
                           Model model) {
        model.addAttribute("allUsers", usersRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             Model model) {
        Users delete_user = userService.get(userId);
        Set<Roles> roles = delete_user.getRoles();
        if (userService.checkRole(delete_user, roles)) {
            model.addAttribute("delete_error", "Администратор не может удалить администратора!");
            model.addAttribute("allUsers", usersRepository.findAll());
            return "admin";
        } else {
            usersRepository.deleteById(delete_user.getId());
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/giveAgent")
    public String giveAgent(@RequestParam(required = true, defaultValue = "") Long userId,
                            Model model) {
        Users given_user = userService.get(userId);
        given_user.getRoles().add(new Roles(2L, "ROLE_AGENT"));
        usersRepository.save(given_user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteAgent")
    public String deleteAgent(@AuthenticationPrincipal Users user,
                              @RequestParam(required = true, defaultValue = "") Long userId,
                              Model model) {
        Users delete_operator = userService.get(userId);
        Set<Roles> roles = delete_operator.getRoles();
        if (userService.checkRole(delete_operator, roles)) {
            model.addAttribute("delete_error", "Администратор не может удалить администратора!");
            model.addAttribute("allUsers", usersRepository.findAll());
            return "admin";
        } else {
            delete_operator.getRoles().clear();
            delete_operator.getRoles().add(new Roles(1L, "ROLE_USER"));
            usersRepository.save(delete_operator);
            return "redirect:/admin";
        }
    }
}
