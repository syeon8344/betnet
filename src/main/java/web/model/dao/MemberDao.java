package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;

@Mapper
public interface MemberDao {
    //09.09 회원가입
    boolean signUp(MemberDto memberDto);
}
