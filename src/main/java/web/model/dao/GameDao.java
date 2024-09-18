package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.GameDto;

import java.util.Map;

@Mapper
public interface GameDao {

    // 포인트로그등록
    int insertPointLog(GameDto gameDto);

    // 포인트 로그 출력
    int getPointId();

    // 게임구매 등록
    int insertGameList(GameDto gameDto);

    // 리스트아이디 가져오기
    int getListtId();

    // 게임구매하기
    int gamePurchase(GameDto gameDto);

}   // end class GameDao
