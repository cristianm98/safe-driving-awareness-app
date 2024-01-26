package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.service.PenaltiesService;
import org.example.safedrivingawareness.service.SpeedLimitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final String PENALTY_CATEGORY_ATTRIBUTE = "penaltyCategory";
    private static final String SPEED_LIMITS_ATTRIBUTE = "speedLimits";
    private static final String SPEED_INCREASE_OPTIONS_ATTRIBUTE = "speedIncreaseOptions";
    private final SpeedLimitService speedLimitService;
    private final PenaltiesService penaltiesService;

    @GetMapping
    public String homeView() {
        return "home";
    }

    @GetMapping("/speed-limits")
    public String speedLimitsFragment(@RequestParam Integer speedValue, Model model){
        model.addAttribute(SPEED_LIMITS_ATTRIBUTE, speedLimitService.calculateSpeedLimits(speedValue));
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(speedValue));
        return "home :: #speedLimitsAccordionBody";
    }

    @GetMapping("/penalties")
    public String penaltiesFragment(@RequestParam Integer speedValue, Model model) {
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(speedValue));
        return "home :: #penaltiesAccordionBody";
    }

    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute(SPEED_LIMITS_ATTRIBUTE, speedLimitService.calculateSpeedLimits(0));
        model.addAttribute(SPEED_INCREASE_OPTIONS_ATTRIBUTE, new String[]{"0", "10", "20", "30", "40", "50"});
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(0));
    }
}
