package web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * OncePerRequestFilter: HTTP 요청에서 한번만 통과되는 필터
 * 페이지 리다이렉트 등이 일어나더라도 한번만 처리되는 필터이므로 인증처럼 1회만 필요한 경우 적합
 */
public class RestLoginFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 생성자: AuthenticationManager를 주입받아 초기화합니다.
     *
     * @param authenticationManager 인증 관리 객체
     */
    public RestLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 특정 URI 및 HTTP 메서드에 대한 요청인지 확인
        if ("/member/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            try {
                // 요청에서 사용자 이름과 비밀번호를 가져옴
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                // 사용자 이름과 비밀번호를 트림하여 불필요한 공백 제거
                if (username != null) {
                    username = username.trim();
                }
                if (password != null) {
                    password = password.trim();
                }
                // 사용자 이름과 비밀번호가 모두 존재하는지 확인
                if (username != null && password != null) {
                    // 인증 시도
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
                    // 인증 성공 시 SecurityContext에 인증 정보 설정
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    // 사용자 이름이나 비밀번호가 없는 경우
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (AuthenticationException e) {
                // 인증 실패 시
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (Exception e) {
                // 기타 예외 발생 시
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                System.out.println("RestLoginFilter failed: " + e.getMessage());
            }
        } else {
            // 필터 체인을 계속 진행
            filterChain.doFilter(request, response);
        }
    }
}