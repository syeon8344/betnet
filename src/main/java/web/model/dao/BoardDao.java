package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardDao {

    boolean bWrite(BoardDto boardDto);

    List<BoardDto> bRead();

}
