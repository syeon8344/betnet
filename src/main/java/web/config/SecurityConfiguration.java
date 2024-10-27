package web.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import web.security.CustomOAuth2UserService;

import java.util.List;

// Spring 설정 클래스
@Configuration
@EnableWebSecurity  // Spring Security 활성화
@EnableMethodSecurity // @PreAuthorize 등 어노테이션 기반 인증 ON 스위치
public class SecurityConfiguration{
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

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
        // 인증이 필요한 경로 리스트
        List<String> authenticationNeeded = List.of(
                "/member/logcheck", // 로그인 상태에서 정보 확인
                "/member/edit", // 정보 수정 요청
                "/member/purchase", // 개인 구매금액 포인트 통계
                "/member/refund", // 개인 배당금 통계
                "/board/write",  // 글 작성 페이지
                "/board/edit",  // 글 수정 페이지
                "/board/delete", // 글 삭제 API
                "/game/ispurchased", // 이미 구매된 경기인지 확인
                "/point/mypoint",  // 현재 보유 포인트 확인 (헤더)
                "/point/**" // 포인트 관련
        );

        http // Spring Security의 HttpSecurity 보안 객체
                // Add the custom login filter before the existing authentication filter
                .authorizeHttpRequests(authz -> // HTTP 요청에 대한 보안 규칙을 정의
                        authz
                                .requestMatchers("/admin/**").hasRole("ADMIN") // .hasRole("ADMIN") == @PreAuthorize("hasRole('ROLE_ADMIN')") ROLE_ 주의
                                // toArray(new String[0]): 0칸의 String 배열을 생성하여 List<String> 제네릭 타입 배열을 String 배열로 변환
                                .requestMatchers(authenticationNeeded.toArray(new String[0])).authenticated() // 인증이 필요한 경로 설정
                                .anyRequest().permitAll() // 그 외의 모든 요청은 인증 필요
                )
                .exceptionHandling((exception) -> exception // HTTP 예외 발생시 처리 방법
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .formLogin(form -> form // formData 형태로 들어오는 로그인 요청
                        .loginPage("/member/login") // 로그인 페이지
                        .usernameParameter("username") // 기본값도 username이라 생략 가능
                        .passwordParameter("password") // 기본값도 password라 생략 가능
                        .loginProcessingUrl("/login") // 로그인 로직 수행 엔드포인트, 현재 기본값 /login POST이므로 생략 가능
                        .defaultSuccessUrl("/") // 로그인 성공시 이동할 위치
                        .permitAll() // 로그인은 누구나 사용가능
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/")// OAuth 로그인 성공 후 페이지 (기본값도 "/")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService) // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                        ))
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 엔드포인트
                        .logoutSuccessUrl("/member/login") // 로그아웃 성공 후 리다이렉션 URL
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

    // 401 Unauthorized 핸들러
    @Bean
    AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response,authException) -> {
            String ajaxHeader = request.getHeader("X-Requested-With");

            if ("XMLHttpRequest".equals(ajaxHeader)) {
                // Handle AJAX requests
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getWriter().write("{\"error\": \"로그인이 필요한 기능입니다.\"}");
            } else {
                // Handle regular requests (Thymeleaf)
                response.sendRedirect("/error/unauthorized"); // Redirect to an error page
            }
        };
    }

    // 403 Forbidden 핸들러
    @Bean
    AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            String ajaxHeader = request.getHeader("X-Requested-With");

            if ("XMLHttpRequest".equals(ajaxHeader)) {
                // Handle AJAX requests
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                response.getWriter().write("{\"message\": \"접근 권한이 없습니다.\"}");
            } else {
                // Handle regular requests (Thymeleaf)
                response.sendRedirect("/error/unauthorized"); // Redirect to an error page
            }
        };
    }

    // 비밀번호 암호화 객체
    @Bean
    public static PasswordEncoder passwordEncoder() {
        // BCrypt 2^(매개변수)번의 해시 연산 수행, 4 ~ 31 사이, 클수록 부하 증가 및 보안성 향상
        return new BCryptPasswordEncoder(10);
    }

}