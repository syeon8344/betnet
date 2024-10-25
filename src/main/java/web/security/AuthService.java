package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.model.dto.MemberDto;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AuthDao authDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 객체

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto user = authDao.findByUsername(username); // 사용자 정보 조회
        if (user == null) {
            throw new UsernameNotFoundException(username + "으로 조회된 사용자가 없습니다.");
        }

        // 단일 역할 처리
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // UserDetails 객체 생성 및 반환
        return MemberDto.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // 비밀번호는 해시화된 형태여야 함
                .role(user.getRole()) // 역할 정보 포함
                .level(user.getLevel()) // 유저 레벨
                .build();
    }

    public MemberDto authenticate(String username, String password) {
        MemberDto user = authDao.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 인증 성공
        }
        return null; // 인증 실패
    }

    public void signUp(MemberDto memberDto) {
        try {
            // 중복 사용자 체크
            if (authDao.findByUsername(memberDto.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists");
            }
            // 유효성 검사
            validateUserInput(memberDto);
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
            // 사용자 레벨 기본값
            int level = 0;

            // MemberDto 객체 생성
            MemberDto user = MemberDto.builder()
                    .username(memberDto.getUsername())
                    .password(encodedPassword)
                    .role(Role.USER)
                    .level(level)
                    .name(memberDto.getName())
                    .email(memberDto.getEmail())
                    .contact(memberDto.getContact())
                    .gender(memberDto.getGender())
                    .age(memberDto.getAge())
                    .teamcode(memberDto.getTeamcode())
                    .account(memberDto.getAccount())
                    .build();
            authDao.register(user); // 사용자 등록
        } catch (Exception e){
            System.out.println("AuthService signUp: " + e);
        }
    }

    // 유효성 검사 모음
    private void validateUserInput(MemberDto memberDto) {
        if (memberDto.getUsername() == null || memberDto.getUsername().isEmpty() || !memberDto.getUsername().matches("^[a-z0-9]{5,12}$")) {
            throw new IllegalArgumentException("아이디는 5~12자 길이의 영문 소문자 및 숫자 조합이어야 합니다.");
        }
        if (memberDto.getPassword() == null || memberDto.getPassword().isEmpty() || !memberDto.getPassword().matches("^(?=.*[A-Za-z])(?=.*[!@#$%^&*])(?=.*[0-9])[A-Za-z0-9!@#$%^&*]{5,15}$")) {
            throw new IllegalArgumentException("비밀번호는 영문자, 특수문자, 숫자가 최소 1개씩 포함된 5~15자 길이의 문자열이어야 합니다.");
        }
        if (memberDto.getName() == null || memberDto.getName().isEmpty()) {
            throw new IllegalArgumentException("이름을 다시 확인해 주세요.");
        }
        if (memberDto.getEmail() == null || memberDto.getEmail().isEmpty() || !memberDto.getEmail().matches("^[a-z0-9_-]+@[a-z0-9_-]+\\.[a-z]+$")) {
            throw new IllegalArgumentException("이메일을 다시 확인해 주세요.");
        }
        // 000-000-0000 또는 000-0000-0000 형식
        if (memberDto.getContact() == null || memberDto.getContact().isEmpty() || memberDto.getContact().matches("^([0-9]{3})+[-]+([0-9]{3,4})+[-]([0-9]{4})$")) {
            throw new IllegalArgumentException("전화번호를 다시 확인해 주세요.");
        }
        if (memberDto.getGender() == null || !memberDto.getGender().matches("^[MF]$")) {
            throw new IllegalArgumentException("성별을 선택해 주세요.");
        }
        if (memberDto.getAge() == 0 || memberDto.getAge() < 0) {
            throw new IllegalArgumentException("나이를 다시 확인해 주세요.");
        }
        if (memberDto.getTeamcode() == 0) {
            throw new IllegalArgumentException("선호하는 야구팀 선택 여부를 확인해 주세요.");
        }
        if (memberDto.getAccount() == null || memberDto.getAccount().isEmpty()) {
            throw new IllegalArgumentException("계좌번호를 입력해 주세요.");
        }
    }

}
