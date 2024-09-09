package web.model.dto;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor
@Getter @Setter @ToString @Builder
public class BoardDto {

    private int postid;
    private int memberid;
    private int teamcode;
    private String content;
    private String title;
    private String createdat;
    private int views;
    private int likes;

}
