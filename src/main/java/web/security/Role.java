package web.security;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_PREMIUM;// 주 5만 포인트 이상 충전시
}