package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardDao {

    // 게시물 등록 함수
    boolean bWrite(BoardDto boardDto);

    // 게시물 전체출력 함수
    List<BoardDto> bRead();

    //게시물 개별 조회 함수
    BoardDto bFindBno(int bno);


    // 게시물 카테고리 조회 함수
    List<BoardDto> caRead(int teamcode);

    // 게시물 수정 함수
    boolean bUpdate(BoardDto boardDto );

    // 게시물 삭제 함수
    boolean bDelete(BoardDto boardDto);

}
