package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("breadcrumbs", new String[]{"Speed Configurator", "Router", "Speed"});
        return "home";
    }
}
