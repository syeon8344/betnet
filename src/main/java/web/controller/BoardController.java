package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.BoardDto;
import web.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired private BoardService boardService;

    @PostMapping("/write")
    public boolean bWrite(BoardDto boardDto) {
        System.out.println("BoardController.bWrite");
        return boardService.bWrite(boardDto);
    }

    @GetMapping("/readAll")
    public List<BoardDto> bRead( @RequestParam("teamcode") int teamcode){
        System.out.println("BoardController.bRead");
        System.out.println("teamcode = " + teamcode);
        return boardService.bRead(teamcode);
    }

    //게시판 개별조회
    @GetMapping("/find/bno")
    public BoardDto bFindBno( int bno ){
        return boardService.bFindBno(bno);
    }



    @PutMapping("/update")
    public boolean bUpdate(BoardDto boardDto ){
        return boardService.bUpdate(boardDto);
    }


    @DeleteMapping("/delete")
    public boolean bDelete(BoardDto boardDto ){
        System.out.println("BoardController.bDelete");
        System.out.println("boardDto = " + boardDto);
        return boardService.bDelete(boardDto);
    }

}
