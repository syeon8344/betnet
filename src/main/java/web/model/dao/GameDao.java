package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.GameDto;
import web.model.dto.SearchDto;

import java.util.List;
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

    // 게임 리스트 출력
    List<GameDto> getlist(SearchDto searchDto);    // getlist() end

    // 게임 상세 출력
    List<GameDto> getDetail(GameDto gameDto);// getDetail() end

    // 게임구매시 내가 구매한 이력이 있는 경기인지 판단
    List<GameDto> isPurchased(GameDto gameDto);// isPurchased() end

}   // end class GameDao
