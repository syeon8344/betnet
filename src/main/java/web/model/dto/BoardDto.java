package web.model.dto;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor
@Getter @Setter @ToString @Builder
public class BoardDto {

    private int postid; // bno
    private int memberid; // no
    private int teamcode;
    private String content;
    private String title;
    private String createdat;
    private int views;
    private int likes;

    // 팀코드 이름 변환 변수 생성
    private String teamname;

}
