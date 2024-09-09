package web.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyDao {

    int save();

}
