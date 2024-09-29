package web.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import web.model.dao.GameDao;
import web.model.dao.PointDao;
import web.model.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
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
        return gameDao.getlist(searchDto);
    }   // getlist() end

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
        List<MatchScheduleDto> compareList = new ArrayList<>();
        // 어제 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        // System.out.println(yesterday);
        for(int i = 0; i < matchScheduleDto.size(); i++) {
            String month = matchScheduleDto.get(i).get월();
            String day = matchScheduleDto.get(i).get일();
            // matchScheduleDto 리스트에 있는 날짜 생성
            LocalDate givenDate = LocalDate.parse("2024-" + month + "-" + day, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 만약 원하는 날짜랑 맞으면 그 줄 compareList에 저장
            if(givenDate.isEqual(yesterday)){
                // matchScheduleDto 객체 생성 (예시로 필드값을 임의로 설정)
                MatchScheduleDto dto = matchScheduleDto.get(i);
                compareList.add(dto);
            }
        }   // for end
        // 승패 비교 함수
        compareList = compareWinandloss(compareList);
        // 회원 구매내역 변경
        compareMemberCorrect(compareList);
        // System.out.println("purchaseList = " + purchaseList);
        // 구매목록에서 한번에 가지고 와서 correct 비교 후 배당급 지급
        // 어제 날짜 구매목록 가지고 오기 // 오늘 날짜로 테스트
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = today.format(formatter);
        List<GameDto> yesterdayPurchaseList = gameDao.yesterdayPurchased(dateString);
        System.out.println("yesterdayPurchaseList = " + yesterdayPurchaseList);
        List<Integer> list = new ArrayList<Integer>();
        // 구매 목록에서 리스트 아이디 카운트와 correct 카운트 수가 맞으면 적중
        yesterdayPurchaseList.forEach(dto ->{
            if(dto.getListid_count() == dto.getCorrect_count()){
                int listid = dto.getListid();
                list.add(listid);
            }
        });
        System.out.println("list = " + list);
        // 배당금 지급
        // 먼저 gamestate가 4인 애들 가지고 오기 어제 날짜의 // 테스트는 오늘날짜
        List<GameDto> correctList = new ArrayList<>();
        // 리스트의 크기만큼 반복
        for (int i = 0; i < list.size(); i++) {
            // selectedCorrectList 메서드 호출하여 결과를 correctList에 추가
            List<GameDto> gameDtos = gameDao.selectedCorrectList(list.get(i), dateString);
            if (gameDtos != null) { // null 체크
                correctList.addAll(gameDtos);
            }
        }
        System.out.println(correctList);
        // listid 별로 묶어서 리스트 객체 만들기
        Map<Integer, List<GameDto>> groupByListid = correctList.stream().collect(Collectors.groupingBy(GameDto::getListid));
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
                pointChange = multiplyBigDecimalAndInt(totalOdds, pointChange);
                System.out.println("pointChange = " + pointChange);
                int result = gameDao.insertPointOods(memberid , pointChange);
                System.out.println("result = " + result);
//                if (result == 1){
//                    log.info("{}회원배당급 지급 완료{}", memberid, today);
//                    System.out.println("로그기록완료");
//                }
            };   // for문 end
         // c foreach end
    }
    // 경기 승패 비교
    public List<MatchScheduleDto> compareWinandloss(List<MatchScheduleDto> compareList){
        // 리스트 for 문 돌려서 경기가 승인지 패인지 계산 후 dto에 저장
        Iterator<MatchScheduleDto> iterator = compareList.iterator(); // Iterator : 컬렉션의 요소를 순차적으로 탐색하고, 요소를 제거하는 데 유용

        while (iterator.hasNext()) {
            MatchScheduleDto match = iterator.next(); // 현재 MatchScheduleDto 객체 가져오기

            // 우천 취소인 경기 리스트에서 제거
            if ("우천취소".equals(match.get비고()) || match.get비고() != null) {
                // System.out.println("경기 취소: " + match);
                String matchid = match.get경기코드();
                // 경기상태 경기취소로 변경
                gameDao.updateMatchstate(matchid);
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
        return compareList;
    }   // compareWinandloss end
    // 회원이 구매한 내역 승패 업데이트
    public void compareMemberCorrect(List<MatchScheduleDto> compareList){
        // 승패에 따라 회원 구매내역 correct 결과 값 변경
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
    }

    // BigDecimal과 int를 곱한 후 int로 변환하는 메서드
    public static int multiplyBigDecimalAndInt(BigDecimal bigDecimal, int intValue) {
        // 곱셈 결과를 BigDecimal로 계산
        BigDecimal multipliedValue = bigDecimal.multiply(BigDecimal.valueOf(intValue));

        // 결과를 int로 변환하여 반환
        return multipliedValue.intValue(); // 소수점 이하 부분은 버려짐
    }
}   // class GameService
