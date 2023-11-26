package org.example.safedrivingawareness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.model.AutoCompleteEntry;
import org.example.safedrivingawareness.model.RouteInput;
import org.example.safedrivingawareness.service.GraphHopperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GraphHopperService graphHopperService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("routeInput", new RouteInput());
        model.addAttribute("sampleNumber", "1");
        model.addAttribute("percentage", "");
        AutoCompleteEntry autoCompleteEntry = new AutoCompleteEntry("lat", "long", "description");
        List<AutoCompleteEntry> autoCompleteResult = List.of(autoCompleteEntry);
//        model.addAttribute("autocompleteResult", autoCompleteResult);
        return "home";
    }

    @GetMapping("/autocomplete")
    public String autocompleteInput(@RequestParam String query, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("query", query);
        model.addAttribute("routeInput", new RouteInput());
        AutoCompleteEntry autoCompleteEntry = new AutoCompleteEntry("lat", "long", "description");
        List<AutoCompleteEntry> autoCompleteResult = List.of(autoCompleteEntry);
        model.addAttribute("autocompleteOptions", autoCompleteResult);
        return "home :: #autocompleteResult";
    }
}
