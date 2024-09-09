package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MemberDto;

@Mapper
public interface PointDao {
    // 잔액 포인트 출력
    MemberDto getMyPoint(int memberid);// getMyPoint() end

}
