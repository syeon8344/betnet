package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.MarketDao;
import web.model.dto.MemberDto;
import web.model.dto.MarketDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketService {

    @Autowired private MarketDao marketDao;
    @Autowired private MemberService memberService;
    @Autowired private FileService fileService;

    // 1. 글 불러오기

    // 2. 글 작성하기 + 파일첨부
    public boolean mkWrite(MarketDto marketDto){
        //로그인 체크
        MemberDto loginDto=memberService.loginCheck();
        int memberId;
        if (loginDto == null) {
            return false;
        } else {
            memberId=loginDto.getMemberid();
        }
        // 첨부파일 여러개 업로드하기
        // 파일이름 리스트
        List<String> fileNames = new ArrayList<>();
        // 1. 첨부파일 개수만큼 반복문 돌리기
        marketDto.getUploadFiles().forEach(file ->{
            // 2. 각 첨부파일마다 업로드메서드 대입
            String fileName = fileService.uploadFile(file);
            if(fileName != null){
                // 3. 업로드된 파일명을 리스트에 담기 (DB에 파일명 저장)
                fileNames.add(fileName);
            }

        });
        System.out.println("fileNames = " + fileNames);
        marketDto.setFileNames(fileNames);
        return marketDao.mkWrite(marketDto, memberId);
//        System.out.println("multipartfile = " + mFile);
//        System.out.println(mFile.getContentType());
//        System.out.println(mFile.getName());
//        System.out.println(mFile.getSize());
//        System.out.println(mFile.isEmpty());
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