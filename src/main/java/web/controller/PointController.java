package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MemberDto;
import web.service.PointService;

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

    // 포인트 충전


}
