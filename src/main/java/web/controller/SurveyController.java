package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.SurveyService;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired SurveyService surveyService;

    @PostMapping("/save")
    public int save(){
        return surveyService.save();
    }




}
