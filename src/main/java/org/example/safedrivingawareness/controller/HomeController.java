package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.service.PenaltiesService;
import org.example.safedrivingawareness.service.SpeedLimitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for home page
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final String PENALTY_CATEGORY_ATTRIBUTE = "penaltyCategory";
    private static final String SPEED_LIMITS_ATTRIBUTE = "speedLimits";
    private static final String SPEED_INCREASE_OPTIONS_ATTRIBUTE = "speedIncreaseOptions";
    private final SpeedLimitService speedLimitService;
    private final PenaltiesService penaltiesService;

    /**
     * Get the home Thymeleaf template
     *  
     * @return name of the <i>home</i> Thymeleaf template
     */
    @GetMapping
    public String homeView() {
        return "home";
    }

    /**
     * Update the <i>speedLimitsAccordionBody</i> element, part of the home Thymeleaf template. 
     * This method will perform the following operations: 
     * 1. Calculate the new speed limits based on the given <i>speedValue</i> and update the <i>speedLimits</i> 
     * attribute in the model.
     * 2. Find the penalties for the given increased <i>speedValue</i> and update the <i>penaltyCategory</i>
     * attribute in the model.
     * 
     * @param speedValue increased speed value
     * @param model      the model
     * @return name of the updated fragment
     */
    @GetMapping("/speed-limits")
    public String speedLimitsFragment(@RequestParam Integer speedValue, Model model){
        model.addAttribute(SPEED_LIMITS_ATTRIBUTE, speedLimitService.calculateSpeedLimits(speedValue));
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(speedValue));
        return "home :: #speedLimitsAccordionBody";
    }

    /**
     * Update the <i>penaltiesAccordionBody</i> element part of the home Thymeleaf template. 
     * This method will find the penalties for the given increased <i>speedValue</i> and update the <i>penaltyCategory</i>
     * attribute in the model.
     * 
     * @param speedValue increased speed value
     * @param model      the model
     * @return name of the updated fragment
     */
    @GetMapping("/penalties")
    public String penaltiesFragment(@RequestParam Integer speedValue, Model model) {
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(speedValue));
        return "home :: #penaltiesAccordionBody";
    }

    /**
     * Initialize the model attributes. 
     * 1.<i>speedLimits</i> attribute is initialized by calculating the speed limits without any increase in the speed.
     * 2.<i>speedIncreaseOptions</i> is initialized to ["0", "10", "20", "30", "40", "50"].
     * 3.<i>penaltyCategory</i> is initialized by calculating the penalty category without any incrase in the speed.
     * 
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute(SPEED_LIMITS_ATTRIBUTE, speedLimitService.calculateSpeedLimits(0));
        model.addAttribute(SPEED_INCREASE_OPTIONS_ATTRIBUTE, new String[]{"0", "10", "20", "30", "40", "50"});
        model.addAttribute(PENALTY_CATEGORY_ATTRIBUTE, penaltiesService.findPenaltyCategory(0));
    }
}
