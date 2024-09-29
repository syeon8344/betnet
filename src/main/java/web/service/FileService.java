package web.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

@Service
public class FileService {
    /*
        프로젝트명
            - build 폴더 : 배포된 클래스/파일 등
            - src 폴더 : 배포 전 개발자가 코드 및 파일 작성하는 곳
        빌드 : 내 코드를 웹서버에 올림/빌드함으로써 외부(클라이언트)로부터 리소스 제공 가능
    */
    // 2. 저장할 경로 만들기 (전역변수) * 개발자 코드 프로젝트의 upload 폴더 != 내장 웹서버의 upload 폴더
        // build 폴더 : 빌드된 웹서버의 폴더
    // 개발자 코드 프로젝트의 upload 폴더 (src->upload)
    // String uploadPath = "C:\\Users\\tj-bu-703-06\\Desktop\\TJ_2024A_Spring\\src\\main\\resources\\static\\upload\\";
    // 내장 웹서버의 upload 폴더 위치 (build->upload)

    static String uploadPath = "\\build\\resources\\main\\static\\upload\\";

    // static 블록: 클래스가 로드될 때 한번만 실행된다.
    static{
        // uploadPath 주소 폴더가 없을 때 생성하기
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }
    }

    // TODO: 로직 체크
    // 이미지 파일 업로드: 파일의 바이트가 저장된 MultipartFile 인터페이스를 받아 저장후 파일명 반환
    public String uploadImage(MultipartFile image, int mkId, int fileNum) {

        System.out.println(image.getOriginalFilename());
        System.out.println(image.getContentType());
        System.out.println(image.getSize());
        // 이미지 파일인지 확인
        if (image.getContentType() != null && image.getContentType().startsWith("image/")) {
            // TODO: 이미지 파일 확장자를 찾아 파일명에 조합
//                int lastIndexOfDot = image.getOriginalFilename().lastIndexOf(".");
//                String extension = image.getContentType().substring(lastIndexOfDot)
//                // 확장자가 없는 경우
//                if (lastIndexOfDot == -1) {
//                    return ""; // 확장자가 없음
//                }
//
//                // 확장자 반환
//                return fileName.substring(lastIndexOfDot);
            // 1. 파일명 생성: image_{mkid}_{1~3번째 첨부파일}
            String fileName = String.format("image_%d_%d", mkId, fileNum);
            // 2. UUID + 파일명 합치기, UUID와 파일명 사이에 구분 문자 조합, 파일명에 구분문자가 존재하면 안된다("_" 등
            // 후에 구분 문자 기준으로 분해하면 [0]UUID, [1]순수파일명
            // .replaceAll(기존문자,새문자)
            // fileName = uuid + "_" + fileName.replaceAll("_", "-"); // 파일명에 _가 존재하면 -로 변경 -> _를 구분문자로 쓰기 위해
            System.out.println("fileName = " + fileName);
            // 3. 저장할 경로와 파일명 합치기
            String filePath = uploadPath + fileName;
            // 4. 해당 경로로 설정한 file 객체, transferTo(file객체)
            File file = new File(filePath);
            // 5. transferTo(file객체) : file객체내 설정한 해당 경로로 파일 복사/저장/이동
            // 일반예외 예외처리 필요
            System.out.println("file = " + file);
            try {
                image.transferTo(file);
                return fileName;
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Autowired private HttpServletRequest request;  // HTTP 요청 객체, HTTP로 요청이 들어온 정보와 기능 포함
    @Autowired private HttpServletResponse response; // HTTP 응답 객체, HTTP로 응답할 때의 정보와 기능 포함

    // [2] 파일 다운로드
    public void fileDownload(String filename) {
        System.out.println("FileService fileDownload");
        System.out.println("filename = " + filename);
        // 1. 다운로드할 경로 설정 (uploadPath)
            // - 업로드된 경로와 다운로드할 파일명 조합
        String downloadPath = uploadPath + filename;
        // - File 클래스는 file 관련된 다양한 메서드를 제공
            // .exists() : 파일 존재여부 true/false
            // .length() : 파일이 있으면 파일 용량을 바이트 개수로 반환 (파일 용량 확인)
        File file = new File(downloadPath);
        // 해당 경로 파일이 존재하면 true, 아니면 false -> false시 return
        if (!file.exists()){return;}
        // 2. 해당 다운로드할 파일을 JAVA로 바이트 형식으로 읽어들이기
            // - 스트림 : JAVA 외부와 통신시 바이트 데이터가 다니는 통로
            // - InputStream : 읽어들이는 통로, OutputStream : 내보내는 통로
            // - Buffered : 버퍼, 특정 위치로 이동하는 동안 잠시 데이터를 보관하는 메모리
        try {
            // ++++++++++++++++++++ 배열을 바이트 배열로 읽어오기 +++++++++++++++++++++
            // 2-1. 파일 입력 스트림 객체 생성
            // BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downloadPath));
            FileInputStream fis = new FileInputStream(downloadPath);
            // 2-2. 파일 용량만큼 배열 선언 (여러개의 바이트가 한 파일)
            long fileSize = file.length();
            byte[] bytes = new byte[(int)fileSize];
            fis.read(bytes); // 경로에 해당하는 파일을 바이트로 가져오기
            fis.close();
            System.out.println(Arrays.toString(bytes));
            // +++++++++++++++++++++++ 바이트 배열을 HTTP 바이트 형식으로 응답하기 +++++++++
            // [3] HTTP 스트림으로 응답하기
            // 3-1 출력 스트림 객체 생성, new BufferedOutputStream (출력할 대상의 스트림 객체)
            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(downloadPath));
            ServletOutputStream sos = response.getOutputStream();
            // HTTP 응답의 헤더 속성 추가 : .setHeader(key,value)
                // Content-Disposition : 브라우저가 제공하는 다운로드 형식
                // attachment;filename="다운로드에 표시할 파일명"
                    // URLEncoder.encode() : URL 경로상의 한글을 인코딩
            String originalFilename = filename.split("_")[1];
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(originalFilename, "UTF-8"));
            // 3-2 바이트 배열 내보내기/출력/쓰기
            sos.write(bytes);
            // 쓰고 난 후 버퍼 닫기
            sos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // [3] 파일 삭제 (수정에서도 사용)
    public void deleteFile(String oldFileName){
        File file = new File(uploadPath + oldFileName);
        file.delete(); //TODO
    }


}
