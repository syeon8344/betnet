package web.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import web.model.dao.AuthDao;
import web.model.dto.MemberDto;

import java.util.Map;
import java.util.UUID;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // https://lotuus.tistory.com/79, https://lotuus.tistory.com/80 참고해보기
    // OAuth2UserService: OAuth2 로그인 요청을 처리하여 사용자 정보를 가져오는 데 사용
    @Autowired
    private AuthDao authDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession httpSession;

    // OAuth2UserRequest 객체를 인자로 받아 해당 요청에 대한 사용자 정보를 로드
    // OAuth2UserRequest는 OAuth2 인증 요청의 세부 정보를 담고 있으며, 인증 제공자와 관련된 정보를 포함
    // User == OAuthDto, PrincipalDetails == MemberDto
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String name = "";

        if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
            System.out.println(oAuth2UserInfo);
            name = (String) oAuth2UserInfo.getAttributes().get("name");
        }
        else if(provider.equals("kakao")){	//추가
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            Map<String, Object> properties = (Map<String, Object>) oAuth2UserInfo.getAttributes().get("properties");
            name = (String) properties.get("nickname"); // nickname 추출
            // System.out.println(oAuth2UserInfo);
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;  			// 사용자가 입력한 적은 없지만 만들어준다

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = passwordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다

        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_USER;

        MemberDto memberDto = authDao.findByUsername(username);

        //DB에 없는 사용자라면 회원가입처리
        if(memberDto == null){
            memberDto = MemberDto.builder()
                    .username(username).password(password).name(name).email(email).role(role)
                    .build();
            authDao.register(memberDto);
        }

        memberDto.setAttributes(oAuth2User.getAttributes());
        return new OAuthDto(memberDto, oAuth2UserInfo);
    }

}
