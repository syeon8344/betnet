package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.GameDao;
import web.model.dao.PointDao;
import web.model.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    GameDao gameDao;
    @Autowired
    PointDao pointDao;
    @Autowired
    MemberService memberService;

    // 게임 구매
    public int gamePeurchase(GameDto gameDto){
        System.out.println("GameService.gamePeurchase");
        System.out.println("gameDto = " + gameDto);
        // 포인트 내역 확인하기
        PointLogDto pointLogDto = pointDao.getMyPoint(gameDto.getMemberid());
        if(gameDto.getPointChange() > pointLogDto.getSum()) {
            return 3;
        }
        // 금액 음수로 바꾸기
        gameDto.setPointChange(-gameDto.getPointChange());
        // System.out.println(gameDto.getPointChange());
        // 포인트 로그 등록
        gameDao.insertPointLog(gameDto);
        // 포인트 로그 얻기
        int pointlogid = gameDao.getPointId();
        System.out.println("pointlogid = " + pointlogid);
        gameDto.setPointlogid(pointlogid);
        // 게임구매목록 등록하기
        gameDao.insertGameList(gameDto);
        // 리스트 아이디 가져오기
        int listid = gameDao.getListtId();
        System.out.println("listid = " + listid);
        gameDto.setListid(listid);
        // 게임상세 등록하기 // 리스트들 for문 돌리기..
        List<String> matchids = gameDto.getMatchids();
        List<Integer> winandlosses = gameDto.getWinandlosses();
        int result = 0;
        for (int i = 0; i < matchids.size(); i++) {
            String  matchid = matchids.get(i);
            System.out.println("matchid = " + matchid);
            gameDto.setMatchid(matchid);

            // winandlosses와 같은 인덱스일 경우에 dao전달
            if (i < winandlosses.size()) {
                int winandloss = winandlosses.get(i); // i번째 winandloss 사용  // matchids , winandlosses 의 배열 순서가 같은 경우
                System.out.println("winandloss = " + winandloss);
                gameDto.setWinandloss(winandloss);
                result = gameDao.gamePurchase(gameDto);
            }
        }
        return result;
    }   // end method gamePurchase

    // 게임 리스트 출력
    public List<GameDto> getlist(SearchDto searchDto){
        MemberDto memberDto = memberService.loginCheck();
        int memberid = memberDto.getMemberid();
        searchDto.setMemberid(memberid);
        List<GameDto> list = gameDao.getlist(searchDto);
        list.forEach(dto ->{
            // 숫자로 나오는 코드 미리 정한 이름으로 변환해서 dto에 저장하기
            // db에서 받은 숫자 코드 매개변수로 넘겨주기
            String descriptionStr = gameStateStr(dto.getGamestate());
            dto.setGamestateStr(descriptionStr);
        });
        return list;
    }   // getlist() end

    // 게임 상태 문자 변환
    public String gameStateStr(int gamestate){
        String gamestateStr = "";
        if(gamestate == 1){
            gamestateStr = "발매중";
        }
        if(gamestate == 2){
            gamestateStr = "발매마감";
        }
        if(gamestate == 3){
            gamestateStr = "적중실패";
        }
        if(gamestate == 4){
            gamestateStr = "적중";
        }
        if(gamestate == 5){
            gamestateStr = "배당금지급완료";
        }
        return gamestateStr;
    }

    // 게임 상세 출력
    public List<GameDto> getDetail(GameDto gameDto){
        MemberDto memberDto = memberService.loginCheck();
        int memberid = memberDto.getMemberid();
        gameDto.setMemberid(memberid);
        return gameDao.getDetail(gameDto);
    }   // getDetail() end

    // 게임구매시 내가 구매한 이력이 있는 경기인지 판단
    public boolean isPurchased(GameDto gameDto){
        System.out.println("GameService.isPurchased");
        System.out.println("gameDto = " + gameDto);
        String matchid = gameDto.getMatchid();
        MemberDto memberDto = memberService.loginCheck();
        int memberid = memberDto.getMemberid();
        gameDto.setMemberid(memberid);
        List<GameDto> gameDtos = gameDao.isPurchased(gameDto);
        boolean result = false;
        for (int i = 0; i < gameDtos.size(); i++){
            String matchid2 = gameDtos.get(i).getMatchid();
            if(matchid.equals(matchid2)){
                result = true;
                break;
            }
        }
        return result;
    }   // isPurchased() end

    // 배당금 지급
    public void payout(List<MatchScheduleDto> matchScheduleDto){
        System.out.println("GameService.payout");
        // System.out.println("matchScheduleDto = " + matchScheduleDto);
        List<MatchScheduleDto> compareList = new ArrayList<>();
        // 어제 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(2);
        // System.out.println(yesterday);
        for(int i = 0; i < matchScheduleDto.size(); i++) {
            String month = matchScheduleDto.get(i).get월();
            String day = matchScheduleDto.get(i).get일();
            // matchScheduleDto 리스트에 있는 날짜 생성
            LocalDate givenDate = LocalDate.parse("2024-" + month + "-" + day, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // System.out.println("givenDate = " + givenDate);
            // System.out.println("yesterday = " + yesterday);
            // 만약 원하는 날짜랑 맞으면 그 줄 compareList에 저장
            if(givenDate.isEqual(yesterday)){
                // matchScheduleDto 객체 생성 (예시로 필드값을 임의로 설정)
                MatchScheduleDto dto = matchScheduleDto.get(i);
                compareList.add(dto);
                // System.out.println("compareList = " + compareList);
            }
        }   // for end
        // 리스트 for 문 돌려서 경기가 승인지 패인지 계산 후 dto에 저장
        // System.out.println("compareList = " + compareList);
        Iterator<MatchScheduleDto> iterator = compareList.iterator(); // Iterator 사용

        while (iterator.hasNext()) {
            MatchScheduleDto match = iterator.next(); // 현재 MatchScheduleDto 객체 가져오기

            // 우천 취소인 경기 리스트에서 제거
            if ("우천취소".equals(match.get비고()) || match.get비고() != null) {
                // System.out.println("경기 취소: " + match);
                String matchid = match.get경기코드();
                // System.out.println("matchid = " + matchid);
                int result = gameDao.updateMatchstate(matchid);
                // System.out.println("result = " + result);
                iterator.remove(); // 안전하게 리스트에서 제거
                continue; // 다음 경기로 넘어감
            }

            // 승패 계산
            if (match.get비고() == null) {
                if (match.get홈점수() > match.get어웨이점수()) {
                    match.set결과(1); // 홈 팀 승리
                } else {
                    match.set결과(0); // 어웨이 팀 승리
                }
            }
        }   // while end

        System.out.println(compareList);
        List<GameDto> purchaseList = new ArrayList<>();
        compareList.forEach(c ->{
            String matchid = c.get경기코드();
            List<GameDto> purchased = gameDao.purchased(matchid);
            purchaseList.addAll(purchased);
        });
        // System.out.println("purchaseList = " + purchaseList);
        // System.out.println(purchaseList.size());
        compareList.forEach(c -> {
            purchaseList.forEach(p -> {
                if (c.get경기코드().equals(p.getMatchid())) { // equals() 사용
                    String matchid = c.get경기코드();
                    if (c.get결과() == p.getWinandloss()) {
                        int result = gameDao.updateCorrect(matchid , 1);
                        System.out.println("result = " + result);
                    } else {
                        int result = gameDao.updateCorrect(matchid , 2);
                        System.out.println("result = " + result);
                    }
                }
            });
        });
        // System.out.println("purchaseList = " + purchaseList);
        // 구매목록에서 한번에 가지고 와서 correct 비교 후 구매목록 상태 수정
        // 어제 날짜 구매목록 가지고 오기
        // 오늘 날짜로 테스트
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = today.format(formatter);
        // System.out.println("dateString = " + dateString);
        List<GameDto> yesterdayPurchaseList = gameDao.yesterdayPurchased(dateString);
        System.out.println("yesterdayPurchaseList = " + yesterdayPurchaseList);
        List<Integer> list = new ArrayList<>();
        // 구매 목록에서 리스트 아이디 카운트와 correct 카운트 수가 맞으면 적중
        yesterdayPurchaseList.forEach(dto ->{
            if(dto.getListid_count() == dto.getCorrect_count()){
                int listid = dto.getListid();
                list.add(listid);
            }
        });
        System.out.println("list = " + list);
        // 해당 리스트 아이디에 맞는 gamestate 바꾸고 구매 테이블 다시 가지고 와서 경기코드 가지고 오고 경기일정에서 배당률 계산해서 배당금 지급
        for(int i=0; i< list.size(); i++){
            int result = gameDao.updateCorrectResult(list.get(i) , 4 );
            System.out.println(result);
        }
        // 배당금 지급
        // 먼저 gamestate가 4인 애들 가지고 오기 어제 날짜의
        List<GameDto> state4List = gameDao.selectedGamestate4(dateString);
        System.out.println("state4List = " + state4List);
        // listid 별로 묶어서 리스트 객체 만들기
        Map<Integer, List<GameDto>> groupByListid = state4List.stream().collect(Collectors.groupingBy(GameDto::getListid));
        // System.out.println("groupByListid = " + groupByListid);

            for (Map.Entry<Integer, List<GameDto>> entry : groupByListid.entrySet()) {
                Integer listid = entry.getKey();
                List<GameDto> games = entry.getValue();

                // 여기서 listid와 해당 게임 리스트에 대한 작업 수행
                // System.out.println("List ID: " + listid);
                BigDecimal totalOdds = BigDecimal.valueOf(1.0); // 초기값 설정
                int pointChange = 0;
                int memberid = 0;
                for (GameDto game : games) {
                    // 각 GameDto 객체에 대한 작업 수행
                    System.out.println(game.getMatchid());
                    compareList.forEach(c -> {
                        // 경기일정의 경기코드랑 리스트 게임에서 경기코드가 같으면
                        if (c.get경기코드().equals(game.getMatchid())) {
                            if (c.get결과() == 1) {
                                // c.get결과()가 1이면 홈팀 승리
                                // System.out.println(c.get홈배당률());
                                double oods = c.get홈배당률();
                                game.setOdds(oods);
                                // System.out.println("Game Info: " + game);
                            }
                            if (c.get결과() == 0) {
                                // System.out.println(c.get어웨이배당률());
                                double oods = c.get어웨이배당률();
                                game.setOdds(oods);
                                // System.out.println("Game Info: " + game);
                            }
                        }
                    });
                    // 각 경기마다 배당률 곱하기
                    totalOdds = totalOdds.multiply(BigDecimal.valueOf(game.getOdds())); // 배당률 누적
                    totalOdds = totalOdds.setScale(2, RoundingMode.HALF_UP); // 소수점 두 자리 반올림
                    pointChange = -game.getPointChange();
                    memberid = game.getMemberid();
                }   // for문 end
                System.out.println(listid +"totaloods = " + totalOdds);
                System.out.println(listid + "pointChange = " + pointChange);
                System.out.println(listid + "memberid = " + memberid);
                pointChange = multiplyBigDecimalAndInt(totalOdds, pointChange);
                System.out.println("pointChange = " + pointChange);
                int result = gameDao.insertPointOods(memberid , pointChange);
                System.out.println("result = " + result);
            };   // for문 end
         // c foreach end
    }
    // BigDecimal과 int를 곱한 후 int로 변환하는 메서드
    public static int multiplyBigDecimalAndInt(BigDecimal bigDecimal, int intValue) {
        // 곱셈 결과를 BigDecimal로 계산
        BigDecimal multipliedValue = bigDecimal.multiply(BigDecimal.valueOf(intValue));

        // 결과를 int로 변환하여 반환
        return multipliedValue.intValue(); // 소수점 이하 부분은 버려짐
    }

}   // class GameService
