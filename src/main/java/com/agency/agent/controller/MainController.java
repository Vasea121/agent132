package com.agency.agent.controller;

import com.agency.agent.model.Request;
import com.agency.agent.model.Roles;
import com.agency.agent.model.Users;
import com.agency.agent.repository.RequestRepository;
import com.agency.agent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/")
    public String getMainPage(Model model){
        List<Users> roleUser = userRepository.findByRoles("ROLE_AGENT");
        model.addAttribute("allAgent", roleUser);
        return "home";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal Users user){
        model.addAttribute("myProfile", user);
        return "profile";
    }
    @GetMapping("/profile/edit")
    public String editProfile(Model model, @AuthenticationPrincipal Users user){
        model.addAttribute("info", user);
        model.addAttribute("editForm", new Users());
        return "edit";
    }

    @PostMapping("/agentRequest")
    public String agentRequest(@RequestParam(required = true, defaultValue = "" ) Long userId){
        return "redirect:/current_agent/" + userId;
    }

    @GetMapping("/current_agent/{id}")
    public String currentId(@PathVariable("id") Long id, @AuthenticationPrincipal Users user, Model model){
        Users agent = userRepository.findById(id).get();
        model.addAttribute("agent", agent);
        model.addAttribute("request", new Request());
        return "current_agent";
    }

    @PostMapping("/profile/edit")
    public String postEditProfile(@ModelAttribute("editForm") Users editUser, @AuthenticationPrincipal Users user){
        user.setFirst_name(editUser.getFirst_name());
        user.setLast_name(editUser.getLast_name());
        user.setEmail(editUser.getEmail());
        user.setNumber(editUser.getNumber());
        user.setUsername(editUser.getUsername());
        user.setEducation(editUser.getEducation());
        user.setExperience(editUser.getExperience());
        user.setAbout(editUser.getAbout());
        userRepository.save(user);
        return "redirect:/profile/edit";
    }

    @PostMapping("/current_agent/action")
    public String postRequest(@ModelAttribute("request") Request request,
                              @RequestParam (required = true, defaultValue = "" ) Long agentId,
                              @AuthenticationPrincipal Users user){
        request.setAgentId(agentId);
        request.setEmail(user.getEmail());
        request.setNumber(user.getNumber());
        request.setFirst_name(user.getFirst_name());
        request.setLast_name(user.getLast_name());
        request.setUserId(user.getId());
        request.setStatus("Создана");
        requestRepository.save(request);
        return "redirect:/";
    }

    @GetMapping("/currentUser")
    public String getCurrentUser(Model model, @AuthenticationPrincipal Users user){
        List<Request> request = requestRepository.findByUserId(user.getId());
        model.addAttribute("myRequest", request);
        return "current_user";
    }
}

