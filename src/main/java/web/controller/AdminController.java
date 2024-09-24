package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.LogDto;
import web.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/cadmin")
public class AdminController {
    @Autowired AdminService adminService;

    @PutMapping("/update")
    public boolean cAdmin(String matchid){
        System.out.println(matchid);
        return adminService.cAdmin(matchid);
    }
    // 회원 접속 로그
    @GetMapping("/accessLog")
    public List<LogDto> accessLog(){
        System.out.println("AdminController.accessLog");
        return adminService.accessLog();

    }



}
