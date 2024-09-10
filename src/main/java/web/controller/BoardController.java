package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public List<BoardDto> bRead(){
        return boardService.bRead();
    }


    @PutMapping("/update")
    public boolean bUpdate(BoardDto boardDto ){
        return boardService.bUpdate(boardDto);
    }


    @DeleteMapping("/delete")
    public boolean bDelete(BoardDto boardDto ){
        return boardService.bDelete(boardDto);
    }

}
