package web.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PointLogDto {
    private int PointLogID;     // 포인트로그 pk
    private int MemberID;       // 회원아이디번호 fk
    private String logDate;     // 로그 증감 날짜
    private int pointChange;    // 포인트증감량 양수면 증가 음수면 감소
    private int description;    // 0 이면 구매 감소 1이면 충전 증가
}
