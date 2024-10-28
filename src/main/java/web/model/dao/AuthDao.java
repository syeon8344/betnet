package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;

import java.util.Optional;

@Mapper
public interface AuthDao {

    // 로그인시 유저 찾기
    MemberDto findByUsername(String username);

    // 회원가입
    void register(MemberDto memberDto);

    // OAuth2 이메일로 유저 찾기
    MemberDto findByEmail(String email);
    // TODO: mapper xml
    boolean checkFirstOAuth2User(int age);
}
