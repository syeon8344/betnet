package web.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MatchScheduleDto {
    // "월":"09","일":"01","시작시간":"14:00","어웨이팀명":"NC","홈팀명":"SSG","어웨이점수":"8",
    // "홈점수":"2","비고":"-","경기코드":"20240901-SSG-1400","어웨이예측순위":"8.062","홈예측순위":"6.124",
    // "어웨이배당률":"1.59","홈배당률":"1.41","어웨이승률":"0.41","홈승률":"0.59"
    private String 월;
    private String 일;
    private String 시작시간;
    private String 어웨이팀명;
    private String 홈팀명;
    private int 어웨이점수;
    private int 홈점수;
    private String 비고;
    private String 경기코드;
    private double 어웨이예측순위;
    private double 홈예측순위;
    private double 어웨이배당률;
    private double 홈배당률;
    private double 어웨이승률;
    private double 홈승률;
    private int 결과; // 홈팀 기준    // 1 : 승 0 : 패
    private String 연도;
}
