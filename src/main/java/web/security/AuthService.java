package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AuthDao authDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // UserDetailsService 인터페이스의 메서드 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDto user = authDao.findByUsername(username); // 사용자 정보 조회
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // 암호화된 비밀번호
                new ArrayList<>() // 권한 정보 추가 (필요시)
        );
    }

    public AuthDto authenticate(String username, String password) {
        AuthDto user = authDao.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 인증 성공
        }
        return null; // 인증 실패
    }

    // 회원이 레벨제인 경우
    public boolean hasAccess(AuthDto authDto, int requiredLevel) {
        return authDto.getLevel() >= requiredLevel; // 레벨 비교
    }
}
