package com.agency.agent.controller;

import com.agency.agent.model.Request;
import com.agency.agent.model.Users;
import com.agency.agent.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/requestAgent")
    public String allAgentRequest(Model model, @AuthenticationPrincipal Users user){
        model.addAttribute("request", requestRepository.findByAgentId(user.getId()));
        return "requestAgent";
    }

    @PostMapping("requestAgent/accept")
    public String postAccept(@RequestParam(required = true, defaultValue = "" ) Long requestId){
        Request request = requestRepository.findById(requestId).get();
        request.setStatus("Подтверждено");
        requestRepository.save(request);
        return "redirect:/requestAgent";
    }

    @PostMapping("requestAgent/inWork")
    public String postInWork(@RequestParam(required = true, defaultValue = "" ) Long requestId){
        Request request = requestRepository.findById(requestId).get();
        request.setStatus("В работе");
        requestRepository.save(request);
        return "redirect:/requestAgent";
    }

    @PostMapping("requestAgent/close")
    public String postClose(@RequestParam(required = true, defaultValue = "" ) Long requestId){
        Request request = requestRepository.findById(requestId).get();
        request.setStatus("Закрыта");
        requestRepository.save(request);
        return "redirect:/requestAgent";
    }

}
