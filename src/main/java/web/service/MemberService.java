package web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.dao.MemberDao;
import web.model.dto.MemberDto;

@Service
public class MemberService {
    @Autowired private MemberDao memberDao;
    @Autowired
    HttpServletRequest request;

    //09.09 회원가입
    public boolean signUp(MemberDto memberDto){
        System.out.println("MemberService.signUp");
        System.out.println("memberDto = " + memberDto);
        return memberDao.signUp(memberDto);}

    //09.10 로그인
    public boolean login(MemberDto memberDto){
        System.out.println("MemberService.login");
        System.out.println("memberDto = " + memberDto);
        int result=memberDao.login(memberDto);
        if(result>=1){
            MemberDto loginDto=MemberDto.builder()
                    .memberid(result)
                    .userName(memberDto.getUserName())
                    .build();
            HttpSession session = request.getSession();
            session.setAttribute("loginDto",loginDto);
            System.out.println("loginDto = " + loginDto);
            return true;
        }
        return false;
    }

    //09.10 로그인체크(세션객체에서 memberid,userName 추출가능)
    public MemberDto loginCheck( ){
        HttpSession session = request.getSession(); // 1. 현재 요청을 보내온 클라이언트의 세션객체호출
        // 2. 세션객체내 속성 값 호출 , 타입변환 필요하다.
        Object object = session.getAttribute( "loginDto" );
        if( object !=null ){   return (MemberDto)object;  }
        return null;
    }

    //09.11 id 중복검사
    public boolean idChecking(String userName){
        return memberDao.idChecking(userName);
    }
}
