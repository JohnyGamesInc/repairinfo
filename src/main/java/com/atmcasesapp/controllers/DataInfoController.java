package com.atmcasesapp.controllers;

import com.atmcasesapp.entity.AtmCase;
import com.atmcasesapp.services.AtmCasesService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/info")
public class DataInfoController {
    @Autowired
    private AtmCasesService casesService;

    @GetMapping
    public String showAllDataInfo(Model model,
                                  @RequestParam(value = "t3cause", required = false, defaultValue = "false") Boolean t3cause,
                                  @RequestParam(value = "t3fixTime", required = false, defaultValue = "false") Boolean t3fixTime,
                                  @RequestParam(value = "repeated", required = false, defaultValue = "false") Boolean repeated) {
        var cases = getCasesBasedOnParams(t3cause, t3fixTime, repeated);
        model.addAttribute("cases", cases);
        return "info";
    }

    private Collection<AtmCase> getCasesBasedOnParams(Boolean t3cause,
                                                      Boolean t3fixTime,
                                                      Boolean repeated) {
        if (t3cause) {
            return casesService.findTopThreeCause();
        }
        if (t3fixTime) {
            return casesService.findTopThreeLongestFixes();
        }
        if (repeated) {
            return casesService.findRepeatedCauseCases();
        }
        return casesService.findAllCases();
    }

}
