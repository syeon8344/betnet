//package web.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import web.model.dao.PointDao;
//
//@Service
//public class PointService {
//    @Autowired
//    PointDao pointDao;
//
//    // 잔액 포인트 출력
//    public MemberDto getMyPoint(int memberid){
//        // 테스트 이후 멤버 아이디는 서비스에서 세션에서 가지고 오기
//        System.out.println("PointService.getMyPoint");
//        System.out.println("memberid = " + memberid);
//        return pointDao.getMyPoint(memberid);
//    }   // getMyPoint() end
//}
