package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.MarketDto;

@Mapper
public interface MarketDao {
    // 1. 글 불러오기
    MarketDto mkReadAll();

    // 2. 글 작성하기 + 파일첨부
    boolean mkWrite(MarketDto marketDto, int memberId);

    // 3. 글 카테고리 불러오기


    // 4. 글 상세 페이지
    MarketDto mkDetail(int mkNo);

    // 5. 상세 페이지 들어갈 때 조회수 증가
    void mkView(int mkNo);

    // 6. 글 수정/삭제 권한 확인

    // 7. 글 수정하기 (거래완료 제외)

    // 8. 글 삭제하기 (거래완료 제외)

    // 9. 게시물 댓글 조회

    // 10. 게시물 댓글 작성
}
