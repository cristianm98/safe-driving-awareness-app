package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.service.SpeedLimitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/configurator")
@RequiredArgsConstructor
public class ConfiguratorController {

    private final SpeedLimitService speedLimitService;

    @GetMapping
    public String speedConfiguratorView(Model model) {
        model.addAttribute("breadcrumbs", new String[]{"Configurator", "Router", "Summary"});
        model.addAttribute("speedLimits", speedLimitService.calculateSpeedLimits(0));
        model.addAttribute("speedIncreaseOptions", new String[]{"0", "10", "20", "30", "40", "50"});
        // TODO compute new speed limits and add to model => needs to be done based on input => fragment rendering is needed
        // TODO add penalties depending on speed and add to model
        return "speed-configurator-view";
    }

    @GetMapping("/speed-info")
    public String speedInfoFragment(@RequestParam Integer option, Model model){
        model.addAttribute("speedLimits", speedLimitService.calculateSpeedLimits(option));
        return "speed-configurator-view :: #infoDiv";
    }
}
