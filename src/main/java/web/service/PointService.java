package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.PointDao;
import web.model.dto.MemberDto;

@Service
public class PointService {
    @Autowired
    PointDao pointDao;
    @Autowired
    MemberService memberService;

    // 잔액 포인트 출력
    public MemberDto getMyPoint( ){
        // 테스트 이후 멤버 아이디는 서비스에서 세션에서 가지고 오기
        System.out.println("PointService.getMyPoint");
        // 1. 로그인 세션에서 값 호출
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) return null;
        // 2. 속성 호출
        int memberid = loginDto.getMemberid();
        return pointDao.getMyPoint(memberid);
    }   // getMyPoint() end
}
