package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.PointLogDto;

@Mapper
public interface ProbabilityDao {
    PointLogDto probability(String memberid);

}
