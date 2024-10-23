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
import web.model.dto.MemberDto;


// Spring Security 인증 관련 컨트롤러
@RestController("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security 인증 처리 관리자

    @Autowired
    private AuthService authService;

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
            return ResponseEntity.ok("로그인 성공.");

        } catch (AuthenticationException e) {
            // 인증 실패 시 401 상태 코드와 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패.");
        }
    }

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(MemberDto memberDto) {
        System.out.println("signUp");
        try {
            authService.signUp(memberDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            // 서비스의 signUp 실행중 유효성 검사 오류는 모두 IllegalArgumentException
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }   catch (Exception e) {
            // 다른 예외 처리 (예: 서버 오류)
            return new ResponseEntity<>("회원가입 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 로그아웃은 Spring Security에서 GET /logout, POST /logout 제공
    /*
    If you request POST /logout, then it will perform the following default operations using a series of LogoutHandlers:
        - Invalidate the HTTP session (SecurityContextLogoutHandler)
        - Clear the SecurityContextHolderStrategy (SecurityContextLogoutHandler)
        - Clear the SecurityContextRepository (SecurityContextLogoutHandler)
        - Clean up any RememberMe authentication (TokenRememberMeServices / PersistentTokenRememberMeServices)
        - Clear out any saved CSRF token (CsrfLogoutHandler)
        - Fire a LogoutSuccessEvent (LogoutSuccessEventPublishingLogoutHandler)
    Once completed, then it will exercise its default LogoutSuccessHandler which redirects to /login?logout.
    * */

}
