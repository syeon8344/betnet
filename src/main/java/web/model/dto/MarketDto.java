package web.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MarketDto {
    private int mkNo;           // 번호
    private String mkTitle;      // 제목
    private String mkContent;    // 내용
    // - HTML의 INPUT TYPE이 "file"일때 사용되는 인터페이스
    // [1] 업로드시 바이트를 저장하고 있는 필드
    private List<MultipartFile> uploadFiles;       // 첨부파일
    // [2] DB에 저장/출력할 업로드된 파일명 필드
    private List<String> fileNames;
    private int mkView;         // 조회수
    private String mkDate;       // 작성일
    // 카테고리
    private int cNo;          // 카테고리 번호
    private String cName;        // 카테고리 이름
    // 회원
    private int no;            // 작성자 번호
    private String id;          // 작성자 아이디
}
