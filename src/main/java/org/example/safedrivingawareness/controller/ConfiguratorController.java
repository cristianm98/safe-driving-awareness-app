package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.service.PenaltiesService;
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
    private final PenaltiesService penaltiesService;

    @GetMapping
    public String speedConfiguratorView(Model model) {
        model.addAttribute("breadcrumbs", new String[]{"Configurator", "Router", "Summary"});
        model.addAttribute("speedLimits", speedLimitService.calculateSpeedLimits(0));
        model.addAttribute("speedIncreaseOptions", new String[]{"0", "10", "20", "30", "40", "50"});
        model.addAttribute("penaltyCategory", penaltiesService.findPenaltyCategory(0));
        return "speed-configurator-view";
    }

    @GetMapping("/speed-limits")
    public String speedLimitsFragment(@RequestParam Integer speedValue, Model model){
        model.addAttribute("speedLimits", speedLimitService.calculateSpeedLimits(speedValue));
        model.addAttribute("penaltyCategory", penaltiesService.findPenaltyCategory(speedValue));
        return "speed-configurator-view :: #speedLimitsAccordionBody";
    }

    @GetMapping("/penalties")
    public String penaltiesFragment(@RequestParam Integer speedValue, Model model) {
        model.addAttribute("penaltyCategory", penaltiesService.findPenaltyCategory(speedValue));
        return "speed-configurator-view :: #penaltiesAccordionBody";
    }
}
