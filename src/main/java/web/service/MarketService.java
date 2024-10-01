package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.model.dao.MarketDao;
import web.model.dto.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketService {

    @Autowired private MarketDao marketDao;
    @Autowired private MemberService memberService;
    @Autowired private FileService fileService;

    // 1. 글 불러오기
    public MarketPageDto mkReadAll(MarketPageDto dto){
        System.out.println("MarketService.mkReadAll");
        // 페이지당 표시할 게시물 수
        int pageSize = 15; // 하나의 페이지당 15개씩 표시
        dto.setPagesize(pageSize);
        System.out.println("pageSize = " + pageSize);
        // 페이지당 시작 레코드 번호
        int startRow = (dto.getPage()-1) * pageSize;
        System.out.println("startRow = " + startRow);
        dto.setStartrow(startRow);
        // 전체 게시물 수 (카테고리번호별, 검색조건별)
        MarketPageDto tempDto = MarketPageDto.builder()
                .mkstate(dto.getMkstate())
                .searchkeyword(dto.getSearchkeyword())
                .build();
        int totalBoardSize = marketDao.getTotalBoardSize(tempDto);
        System.out.println("totalBoardSize = " + totalBoardSize);
        dto.setTotalboardsize(totalBoardSize);
        // 전체 페이지 수 : 전체게시물수 / 페이지당게시물수
        // e.g. 총 게시물 수 23개, 페이지당 5개 게시물 출력 : 4+1 5페이지
        int totalPage = totalBoardSize % pageSize == 0 ? totalBoardSize / pageSize : totalBoardSize / pageSize + 1;
        dto.setTotalpage(totalPage);
        System.out.println("totalPage = " + totalPage);

        System.out.println("dto = " + dto);

        // 6. 게시물 정보 조회
        List<MarketDto> data = marketDao.mkReadAll(dto);
        for(MarketDto d : data){
            List<String> filenames = marketDao.getFilenames(d.getMkid());
            d.setFilenames(filenames);
        }
        dto.setData(data);
        int btnSize = 10; // 페이지당 최대 버튼 수
        int startBtn = (((dto.getPage()-1)/btnSize)*btnSize + 1); // 페이지별 시작 버튼 번호 변수
        dto.setStartbtn(startBtn);
        int endBtn = startBtn + btnSize - 1; // 페이지의 끝버튼
        if(endBtn >= totalPage) {endBtn = totalPage;} // 끝 번호가 마지막이면
        dto.setEndbtn(endBtn);

        return dto;
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
        int fileNum = marketDto.getUploadfiles().size();
        ArrayList<MultipartFile> files = (ArrayList<MultipartFile>) marketDto.getUploadfiles();
        // 1. 첨부파일 개수만큼 반복문 돌리기
        for (int i = 0; i < files.size(); i++){
            // 2. 각 첨부파일마다 업로드메서드 대입
            String fileName = fileService.uploadImage(files.get(i), marketDto.getMkid(), i);
            if(fileName != null){
                // 3. 업로드된 파일명을 리스트에 담기 (DB에 파일명 저장)
                fileNames.add(fileName);
            }
        };

        // 첨부파일명 목록을 테이블에 저장 후 나머지 저장
        if (marketDao.mkWriteFiles(fileNames)){
            // 작성자 회원코드
            marketDto.setMemberid(memberId);
            return marketDao.mkWrite(marketDto);
        } else {
            return false;
        }
    }

    // 3. 글 상세 페이지
    public MarketDto mkRead(int mkId){
        // 글 내용
        MarketDto dto = marketDao.mkRead(mkId);
        // 댓글
        List<MarketReplyDto> replyDtoList = marketDao.mkReadReply(mkId);
        // 이미지 파일명 목록
        List<String> filenames = marketDao.getFilenames(mkId);
        // DTO 포장 및 반환
        dto.setMkreplies(replyDtoList);
        dto.setFilenames(filenames);
        return dto;
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
                .mkid(mkId)
                .memberid(memberId)
                .build();

        return marketDao.mkCheck(dto) > 0;
    }

    // 6. 글 수정하기 (JS에서 권한 확인 후, 거래완료 제외)
    public boolean mkEdit(MarketDto marketDto){
        //로그인 체크
        MemberDto loginDto=memberService.loginCheck();
        int memberId;
        if (loginDto == null) {
            return false;
        } else {
            memberId = loginDto.getMemberid();
        }
        marketDto.setMemberid(memberId);
        return marketDao.mkEdit(marketDto);
    }

    // 7. 글 삭제하기 (JS에서 권한 확인 후, 거래완료 제외)
    public boolean mkDelete(int mkId){
        //로그인 체크
        MemberDto loginDto=memberService.loginCheck();
        int memberId;
        if (loginDto == null) {
            return false;
        } else {
            memberId = loginDto.getMemberid();
        }
        MarketDto dto = MarketDto.builder()
                .mkid(mkId)
                .memberid(memberId)
                .build();

        return marketDao.mkDelete(dto);
    }

    // 8. 게시물 댓글 작성
    public boolean mkWriteReply(MarketReplyDto replyDto){
        //로그인 체크
        MemberDto loginDto=memberService.loginCheck();
        int memberId;
        if (loginDto == null) {
            return false;
        } else {
            memberId = loginDto.getMemberid();
        }
        replyDto.setMkreplywriter(memberId);
        return marketDao.mkWriteReply(replyDto);
    }
}
