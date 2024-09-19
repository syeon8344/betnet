package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MemberDto;
import web.model.dto.TeamsDto;
import web.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired private MemberService memberService;

    //09.09 회원가입
    @PostMapping("/signup")
    public boolean signUp(MemberDto memberDto){
        System.out.println("MemberController.signUp");
        System.out.println("memberDto = " + memberDto);
        return memberService.signUp(memberDto);}

    //09.10 로그인
    @PostMapping("/login")
    public MemberDto login(MemberDto memberDto){
        System.out.println("MemberController.login");
        System.out.println("memberDto = " + memberDto);
        return memberService.login(memberDto);
    }

    //09.10 로그인체크
    @GetMapping("/logincheck")
    public MemberDto loginCheck(){
        return memberService.loginCheck();
    }

    //09.11 id 중복검사
    @GetMapping("/idchecking")
    public boolean idChecking(String userName){
        return memberService.idChecking(userName);
    }

    //09.12 전화번호 중복검사
    @GetMapping("/phonecheck")
    public boolean phoneCheck(String contact){
        System.out.println("MemberController.phoneCheck");
        System.out.println("contact = " + contact);
        return memberService.phoneCheck(contact);
    }

    //09.12 팀정보 가져오기
    @GetMapping("/teams")
    public List<TeamsDto> teams(){
        return memberService.teams();
    }

    //09.12 이메일 중복검사
    @GetMapping("/emailcheck")
    public boolean emailCheck(String email){
        return memberService.emailCheck(email);
    }

    //09.12 로그아웃
    @GetMapping("/logout")
    public void logout(){
        memberService.logout();
    }

    //09.13 로그인상태에서 모든정보 가져오기
    @GetMapping("/logcheck")
    public MemberDto logCheck(){
       return memberService.logCheck();
    }
}
