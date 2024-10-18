package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

// Spring 설정 클래스
@Configuration
@EnableWebSecurity  // Spring Security 활성화
public class SecurityConfiguration{

    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security 인증 처리 관리자

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
        List<String> permittedPaths = List.of(
                "/", // 메인 페이지
                "/member/login",  // 로그인 페이지
                "/member/salary", // 연봉 예측 페이지
                "/board",  // 게시판
                "/board/view"  // 게시판 상세글 보기
        );

        // 인증이 필요한 경로 리스트
        List<String> authenticatedPaths = List.of(
                "/member/**",  // 회원 마이페이지 등 나머지 /member 엔드포인트
                "/board/write",  // 글 작성 페이지
                "/board/edit",  // 글 수정 페이지
                "/board/delete" // 글 삭제 API
        );

        http // Spring Security의 HttpSecurity 보안 객체
                // Add the custom login filter before the existing authentication filter
                .authorizeHttpRequests(authReq -> // HTTP 요청에 대한 보안 규칙을 정의
                        authReq
                                // toArray(new String[0]): 0칸의 String 배열을 생성하여 List<String> 제네릭 타입 배열을 String 배열로 변환
                                .requestMatchers(permittedPaths.toArray(new String[0])).permitAll() // 허용할 경로 설정
                                .requestMatchers(authenticatedPaths.toArray(new String[0])).authenticated() // 인증이 필요한 경로 설정
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/member/login") // 로그아웃 성공 후 리다이렉션 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
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

    // 비밀번호 암호화 객체
    @Bean
    public PasswordEncoder passwordEncoder() {
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