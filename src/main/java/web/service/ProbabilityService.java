package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.ProbabilityDao;
import web.model.dto.PointLogDto;

@Service
public class ProbabilityService {
    @Autowired
    ProbabilityDao probabilityDao;


    public PointLogDto probability(String memberid){
        return probabilityDao.probability(memberid);
    }
}
