package web.model.dto;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor
@Getter @Setter @ToString @Builder
public class BoardDto {

    private int memberid;
    private int teamcode;
    private String content;
    private String title;
    private int views;
    private int likes;

}
