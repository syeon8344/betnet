package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.*;
import web.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired private MemberService memberService;

    // AuthController Spring Security 회원가입으로 전환
//    //09.09 회원가입
//    @PostMapping("/signup")
//    public boolean signUp(MemberDto memberDto){
//        System.out.println("MemberController.signUp");
//        System.out.println("memberDto = " + memberDto);
//        return memberService.signUp(memberDto);}

    // Spring Security /auth/login 로그인으로 전환
//    //09.10 로그인
//    @PostMapping("/login")
//    public MemberDto login(MemberDto memberDto){
//        System.out.println("MemberController.login");
//        System.out.println("memberDto = " + memberDto);
//        return memberService.login(memberDto);
//    }
    //09.10 로그인체크
    @GetMapping("/logincheck")
    public LoginCheckDto loginCheck(){
        return memberService.loginCheck();
    }

    //09.11 id 중복검사
    @GetMapping("/idchecking")
    public boolean idChecking(MemberDto memberDto){
        System.out.println("idChecking" + memberDto.getUsername());
        return memberService.idChecking(memberDto.getUsername());
    }

    //09.12 전화번호 중복검사
    @GetMapping("/phonecheck")
    public boolean phoneCheck(String contact){
        System.out.println("MemberController.phoneCheck");
        System.out.println("contact = " + contact);
        return memberService.phoneCheck(contact);
    }

    //09.12 이메일 중복검사
    @GetMapping("/emailcheck")
    public boolean emailCheck(String email){
        return memberService.emailCheck(email);
    }

    //09.12 팀정보 가져오기
    @GetMapping("/teams")
    public List<TeamsDto> teams(){
        return memberService.teams();
    }

    // Spring Security /logout으로 전환
//    //09.12 로그아웃
//    @GetMapping("/logout")
//    public void logout(){
//        memberService.logout();
//    }

//    //09.13 로그인상태에서 모든정보 가져오기
//    @GetMapping("/logcheck")
//    public LoginCheckDto logCheck(){
//       return memberService.logCheck();
//    }

    // 09.19 개인정보 수정
    @PutMapping("/api/edit")
    public boolean edit(MemberDto memberDto){
        System.out.println("edit memberDto = " + memberDto);
        return memberService.edit(memberDto);
    }

    // 09.23 개인 구매금액 포인트 통계
    @GetMapping("/purchase")
    public List<PointLogDto> purchase(SearchDto searchDto){
        System.out.println("purchase searchDto = " + searchDto);
        return memberService.purchase(searchDto);
    }

    // 09.23 개인 배당금 통계
    @GetMapping("/refund")
    public List<PointLogDto> refund(SearchDto searchDto){
        System.out.println("refund searchDto = " + searchDto);
        return memberService.refund(searchDto);
    }
}
