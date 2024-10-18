package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AuthDao authDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 객체

    // UserDetailsService 인터페이스의 메서드 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDto user = authDao.findByUsername(username); // 사용자 정보 조회
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + "not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>(); // Roles 목록
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.name())); // 역할을 문자열로 변환하여 추가
        }

        // UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities // 불러온 Roles 사용
        );
    }

    public AuthDto authenticate(String username, String password) {
        AuthDto user = authDao.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 인증 성공
        }
        return null; // 인증 실패
    }

    // 회원가입
    public void signUp(String username, String password, List<Role> roles) {
        // 중복 사용자 체크
        if (authDao.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists"); // 예외 처리
        }

        // 입력 유효성 검사 (예시)
        if (username == null || password == null || roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Username, password, and roles cannot be null or empty");
        }

        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        int level = 0; // 사용자 레벨 초기화

        // AuthDto 객체 생성, roles는 List<Role>로 전달
        AuthDto user = new AuthDto(username, encodedPassword, roles, level);
        authDao.register(user); // 사용자 등록
    }

    // 회원이 레벨제인 경우
    public boolean checkLevel(AuthDto authDto, int requiredLevel) {
        return authDto.getLevel() >= requiredLevel; // 레벨 비교
    }
}
