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
        // 페이지당 표시할 게시물 수
        int pageSize = dto.getPagesize(); // 하나의 페이지당 10개씩 표시
        // 페이지당 시작 레코드 번호
        int startRow = (dto.getPage()-1) * pageSize;
        // 전체 게시물 수 (카테고리번호별, 검색조건별)
        int totalBoardSize = marketDao.getTotalBoardSize(dto.getMkstate(), dto.getSearchkeyword());
        // 전체 페이지 수 : 전체게시물수 / 페이지당게시물수
        // e.g. 총 게시물 수 23개, 페이지당 5개 게시물 출력 : 4+1 5페이지
        int totalPage = totalBoardSize % pageSize == 0 ? totalBoardSize / pageSize : totalBoardSize / pageSize + 1;
        // 6. 게시물 정보 조회
        List<MarketDto> data = marketDao.mkReadAll(dto.getMkstate(), startRow, pageSize, dto.getSearchkeyword());
        int btnSize = 10; // 페이지당 최대 버튼 수
        int startBtn = (((dto.getPage()-1)/btnSize)*btnSize + 1); // 페이지별 시작 버튼 번호 변수
        int endBtn = startBtn + btnSize - 1; // 페이지의 끝버튼
        if(endBtn >= totalPage) {endBtn = totalPage;} // 끝 번호가 마지막이면
        // 반환 객체
        return MarketPageDto.builder()
                .page(dto.getPage()) // 현재 페이지 번호
                .totalboardsize(totalBoardSize) // 전체 게시물 수
                .totalpage(totalPage) // 전체 페이지 수
                .data(data) // 조회된 게시물 목록
                .startbtn(startBtn) // 페이지 표시되는 시작 버튼
                .endbtn(endBtn) // 페이지에 표시되는 끝 버튼
                .build();
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
        marketDto.setFilenames(fileNames);
        return marketDao.mkWrite(marketDto, memberId);
    }

    // 3. 글 상세 페이지
    public MarketDto mkRead(int mkId){
        // 글 내용
        MarketDto dto = marketDao.mkRead(mkId);
        // 댓글
        List<MarketReplyDto> replyDtoList = marketDao.mkReadReply(mkId);
        dto.setMkreplies(replyDtoList);
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

        return marketDao.mkCheck(dto);
    }

    // 6. 글 수정하기 (거래완료 제외)
    public boolean mkEdit(MarketDto marketDto){

        return marketDao.mkEdit(marketDto);
    }



    // 7. 글 삭제하기 (거래완료 제외)
    public boolean mkDelete(int mkId){
        //로그인 체크
        MemberDto loginDto=memberService.loginCheck();
        int memberId;
        if (loginDto == null) {
            return false;
        } else {
            memberId = loginDto.getMemberid();
        }
        return marketDao.mkDelete(memberId, mkId);
    }

    // 8. 게시물 댓글 작성
    public boolean mkWriteReply(MarketReplyDto replyDto){
        //System.out.println("map = " + map);
        //        // no는 입력받지 않는다
        //            // HTTP 세션에서 가져오기 (회원제 댓글이라는 가정)
        //            // 세션 : 서버에 저장되는 임시저장소, 서버 종료나 세션 초기화시 사라지므로 매번 로그인할 때 생성되는 방식으로 적합
        //
        //        // 로그인 체크 및 no 얻어오기
        //        Object object = memberService.mLoginCheck();
        //            // 세션은 무조건 Object타입으로 저장
        //        if (object==null){return false;}
        //        MemberDto loginDto = (MemberDto) object;
        //        int no = loginDto.getNo();
        //        map.put("no", String.valueOf(no));// map 제네릭이 String : String이므로 int no를 String변환
        return marketDao.mkWriteReply(replyDto);
    }
}
