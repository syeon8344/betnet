package web.security;

import lombok.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto implements UserDetails {

    private int memberid; //회원번호
    private String username; //아이디
    private String password; //비번
    private String name;    //이름
    private String contact; //연락처
    private String email;   //이메일
    private String gender;  //성별
    private int age;        //나이
    private String joindate;//가입일
    private int teamCode;   // 선호팀코드
    private String account; //계좌
    private String teamName; //선호팀이름
    private Role role; // Enum 타입
    private int level; // 계정 레벨

    // UserDetails Implementation
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private boolean locked;	//계정 잠김 여부

    // 생성자
    public AuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true; // 기본값
        this.accountNonLocked = true; // 기본값
        this.credentialsNonExpired = true; // 기본값
        this.enabled = true; // 기본값
    }

    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return this.getRole();
            }

            private String getRole() {
                return role.name();  // role은 enum 클래스
            }
        });

        return collectors;
    }

    /**
     * 계정 만료 여부
     * @return : 만료 안됨 true, 만료됨 false
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * @return : 잠기지 않음 true, 잠김 false
     */
    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    /**
     * 비밀번호 만료 여부
     * @return : 만료되지 않음 true, 만료됨 false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * 계정이 잠겨있지 않는지 확인
     * @return : 활성화 true, 비활성화 false
     */
    @Override
    public boolean isEnabled() {
        //계정이 잠겨있지 않으면 true
        return (!locked);
    }
}
