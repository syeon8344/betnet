package web.security;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;

@Mapper
public interface AuthDao {

    // 로그인시 유저 찾기
    MemberDto findByUsername(String username);

    // 회원가입
    void register(MemberDto memberDto);

}
