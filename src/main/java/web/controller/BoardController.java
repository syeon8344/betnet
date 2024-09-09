package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.BoardDto;
import web.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired private BoardService boardService;

    @PostMapping("/write")
    public boolean bWrite(BoardDto boardDto) {
        System.out.println("BoardController.bWrite");
        return boardService.bWrite(boardDto);
    }

}
