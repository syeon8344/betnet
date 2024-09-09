package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.BoardDto;

@Mapper
public interface BoardDao {

    boolean bWrite(BoardDto boardDto);



}
