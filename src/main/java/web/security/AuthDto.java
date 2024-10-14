package web.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
    private String username;
    private String password;
    private int level;
}
