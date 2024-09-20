package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.AdminDao;

@Service
public class AdminService {
    @Autowired AdminDao adminDao;

    public boolean cAdmin(String matchid){
        return adminDao.cAdmin(matchid);
    }
}
