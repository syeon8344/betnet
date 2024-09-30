package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MarketDto;
import web.model.dto.MarketPageDto;
import web.model.dto.MarketReplyDto;

import java.util.List;

@Mapper
public interface MarketDao {

    // 1. 글 불러오기
    List<MarketDto> mkReadAll(int mkState, int startRow, int pageSize, String searchKeyword);

    // 1-1. 게시판 페이지화) 총 게시글 수 검색
    int getTotalBoardSize(int mkState, String searchKeyword);

    // 2. 글 작성하기 + 파일첨부
    boolean mkWrite(MarketDto marketDto, int memberId);

    // 3. 글 상세 페이지
    MarketDto mkRead(int mkId);

    // 3-1. 상세글보기에서 댓글 불러오기
    List<MarketReplyDto> mkReadReply(int mkId);

    // 4. 상세 페이지 들어갈 때 조회수 증가
    boolean mkView(int mkId);

    // 5. 글 수정/삭제 권한 확인
    boolean mkCheck(MarketDto dto);

    // 6. 글 수정하기 (거래완료 제외)
    boolean mkEdit(MarketDto marketDto);

    // 7. 글 삭제하기 (거래완료 제외)
    boolean mkDelete(int memberId, int mkId);

    // 8. 게시물 댓글 작성
    boolean mkWriteReply(MarketReplyDto marketReplyDto);

}
