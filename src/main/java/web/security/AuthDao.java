package web.security;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDao {
    // 로그인시 유저 찾기
    AuthDto findByUsername(String username);

    // 회원가입
    void register(AuthDto authDto);
}
