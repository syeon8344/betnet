package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.PointLogDto;
import web.service.ProbabilityService;

@RestController
@RequestMapping("/prob")
public class ProbabilityController {
    @Autowired ProbabilityService probabilityService;


    @GetMapping("/bility")
    public PointLogDto probability(String memberid){
        return probabilityService.probability(memberid);
    }



}
