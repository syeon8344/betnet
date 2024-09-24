package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.LogDto;

import java.util.List;

@Mapper
public interface AdminDao {

    boolean cAdmin(String matchid);

    // 회원 접속 로그
    List<LogDto> accessLog();



}
