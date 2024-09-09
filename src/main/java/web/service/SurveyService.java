package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.SurveyDao;

@Service
public class SurveyService {

    @Autowired private SurveyDao surveyDao;

    public int save(){
        return surveyDao.save();

    }


}
