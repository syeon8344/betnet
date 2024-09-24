package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.AdminDao;
import web.model.dto.LogDto;

import java.util.List;

@Service
public class AdminService {
    @Autowired AdminDao adminDao;

    public boolean cAdmin(String matchid){
        return adminDao.cAdmin(matchid);
    }
    // 회원 접속 로그
    public List<LogDto> accessLog(){
        System.out.println("AdminService.accessLog");
        return adminDao.accessLog();
    }

}
