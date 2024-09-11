package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import web.model.dao.PointDao;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;

import java.util.List;

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

    // 포인트 충전 아임포트에서 결제 완료 시 member DB 업데이트
    public int addPoint(int memberid , int pointChange){
        return pointDao.addPoint(memberid , pointChange);
    }   // addPoint() end

    // 포인트로그 포인트 충전 내역 저장
    public int insertPointLog(PointLogDto pointLogDto){
        return pointDao.insertPointLog(pointLogDto);
    }   // insertPointLog() end

    // 포인트내역 출력
    public List<PointLogDto> mypointlog(int memberid){
        List<PointLogDto> pointLogDtos = pointDao.mypointlog(memberid);
        pointLogDtos.forEach(dto ->{
            // 숫자로 나오는 코드 미리 정한 이름으로 변환해서 dto에 저장하기
            // db에서 받은 숫자 코드 매개변수로 넘겨주기
            String descriptionStr = descriptionString(dto.getDescription());
            dto.setDescriptionStr(descriptionStr);
        });
        System.out.println(pointLogDtos);
        return pointLogDtos;
    }   // mypointlog() end

    // 포인트 description 변환
    public String descriptionString(int description){
        String descriptionStr = "";
        if(description == 1){
            descriptionStr = "포인트충전";
        }
        if(description == 0){
            descriptionStr = "게임구매";
        }
        return descriptionStr;
    }
}
