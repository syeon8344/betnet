package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    // 메인페이지
    @GetMapping("/")    // http://localhost:8080/ // 페이지 요청은 HTTP의 GET방식을 주로 사용한다.
    public String index(){
        return "/index.html";   // templates 폴더내 반환할 경로와 파일명
    }

    // 포인트내역 페이지
    @GetMapping("/point")    // http://localhost:8080/ // 페이지 요청은 HTTP의 GET방식을 주로 사용한다.
    public String memberPoint(){
        return "/point/point.html";   // templates 폴더내 반환할 경로와 파일명
    }



    //==========================게시판(board)시작==================================

    // 게시판 페이지
    @GetMapping("/board") // http 페이지 주소
    public String board(){return "/board/board.html";  }

    @GetMapping("/board/write") // http 페이지 주소
    public String boardWrite(){return "/board/write.html";  }

    @GetMapping("/board/view") // http 페이지 주소
    public String boardView(){return "/board/view.html";  }

    @GetMapping("/board/update") // http 페이지 주소
    public String boardUpdate(){return "/board/update.html";  }


    //==========================게시판(board)끝==================================

    //===============멤버
    // 로그인 페이지
    @GetMapping("/member/login")
    public String login(){return "/member/login.html";}

    // 회원가입 페이지
    @GetMapping("/member/signup")
    public String signup(){return "/member/signup.html";}

    // 마이페이지
    @GetMapping("/member/mypage")
    public String myPage(){return "/member/mypage.html";}

    //===============멤버
    // 설문 페이지
    @GetMapping("/poll")
    public String seve(){
        return "/survey/survey.html";
    }

    // 게임구매내역페이지
    @GetMapping("/game")
    public String gameList(){
        return "/game/gameList.html";
    }

    // [1] 채팅 관련 템플릿 매핑
    @GetMapping("/chat")
    public String chat(){ return "/index.html";
    }

}
