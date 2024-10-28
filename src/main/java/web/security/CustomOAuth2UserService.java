package web.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.dao.AuthDao;
import web.model.dto.MemberDto;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Transactional // 해당 메서드나 클래스의 모든 데이터베이스 작업이 하나의 트랜잭션으로 묶여 처리
// 메서드가 호출되면 새로운 트랜잭션이 시작되고, 메서드 실행이 완료되면 트랜잭션이 커밋됩니다.
// 만약 메서드 실행 중에 예외가 발생하면, 트랜잭션이 롤백되어 데이터베이스의 상태가 이전 상태로 복구됩니다.
public class CustomOAuth2UserService implements OAuth2UserService <OAuth2UserRequest, OAuth2User> {
    // https://lotuus.tistory.com/79, https://lotuus.tistory.com/80 참고해보기
    // OAuth2UserService: OAuth2 로그인 요청을 처리하여 사용자 정보를 가져오는 데 사용
    @Autowired
    private AuthDao authDao;

    @Autowired
    private HttpSession httpSession;

    // OAuth2UserRequest 객체를 인자로 받아 해당 요청에 대한 사용자 정보를 로드
    // OAuth2UserRequest는 OAuth2 인증 요청의 세부 정보를 담고 있으며, 인증 제공자와 관련된 정보를 포함
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService: 기본 제공되는 사용자 정보 로딩 기능을 사용
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드 (네이버 로그인인지 구글 로그인인지 구분)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값 (Primary Key와 같은 의미)을 의미
        // 구글의 기본 코드는 "sub", 후에 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuthUser의 attribute를 담을 클래스 ( 네이버 등 다른 소셜 로그인도 이 클래스 사용)
        OAuthDto attributes = OAuthDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        MemberDto userEntity = saveOrUpdate(attributes);

        // UserEntity 클래스를 사용하지 않고 SessionUser클래스를 사용하는 이유는 오류 방지.
        httpSession.setAttribute("user", userEntity);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보 업데이트 시 UserEntity 엔티티에 반영
    private MemberDto saveOrUpdate(OAuthDto attributes) {

        // 이메일을 기준으로 사용자를 찾아 업데이트하거나, 사용자를 새로 생성합니다.
        MemberDto memberDto = authDao.findByEmail(attributes.getEmail());

        if (memberDto != null) {
            // 사용자가 존재하면 업데이트
            memberDto.setName(attributes.getName());
            memberDto.setPicture(attributes.getPicture());
            authDao.updateMember(memberDto);
        } else {
            // 사용자가 존재하지 않으면 새로 생성
            memberDto = attributes.toMemberDto();
            authDao.insertMember(memberDto);
        }

        return memberDto;
    }

}
