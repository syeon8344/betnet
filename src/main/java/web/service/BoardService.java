package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BoardDao;
import web.model.dto.BoardDto;

@Service
public class BoardService {

    @Autowired private BoardDao boardDao;

    public boolean bWrite(BoardDto boardDto) {
        System.out.println("BoardService.bWrite");
        return boardDao.bWrite(boardDto);
    }

}
