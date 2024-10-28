package web.model.dto;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import web.security.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto implements UserDetails{

    private int memberid; //회원번호
    private String username; //아이디
    // @ToString.Exclude // .toString에서 제외
    private String password; //비번
    private String name;    //이름
    private String contact; //연락처
    private String email;   //이메일
    private String gender;  //성별
    private int age;        //나이
    private String joindate;//가입일
    private int teamcode;   // 선호팀코드
    private String account; //계좌
    private String teamname; //선호팀이름
    private Role role; // Enum 타입, 회원 권한
    // OAuth 2.0
    private Long id;
    private String provider;
    private String providerId;
    private String picture;
    private Map<String, Object> attributes;

    // UserDetails Implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 단일 권한을 리스트로 반환, singletonList(): 메모리에 효율적인 불변의 1칸 리스트
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

}
