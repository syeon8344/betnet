package web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.service.CheerChatService;

import java.util.List;
import java.util.Vector;

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
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        // 메세지 내용 DB 처리 : 메시지 정보를 DTO화 해서 ---> 서비스 ---> 다오
        // - 특정한 세션으로  받은 메시지 내용들 현재 접속된 다른 세션에게도 전송
        // 타입이 마커이면 해당 서비스로 보내기 -> 서비스는 csv 파일 저장하는
        // ObjectMapper를 사용하여 JSON을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        // 타입 가져오기
        String type = jsonNode.get("type").asText();

        if(type.equals("marker")){
            System.out.println("메시지 타입: " + type);
        }

        for (int i = 0; i < connectedList.size(); i++) {
            WebSocketSession s = connectedList.get(i);
            s.sendMessage(message);
        }   // for end
    }
}   // CheerChatController end
