package web.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MarketDto {
    private int mkId;           // 번호
    private String mkTitle;      // 제목
    private String mkContent;    // 내용
    // MultipartFile - HTML의 INPUT TYPE이 "file"일때 사용되는 인터페이스
    private List<MultipartFile> uploadFiles;   // 첨부파일(들)
    // DB에 저장/출력할 업로드된 파일명(들) 필드
    private List<String> fileNames;
    private int mkView;         // 조회수
    private String mkDate;       // 작성일
}
