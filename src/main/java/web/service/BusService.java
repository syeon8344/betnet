package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BusDao;
import web.model.dao.GameDao;
import web.model.dao.PointDao;
import web.model.dto.BusDto;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;

import java.util.List;

@Service
public class BusService {
    @Autowired
    BusDao busDao;
    @Autowired
    PointDao pointDao;
    @Autowired
    GameDao gameDao;
    @Autowired MemberService memberService;

    public boolean busReservation(BusDto busDto){
        System.out.println("BusService.busReservation");
        System.out.println("busDto = " + busDto);
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) return false;
        // 2. 속성 호출
        int memberid = loginDto.getMemberid();
        System.out.println("memberid = " + memberid);
        busDto.setMemberId(memberid);
        System.out.println("memberid = " + memberid);
        // 포인트 내역 확인하기
        PointLogDto pointLogDto = pointDao.getMyPoint(busDto.getMemberId());
        if(18000 > pointLogDto.getSum()) {
            return false;
        }

        busDao.busPurchase(busDto);
        int pointlogid=gameDao.getPointId();
        busDto.setPointlogid(pointlogid);
        return busDao.busReservation(busDto);
    }

    public List<BusDto> busCheck(String gameCode){
        System.out.println("gameCode = " + gameCode);
        return busDao.busCheck(gameCode);
    }

    public List<BusDto> busLog(){
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) return null;
        // 2. 속성 호출
        int memberid = loginDto.getMemberid();
        System.out.println("memberid = " + memberid);
        return busDao.busLog(memberid);
    }

}
