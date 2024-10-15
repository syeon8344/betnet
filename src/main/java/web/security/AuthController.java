package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// Spring Security 인증 관련 컨트롤러
@RestController("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security 인증 처리 관리자

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            // 사용자 이름과 비밀번호를 사용하여 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            // 인증 처리 관리자에게 인증 시도
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증 성공 시 추가 처리 (예: JWT 생성)
            return ResponseEntity.ok("Login successful");

        } catch (AuthenticationException e) {
            // 인증 실패 시 401 상태 코드와 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패.");
        }
    }
    
}
