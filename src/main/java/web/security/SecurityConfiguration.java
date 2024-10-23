package web.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

// Spring 설정 클래스
@Configuration
@EnableWebSecurity  // Spring Security 활성화
public class SecurityConfiguration{

    // HTTP 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // "/**" 처럼 **은 해당 경로 및 하위 경로 전체에 적용된다.
        // "/" 처럼 **이 없더라도 해당 경로에 쿼리스트링이 추가된 경우는 해당된다.
        // 예시) /board?param=value 같은 경우 "/board" 및 "/board/**" 모두 해당된다.
        // ViewController의 엔드포인트와 RestController들의 엔드포인트들을 모두 포함한다.
        // 정확한 경로 → 높은 우선순위 (** 미포함)
        // 하위 경로 포함 경로 → 낮은 우선순위 (** 포함)
        // 허용할 경로 리스트, List.of() 사용시 불변 리스트
        // TODO: 우선 전체 엔드포인트를 허용하고 특정 인증 필요 엔드포인트들만 명시하기 (블랙리스트)
        List<String> permittedPaths = List.of( // 현재 사용하지 않음
                "/", // 메인 페이지
                // 정적 리소스들
                "/css/**",
                "/csv/**",
                "/img/**",
                "/js/**",
                "/upload/**",
                "/favicon.ico",
                "/member/login", // 로그인 페이지
                "/member/signup", // 회원가입 
                "/error/unauthorized", // 401 에러 페이지
                // API
                "/auth/**", // 인증 관련 (로그인, 회원가입)
                "/member/**", // 일부 멤버 API 제외 나머지
                "/history/**", // 크롤링 데이터 조회 페이지
                "/board",  // 게시판
                "/board/view",  // 게시판 상세글 보기
                "/chat/**"

        );

        // 인증이 필요한 경로 리스트
        List<String> authenticationNeeded = List.of(
                "/admin/**", // 관리자
                "/member/logcheck", // 로그인 상태에서 정보 확인
                "/member/edit", // 정보 수정 요청
                "/member/purchase", // 개인 구매금액 포인트 통계
                "/member/refund", // 개인 배당금 통계
                "/board/write",  // 글 작성 페이지
                "/board/edit",  // 글 수정 페이지
                "/board/delete", // 글 삭제 API
                "/game/ispurchased",
                "/point/**" // 포인트 관련
        );

        http // Spring Security의 HttpSecurity 보안 객체
                // Add the custom login filter before the existing authentication filter
                .authorizeHttpRequests(authReq -> // HTTP 요청에 대한 보안 규칙을 정의
                        authReq
                                // toArray(new String[0]): 0칸의 String 배열을 생성하여 List<String> 제네릭 타입 배열을 String 배열로 변환
                                .requestMatchers(authenticationNeeded.toArray(new String[0])).authenticated() // 인증이 필요한 경로 설정
                                .anyRequest().permitAll() // 그 외의 모든 요청은 인증 필요
                )
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/member/login") // 로그아웃 성공 후 리다이렉션 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );
        return http.build(); // 설정된 SecurityFilterChain 반환
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response,authException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.sendRedirect("/error/unauthorized");
        };
    }

    @Bean
    AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            // JSON 형태의 응답 생성
            String jsonResponse = "{\"message\":\"접근 권한이 없습니다.\"}";
            response.getWriter().write(jsonResponse);
        };
    }

    // 비밀번호 암호화 객체
    @Bean
    public static PasswordEncoder passwordEncoder() {
        // SCrypt 알고리즘 강도 설정: 인증 과정이 1초 정도 걸리도록 부하 조정
        // 순서대로 CPU 부하, 메모리 부하, 병렬화 수준, 출력 길이, 해시 길이 설정
        // - CPU 부하: 16384 (CPU 리소스 사용량을 결정, 값이 클수록 더 많은 시간 소요)
        // - 메모리 부하: 4 (메모리 사용량을 결정, 값이 클수록 더 많은 메모리 사용)
        // - 병렬화 수준: 1 (동시에 사용할 스레드 수, 일반적으로 1로 설정)
        // - 출력 길이: 32 (최종 해시 결과의 바이트 길이)
        // - 해시 길이: 16 (내부 해시 결과의 바이트 길이)
        return new SCryptPasswordEncoder(16384, 4, 1, 32, 16);
    }

}