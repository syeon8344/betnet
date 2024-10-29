package web.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Vector;

@Component
public class BusSocketController extends TextWebSocketHandler {

    private List<WebSocketSession> 버스클라이언트소켓 = new Vector<>();

    // 1. 클라이언트가 서버 웹소켓에 접속 성공 했을때 # Established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        버스클라이언트소켓.add(session);
    }

    // 2. 클라이언트가 서버 웹소켓에 접속 끊었을때. # Closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("session = " + session);
        System.out.println("[서버웹소켓 측] JS 클라이언트 웹소켓 이 나감");
        // - 접속된 클라이언소켓을 접속명단에서 제외
        버스클라이언트소켓.remove( session );
        // - 현재 접속된 인원수
        System.out.println( "서버소켓의 접속 인원 : " + 버스클라이언트소켓.size() );
        // - 퇴장/제거 한 소켓(세션)을 제외한 다른 클라이언트소켓(세션) 들에게 메시지 전송
        // - 클라이언트소켓의 정보를 세션 에서 저장하고 있다.
        TextMessage textMessage = new TextMessage("Hello , ClientSocket ");
        handleTextMessage( null , textMessage );

    }


}
