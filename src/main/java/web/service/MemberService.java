package web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.model.dao.MemberDao;
import web.model.dto.*;
import web.model.dao.AuthDao;

import java.util.List;

@Service
public class MemberService {
    @Autowired private MemberDao memberDao;
    @Autowired
    HttpServletRequest request;
    @Autowired
    AuthService authService;
    @Autowired
    AuthDao authDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    // AuthController Spring Security 회원가입으로 전환
//    //09.09 회원가입
//    public boolean signUp(MemberDto memberDto){
//        System.out.println("MemberService.signUp");
//        System.out.println("memberDto = " + memberDto);
//        return memberDao.signUp(memberDto);}

    // Spring Security /auth/login 로그인으로 전환
//    //09.10 로그인
//    public MemberDto login(MemberDto memberDto){
//        System.out.println("MemberService.login");
//        System.out.println("memberDto = " + memberDto);
//        MemberDto result =memberDao.login(memberDto);
//        System.out.println("result = " + result);
//        if(result!=null){
//            MemberDto loginDto=MemberDto.builder()
//                    .memberid(result.getMemberid())
//                    .username(result.getUsername())
//                    .build();
//            HttpSession session = request.getSession();
//            session.setAttribute("loginDto",loginDto);
//            System.out.println("loginDto = " + loginDto);
//            return result;
//        }
//        return result;
//    }

    //09.10 로그인체크(세션객체에서 memberid,userName 추출가능)
    public LoginCheckDto loginCheck( ){
        return authService.getCurrentLoginDto();
//        HttpSession session = request.getSession(); // 1. 현재 요청을 보내온 클라이언트의 세션객체호출
//        // 2. 세션객체내 속성 값 호출 , 타입변환 필요하다.
//        Object object = session.getAttribute( "loginDto" );
//        if( object !=null ){   return (MemberDto)object;  }
//        return null;
    }


    public LoginCheckDto logCheck( ){
        return authService.getCurrentLoginDto();
//        HttpSession session = request.getSession(); // 1. 현재 요청을 보내온 클라이언트의 세션객체호출
//        // 2. 세션객체내 속성 값 호출 , 타입변환 필요하다.
//        Object object = session.getAttribute( "loginDto" );
//        if(object==null){return null;}
//        MemberDto memberDto=(MemberDto)object;
//        int memberid=memberDto.getMemberid();
//        System.out.println(memberDao.logCheck(memberid));
//        return memberDao.logCheck(memberid);

    }

    //09.11 id 중복검사
    public boolean idChecking(String userName){
        return memberDao.idChecking(userName);
    }

    //09.12 전화번호 중복검사
    public boolean phoneCheck(String contact){
        System.out.println("MemberService.phoneCheck");
        System.out.println("contact = " + contact);
        return memberDao.phoneCheck(contact);
    }

    //09.12 팀정보 가져오기
    public List<TeamsDto> teams(){
        return memberDao.teams();
    }

    //09.12 이메일 중복검사
    public boolean emailCheck(String email){
        return memberDao.emailCheck(email);
    }

    //09.12 로그아웃
    public void logout(){
        // 1. 현재 요청을 보내온 클라이언트의 세션객체호출
        HttpSession session = request.getSession();
        // 2. 세션객체내 모든 속성 값 초기화
        session.invalidate();
    }

    // 09.19 개인정보 수정
    public boolean edit(MemberDto memberDto){
        // Retrieve the user from the database
        MemberDto user = authDao.findByUsername(memberDto.getUsername());
        if(passwordEncoder.matches(memberDto.getPassword(), user.getPassword())){
            memberDto.setPassword(user.getPassword());
            return memberDao.edit(memberDto);
        } else {
            return false;
        }
    }

    // 09.23 개인 구매금액 포인트 통계
    public List<PointLogDto> purchase(SearchDto searchDto){
        return memberDao.purchase(searchDto);
    }

    // 09.23 개인 배당금 통계
    public List<PointLogDto> refund(SearchDto searchDto) {
        System.out.println(memberDao.refund(searchDto));
        return memberDao.refund(searchDto);
    }


}
