package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.dao.GameDao;
import web.model.dao.PointDao;
import web.model.dto.GameDto;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;
import web.model.dto.SearchDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

}   // class GameService
