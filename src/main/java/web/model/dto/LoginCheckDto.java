package web.model.dto;

import lombok.Builder;
import lombok.Data;
import web.security.Role;

@Data
@Builder
public class LoginCheckDto {
    private int memberid;
    private String username;
    private String name;
    private Role role;
    private String account;
}
