package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.MarketDto;
import web.service.MarketService;

@RestController
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    // 1. 글 불러오기

    // 2. 글 작성하기 + 파일첨부
    @PostMapping("/write")
    public boolean mkWrite(MarketDto marketDto){
        return marketService.mkWrite(marketDto);
    }

    // 3. 글 카테고리 불러오기

    // 4. 글 상세 페이지

    // 5. 상세 페이지 조회수 증가

    // 6. 글 수정/삭제 권한 확인

    // 7. 글 수정하기 (거래완료 제외)

    // 8. 글 삭제하기 (거래완료 제외)

    // 9. 게시물 댓글 조회

    // 10. 게시물 댓글 작성
}
