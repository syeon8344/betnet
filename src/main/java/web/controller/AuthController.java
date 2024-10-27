package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MemberDto;
import web.service.AuthService;

import java.util.Collections;


// Spring Security 인증 관련 컨트롤러
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security 인증 처리 관리자

    @Autowired
    private UserDetailsService userDetailsService; // Spring Security

    @Autowired
    private AuthService authService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(MemberDto memberDto) {
        try {
            authService.signUp(memberDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            // 서비스의 signUp 실행중 유효성 검사 오류는 모두 IllegalArgumentException 에외 발생
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 다른 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "회원가입 중 오류 발생."));
        }
    }

    // 로그인 여부
    @GetMapping("/status")
    public ResponseEntity<?> getAuthenticationStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Check if the principal is an instance of AnonymousAuthenticationToken
            if (authentication instanceof AnonymousAuthenticationToken) {
                System.out.println("User is not authenticated (anonymous).");
            } else {
                System.out.println("User is authenticated as: " + authentication.getName());
            }
        }
        System.out.println("No authentication information found.");
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(Collections.singletonMap("status", "logged in"));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("status", "not logged in"));
        }
    }

    // "member/edit" 정보수정용 정보 불러오기
    @GetMapping("/getcurrentuserinfo")
    public MemberDto getCurrentUserInfo(){
        return authService.getCurrentUserInfo();
    }

    // ========== OAuth SNS 로그인 =============

}
