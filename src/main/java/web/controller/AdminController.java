package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.AdminService;

@RestController
@RequestMapping("/cadmin")
public class AdminController {
    @Autowired AdminService adminService;

    @PutMapping("/update")
    public boolean cAdmin(String matchid){
        System.out.println(matchid);
        return adminService.cAdmin(matchid);
    }




}
