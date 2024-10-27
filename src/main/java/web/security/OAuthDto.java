package web.security;

import lombok.*;
import web.model.dto.MemberDto;

import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OAuthDto {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String provider;
    private String providerId;

    public static OAuthDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(registrationId, userNameAttributeName, attributes);
    }

    private static OAuthDto ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthDto.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .provider(registrationId)
                .providerId((String) attributes.get("sub"))
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public MemberDto toMemberDto() {
        return MemberDto.builder()
                .name(name)
                .email(email)
                .role(Role.ROLE_OAUTH2)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
