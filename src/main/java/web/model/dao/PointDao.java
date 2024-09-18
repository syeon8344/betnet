package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;
import web.model.dto.SearchDto;

import java.util.List;

@Mapper
public interface PointDao {
    // 잔액 포인트 출력
    PointLogDto getMyPoint(int memberid);// getMyPoint() end

    // 포인트로그 포인트 충전 내역 저장
    int insertPointLog(PointLogDto pointLogDto); // insertPointLog() end

    // 포인트내역 출력
    List<PointLogDto> mypointlog(SearchDto searchDto);// mypointlog() end

}