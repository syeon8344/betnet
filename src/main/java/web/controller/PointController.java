package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MemberDto;
import web.model.dto.PointLogDto;
import web.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    PointService pointService;

    // 잔액 포인트 출력
    @GetMapping("/mypoint")
    public MemberDto getMyPoint(){
        // 테스트 이후 멤버 아이디는 서비스에서 세션에서 가지고 오기
        System.out.println("PointController.getMyPoint");
        return pointService.getMyPoint();
    }   // getMyPoint() end

    // 포인트 충전 아임포트에서 결제 완료 시 member DB 업데이트
    @PutMapping("/addpoint")
    public int addPoint(int memberid , int pointChange){
        return pointService.addPoint(memberid , pointChange);
    }   // addPoint() end

    // 포인트로그 포인트 충전 내역 저장
    @PostMapping("/insertpointlog")
    public int insertPointLog(PointLogDto pointLogDto){
        return pointService.insertPointLog(pointLogDto);
    }   // insertPointLog() end

    // 포인트내역 출력
    @GetMapping("/mypointlog")
    public List<PointLogDto> mypointlog(int memberid){
        return pointService.mypointlog(memberid);
    }   // mypointlog() end
}
