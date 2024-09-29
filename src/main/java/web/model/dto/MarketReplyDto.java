package web.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MarketReplyDto {
    private int mkReplyId;  // 댓글 고유코드
    private int mkReplyWriter;  // 댓글 작성자 memberid
    private int mkId;  // 댓글이 있는 게시글 번호
    private String mkReplyDate;  // 댓글 작성날짜
    private String mkReplyContent;  // 댓글 내용
}
