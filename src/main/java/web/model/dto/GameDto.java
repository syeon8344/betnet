package web.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor@AllArgsConstructor
@Getter @Setter @ToString @Builder
public class GameDto {
    // GamePurchaseDetails  // 게임구매 상세
    private int detailid;
    private int listid; // 게임 구매목록 fk
    private String matchid; // csv에서 가져오는 경기 인덱스
    private int winandloss; // 회원이 설정하는 승패 1 : 승 0 : 패
    private int matchstate; // 경기 상태 0 : 경기 취소 1 : 경기 정상
    private int correct;    // 1 : 적중 0: 적중 실패  // 한 게임당 적중결과
    private int gamestate;  // 게임 상태 1 : 발매중 2: 발매마감 3: 적중실패 4: 적중 5: 배당금지급완료   // 여러 게임 구매 시 적중 결과
    private String gamestateStr;

    // GamePurchaseList // 게임구매목록
    private int pointlogid;

    private List<String> matchids; // 여러게임 구매를 위한 matchid 배열
    private List<Integer> winandlosses; // 여러게임 구매를 위한 winandloss 배열
    private List<Integer> oddses; // 여러게임 구매를 위한 odds 배열

    private int memberid;
    private int pointChange;
    private int odds; // 배당률

    private String logDate; // 포인트 거래일시
}