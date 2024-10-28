package web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import web.model.dao.AuthDao;
import java.io.IOException;

public class OAuth2AdditionalInfoFilter extends OncePerRequestFilter {
    @Autowired
    private AuthDao authDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자 정보 확인
        if (authentication != null && authentication.isAuthenticated()) {
            // 추가 정보 입력이 필요한 경우
            // (예: 사용자가 최초 로그인한 경우)
            if (isFirstTimeUser(authentication)) {
                response.sendRedirect("/complete-registration");
                return; // 더 이상의 필터 체인 진행을 중단
            }
        }

        filterChain.doFilter(request, response); // 필터 체인 계속 진행
    }

    private boolean isFirstTimeUser(Authentication authentication) {
        // 사용자 정보를 확인하여 추가 정보 입력이 필요한지 판단하는 로직
        // 예를 들어, DB에서 추가 정보가 없으면 true를 반환
        // TODO: OAuth2 회원가입 추가 정보 입력 구현 (나이가 입력되지 않은 사용자 == 처음 등록한 사용자, 추가 정보 입력하도록 요구)
        OAuthDto oauthDto = (OAuthDto) authentication.getPrincipal();
        // 해당 회원 나이 등록 여부 체크
        return authDao.checkFirstOAuth2User(oauthDto.getMemberDto().getAge());
    }
}
