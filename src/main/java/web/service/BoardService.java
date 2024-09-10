package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BoardDao;
import web.model.dto.BoardDto;

import java.util.List;

@Service
public class BoardService {

    @Autowired private BoardDao boardDao;

    public boolean bWrite(BoardDto boardDto) {
        System.out.println("BoardService.bWrite");
        System.out.println("boardDto = " + boardDto);
        return boardDao.bWrite(boardDto);
    }

    public List<BoardDto> bRead(){
        return boardDao.bRead();
    }

    public boolean bUpdate(BoardDto boardDto ){
        return boardDao.bUpdate(boardDto);
    }


    public boolean bDelete(BoardDto boardDto ){
        return boardDao.bDelete(boardDto);
    }
}
