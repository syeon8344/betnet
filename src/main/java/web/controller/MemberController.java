package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MemberDto;
import web.service.MemberService;

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
    public boolean login(MemberDto memberDto){
        System.out.println("MemberController.login");
        System.out.println("memberDto = " + memberDto);
        return memberService.login(memberDto);
    }

//    @GetMapping("/logincheck")
//    public int loginCheck(){
//        return memberService.loginCheck();
//    }

}