package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BusDao;
import web.model.dto.BusDto;
import web.model.dto.MemberDto;

@Service
public class BusService {
    @Autowired
    BusDao busDao;
    @Autowired MemberService memberService;


    // 예약
    public boolean busLog(BusDto busDto){
        System.out.println("busDto = " + busDto);
        return busDao.busLog(busDto);
    }

    public boolean busReservation(BusDto busDto){
        System.out.println("BusService.busReservation");
        System.out.println("busDto = " + busDto);
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) return false;
        // 2. 속성 호출
        int memberid = loginDto.getMemberid();
        busDto.setMemberId(memberid);
        System.out.println("memberid = " + memberid);
        return busDao.busReservation(busDto);
    }

}
