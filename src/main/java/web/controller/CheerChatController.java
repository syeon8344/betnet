package web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.model.dto.CheerChatDto;
import web.service.CheerChatService;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

@RequestMapping("/cheer")
@RestController
@Component
public class CheerChatController extends TextWebSocketHandler {
    @Autowired
    private CheerChatService cheerChatService;

    // - 클라이언트 소켓들의 접속 명단을 저장하는 컬렉션 프레임워크
    private List<WebSocketSession> connectedList = new Vector<>();

    // 1. 클라이언트가 서버 웹소켓에 접속 성공했을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // - 접속된 클라이언트 소켓을 컬렉션에 저장
        connectedList.add(session);
        // - 현재 접속된 인원수
        System.out.println("채팅 접속인원 : "+connectedList.size());
        // - 소켓에 접속했을때 채팅방 csv불러와서 뿌려주기...
    }   // afterConnectionEstablished end

    // 2. 클라이언트가 서버 웹소켓에 접속 끊었을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connectedList.remove(session);
        // - 현재 접속된 인원수
        // System.out.println("채팅 접속인원 : "+connectedList.size());
        // - 퇴장/제거한 세션을 제외한 다른 클라이언트소켓들에게 메시지 전송
        // - 서버에서 클라이언트에게 메세지 전송하기
        TextMessage textMessage = new TextMessage("Hello , ClientSocket");    // 메시지 내용 구성
        handleTextMessage(null, textMessage);   // 메시지 전송함수
    }   // afterConnectionClosed end

    @Override
    public void handleTextMessage(@Payload WebSocketSession session, @Payload TextMessage message) throws Exception{
        System.out.println("CheerChatController.handleTextMessage");
        System.out.println("session = " + session + ", message = " + message);
        // 타입이 마커이면 해당 서비스로 보내기 -> 서비스는 csv 파일 저장하는
        // ObjectMapper를 사용하여 JSON을 파싱
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            // 메시지 타입 체크
            String type = jsonNode.get("type").asText();
            if("marker".equals(type)){
                // 서비스 호출
                cheerChatService.saveChatRoom(jsonNode);
            }if ("read".equals(type)) {
                List<CheerChatDto> list = cheerChatService.readCSV();
                System.out.println(list);

                // ObjectMapper를 사용하여 리스트를 JSON으로 변환
                String jsonResponse = objectMapper.writeValueAsString(list); // 리스트를 JSON 문자열로 변환

                for (int i = 0; i < connectedList.size(); i++) {
                    // 2. 목록에 저장된 하나의 세션 호출
                    WebSocketSession s = connectedList.get(i);
                    // 3. 꺼낸 클라이언트 소켓 정보에 메시지를 보내기
                    s.sendMessage(new TextMessage(jsonResponse)); // JSON 문자열을 전송
                } // for end
            }
            else{
                for( int i = 0 ; i<connectedList.size() ; i++ ){
                    // 2. 목록에 저장된 하나의 세션 호출
                    WebSocketSession s = connectedList.get(i);
                    // 3. 꺼낸 클라이언소켓 정보에 메시지를 보내기
                    s.sendMessage( message );
                } // for end
            }
        } catch (IOException e) {
            System.err.println("메시지 처리 중 오류 발생: " + e.getMessage());
            // 필요시 세션 종료 또는 재연결 등의 처리를 할 수 있습니다.
        } catch (Exception e) {
            System.err.println("예기치 않은 오류 발생: " + e.getMessage());
            // 다른 예외에 대해서도 처리
        }
    }

}   // CheerChatController end
