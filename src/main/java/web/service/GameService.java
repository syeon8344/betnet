package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.dao.GameDao;
import web.model.dao.PointDao;
import web.model.dto.GameDto;
import web.model.dto.PointLogDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameService {
    @Autowired
    GameDao gameDao;
    @Autowired
    PointDao pointDao;

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

}   // class GameService
