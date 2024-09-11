package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;

import java.util.List;

@Mapper
public interface PointDao {
    // 잔액 포인트 출력
    MemberDto getMyPoint(int memberid);// getMyPoint() end

    // 포인트 충전 아임포트에서 결제 완료 시 member DB 업데이트
    int addPoint(int memberid , int pointChange); // addPoint() end

    // 포인트로그 포인트 충전 내역 저장
    int insertPointLog(PointLogDto pointLogDto); // insertPointLog() end

    // 포인트내역 출력
    List<PointLogDto> mypointlog(int memberid);// mypointlog() end

}
