package web.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 로그인 요청 정보를 담는 클래스
public class LoginRequestDto {
    private String username; // 사용자 이름
    private String password; // 비밀번호
}
