package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public List<MarketDto> mkReadAll() {
        return marketDao.mkReadAll();
    }

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
        // 파일이름 리스트 및 길이
        List<String> fileNames = new ArrayList<>();
        int fileNum = marketDto.getUploadFiles().size();
        ArrayList<MultipartFile> files = (ArrayList<MultipartFile>) marketDto.getUploadFiles();
        // 1. 첨부파일 개수만큼 반복문 돌리기
        for (int i = 0; i < files.size(); i++){
            // 2. 각 첨부파일마다 업로드메서드 대입
            String fileName = fileService.uploadImage(files.get(i), marketDto.getMkId(), i);
            if(fileName != null){
                // 3. 업로드된 파일명을 리스트에 담기 (DB에 파일명 저장)
                fileNames.add(fileName);
            }
        };
        System.out.println("fileNames = " + fileNames);
        marketDto.setFileNames(fileNames);
        return marketDao.mkWrite(marketDto, memberId);
//        System.out.println("multipartfile = " + mFile);
//        System.out.println(mFile.getContentType());
//        System.out.println(mFile.getName());
//        System.out.println(mFile.getSize());
//        System.out.println(mFile.isEmpty());
    }

    // 3. 글 상세 페이지
    public MarketDto mkRead(int mkId){
        return marketDao.mkRead(mkId);
    }
    // 4. 상세 페이지 조회수 증가
    public boolean mkView(int mkId){
        return marketDao.mkView(mkId);
    }
    // 5. 글 수정/삭제 권한 확인
    public boolean mkCheck(int mkId){
        // 현재 로그인된 회원의 회원번호와 게시글 번호를 보내 true/false
        int memberId = 0;
        MemberDto loginDto = memberService.loginCheck();
        if (loginDto == null) {
            return false;
        } else {
            memberId=loginDto.getMemberid();
        }

        MarketDto dto = MarketDto.builder()
                .mkId(mkId)
                .memberId(memberId)
                .build();

        return marketDao.mkCheck(dto);
    }

    // 6. 글 수정하기 (거래완료 제외)

    // 7. 글 삭제하기 (거래완료 제외)

    // 8. 게시물 댓글 조회

    // 9. 게시물 댓글 작성

    // 10. 게시글 제목 검색
    public List<MarketDto> mkTitleSearch(String title){
        return marketDao.mkTitleSearch(title);
    }

}
