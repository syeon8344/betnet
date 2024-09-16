package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BoardDao;
import web.model.dto.BoardDto;
import web.model.dto.MemberDto;
import web.model.dto.ReplyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired private BoardDao boardDao;
    @Autowired private MemberService memberService;


    public boolean bWrite(BoardDto boardDto) {

        System.out.println("BoardService.bWrite");
        MemberDto loginDto = memberService.loginCheck();
        System.out.println("loginDto = " + loginDto);
        if (loginDto == null) return false;
        int no = loginDto.getMemberid();
        boardDto.setMemberid(no);
        System.out.println("boardDto = " + boardDto);

        return boardDao.bWrite(boardDto);
    }


    public List<BoardDto> bRead(int teamcode) {
        System.out.println("BoardService.bRead");
        List<BoardDto> boardList;

        if (teamcode == 0) {
            boardList = boardDao.bRead();
        } else {
            boardList = boardDao.caRead(teamcode);
        }

        // 각 BoardDto 객체의 teamcode를 teamname으로 변환
        for (BoardDto board : boardList) {
            String teamname = nameChange(board.getTeamcode());
            board.setTeamname(teamname); // 변환된 팀 이름 설정
        }

        return boardList;
    }

    //teamcode -> teamname 변환
    public String nameChange(int teamcode){
        String cateName = "";
        if(teamcode==1){
            cateName = "KIA";
        }
        if(teamcode==2){
            cateName = "삼성";
        }
        if(teamcode==3){
            cateName = "LG";
        }
        if(teamcode==4){
            cateName = "두산";
        }
        if(teamcode==5){
            cateName = "KT";
        }
        if(teamcode==6){
            cateName = "한화";
        }
        if(teamcode==7){
            cateName = "SSG";
        }
        if(teamcode==8){
            cateName = "롯데";
        }
        if(teamcode==9){
            cateName = "NC";
        }
        if(teamcode==10){
            cateName = "키움";
        }
        if(teamcode==11){
            cateName = "자유";
        }
        return cateName;
    }   // nameChange() end


    //  게시물 개별 조회 처리
    public BoardDto bFindBno( int bno ){
        List<BoardDto> boardList;
        System.out.println("BoardService.bFindBno");
        System.out.println("bno = " + bno);

        // 조회수 증가 처리
        boardDao.bViewIncrease(bno);

        return boardDao.bFindBno( bno );
    }


    public boolean bUpdate(BoardDto boardDto ){
        System.out.println("BoardService.bUpdate");
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) return false;
        int no = loginDto.getMemberid();
        boardDto.setMemberid(no);
        System.out.println("boardDto = " + boardDto);

        return boardDao.bUpdate(boardDto);
    }


    // 게시물 삭제 메서드
    public boolean bDelete(BoardDto boardDto ){
        MemberDto loginDto = memberService.loginCheck();
        System.out.println("loginDto = " + loginDto);
        if (loginDto == null) return false;
        int no = loginDto.getMemberid();
        boardDto.setMemberid(no);
        System.out.println("boardDto = " + boardDto);
        return boardDao.bDelete(boardDto);
    }

    /////////////////////////댓글 메섣/////////////
    public List<ReplyDto> rpRead(int bno){
        System.out.println("BoardService.bReplyRead");
        System.out.println("bno = " + bno);
        return boardDao.rpRead(bno);
    }

    public boolean reWrite(ReplyDto replyDto){
        System.out.println("BoardService.reWrite");
        MemberDto loginDto = memberService.loginCheck();
        System.out.println("loginDto = " + loginDto);
        if (loginDto == null) return false;
        int no = loginDto.getMemberid();
        replyDto.setMemberid(no);
        System.out.println("replyDto = " + replyDto);
        return boardDao.reWrite(replyDto);
    }


}
