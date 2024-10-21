package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AuthDao authDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 객체

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDto user = authDao.findByUsername(username); // 사용자 정보 조회
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        // 단일 역할 처리
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // UserDetails 객체 생성 및 반환
        return AuthDto.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // 비밀번호는 해시화된 형태여야 함
                .role(user.getRole()) // 역할 정보 포함
                .build();
    }

    public AuthDto authenticate(String username, String password) {
        AuthDto user = authDao.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 인증 성공
        }
        return null; // 인증 실패
    }

    public void signUp(AuthDto authDto) {
        // 중복 사용자 체크
        if (authDao.findByUsername(authDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // 유효성 검사
        validateUserInput(authDto);

        String encodedPassword = passwordEncoder.encode(authDto.getPassword()); // 비밀번호 암호화
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("USER")); // Adjust roles as needed
        int level = 0; // 사용자 레벨 초기화

        // AuthDto 객체 생성
        AuthDto user = new AuthDto(authDto.getUsername(), encodedPassword, roles, level);
        authDao.register(user); // 사용자 등록
    }

    private void validateUserInput(AuthDto authDto) {
        if (authDto.getUsername() == null || authDto.getUsername().isEmpty() || !authDto.getUsername().matches("^[a-zA-Z0-9]{3,30}$")) {
            throw new IllegalArgumentException("Invalid username format");
        }
        if (authDto.getPassword() == null || authDto.getPassword().isEmpty() || !authDto.getPassword().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$")) {
            throw new IllegalArgumentException("Password must be at least 6 characters long and include both letters and numbers");
        }
        if (authDto.getName() == null || authDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (authDto.getEmail() == null || authDto.getEmail().isEmpty() || !authDto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (authDto.getContact() == null || authDto.getContact().isEmpty()) {
            throw new IllegalArgumentException("Contact cannot be null or empty");
        }
        if (authDto.getGender() == null || !authDto.getGender().matches("^[MF]$")) {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        if (authDto.getAge() == null || authDto.getAge() < 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        if (authDto.getTeamcode() == null) {
            throw new IllegalArgumentException("Team code cannot be null");
        }
        if (authDto.getAccount() == null || authDto.getAccount().isEmpty()) {
            throw new IllegalArgumentException("Account cannot be null or empty");
        }
    }

    // 회원 레벨 비교
    public boolean checkLevel(AuthDto authDto, int requiredLevel) {
        return authDto.getLevel() >= requiredLevel;
    }
}
