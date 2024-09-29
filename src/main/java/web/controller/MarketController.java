package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MarketDto;
import web.service.MarketService;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    // 1. 글 불러오기
    @GetMapping("/readall")
    public List<MarketDto> mkReadAll(){
        return marketService.mkReadAll();
    }

    // 2. 글 작성하기 + 파일첨부
    @PostMapping("/write")
    public boolean mkWrite(MarketDto marketDto){
        return marketService.mkWrite(marketDto);
    }

    // 3. 글 상세 페이지
    @GetMapping("/read")
    public MarketDto mkRead(@RequestParam("mkid") int mkId){
        return marketService.mkRead(mkId);
    }

    // 4. 상세 페이지 조회수 증가
    @PostMapping("/view")
    public boolean mkView(@RequestParam("mkid") int mkId){
        return marketService.mkView(mkId);
    }

    // 5. 글 수정/삭제 권한 확인
    @GetMapping("/check")
    public boolean mkCheck(@RequestParam("mkid") int mkId){
        return marketService.mkCheck(mkId);
    }

    // 6. 글 수정하기 (거래완료 제외)

    // 7. 글 삭제하기 (거래완료 제외)

    // 8. 게시물 댓글 조회

    // 9. 게시물 댓글 작성

    // 10. 게시글 제목 검색
    public List<MarketDto> mkTitleSearch(String title){
        return marketService.mkTitleSearch(title);
    }
}
