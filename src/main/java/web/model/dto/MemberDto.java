package web.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    //09.09 회원Dto
    private int memberid;
    private String userName;
    private String password;
    private String name;
    private String contact;
    private String email;
    private String gender;
    private int age;
    private String joinDate;
    private int teamCode;
    private int purchaseLimitAmount;
    private int purchaseLimitCount;
    private int points;
    private String account;
}
