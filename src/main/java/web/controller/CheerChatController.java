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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CheerChatController extends TextWebSocketHandler {
    @Autowired
    private CheerChatService cheerChatService;

    // - 기존 마커들을 뿌려줄 소켓 멤버
    private Map<String ,List<WebSocketSession>> connectedList = new ConcurrentHashMap<>();
    // - 각 방에 입장한 멤버
    private Map<String, List<WebSocketSession>> roomUsers = new ConcurrentHashMap<>();

    // 1. 클라이언트가 서버 웹소켓에 접속 성공했을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // System.out.println("CheerChatController.afterConnectionEstablished");
        // System.out.println("session = " + session);
        // - 접속된 클라이언트 소켓을 컬렉션에 저장
        // connectedList.add(session);
        // - 현재 접속된 인원수
        // System.out.println("채팅 접속인원 : "+connectedList.size());
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
                boolean result = cheerChatService.saveChatRoom(jsonNode);
                if (result){
                    session.sendMessage(message);
                }
            }
            // 회원이 페이지에 들어갔을 때
            if ("read".equals(type)) {
                addUserMatch(session , message);
                List<CheerChatDto> list = cheerChatService.readCSV(jsonNode);
                System.out.println(list);
                String matchId = jsonNode.get("matchId").asText();
                // ObjectMapper를 사용하여 리스트를 JSON으로 변환
                String jsonResponse = objectMapper.writeValueAsString(list); // 리스트를 JSON 문자열로 변환

                for (String matchIdKey : connectedList.keySet()) {
                    if (matchId.equals(matchIdKey)) {
                        List<WebSocketSession> userSessions = roomUsers.get(matchIdKey);
                        for (WebSocketSession session2 : userSessions) {
                            session2.sendMessage(message); // 메시지 전송
                        }
                    }
                }   // for end
            }
            // 회원이 방에 들어갔을때
            if("alarm".equals(type)){
                addUserToRoom(session , message);
                String roomId = jsonNode.get("roomId").asText();
                for (String roomIdKey : roomUsers.keySet()) {
                    if (roomId.equals(roomIdKey)) {
                        List<WebSocketSession> userSessions = roomUsers.get(roomIdKey);
                        for (WebSocketSession session2 : userSessions) {
                            session2.sendMessage(message); // 메시지 전송
                        }
                    }
                }   // for end
            }
            // 채팅방에서 메세지를 보냈을 때
            if("msg".equals(type)){
                System.out.println(jsonNode);
                String roomId = jsonNode.get("roomId").asText();
                for (String roomIdKey : roomUsers.keySet()) {
                    if (roomId.equals(roomIdKey)) {
                        List<WebSocketSession> userSessions = roomUsers.get(roomIdKey);
                        for (WebSocketSession session2 : userSessions) {
                            session2.sendMessage(message); // 메시지 전송
                        }
                    }
                }   // for end
            }
            // 회원이 채팅을 나갔을 때
            if("out".equals(type)){
                System.out.println(jsonNode);
                String roomId = jsonNode.get("roomId").asText();
                boolean result = leaveRoom(roomId , session);
                if (result){
                    for (String roomIdKey : roomUsers.keySet()) {
                        if (roomId.equals(roomIdKey)) {
                            List<WebSocketSession> userSessions = roomUsers.get(roomIdKey);
                            for (WebSocketSession session2 : userSessions) {
                                session2.sendMessage(message); // 메시지 전송
                            }
                        }
                    }   // for end
                }   // if end
            }
        } catch (IOException e) {
            System.err.println("메시지 처리 중 오류 발생: " + e.getMessage());
            // 필요시 세션 종료 또는 재연결 등의 처리를 할 수 있습니다.
        } catch (Exception e) {
            System.err.println("예기치 않은 오류 발생: " + e.getMessage());
            // 다른 예외에 대해서도 처리
        }
    }

    // 방아이디 별로 멤버 저장하는 함수
    public void addUserToRoom(WebSocketSession session , TextMessage message) {
        System.out.println("session = " + session);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            String roomId = jsonNode.get("roomId").asText();
            String userName = jsonNode.get("userName").asText();
            roomUsers.computeIfAbsent(roomId, k -> new Vector<>()).add(session);
            System.out.println(roomUsers);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    // 경기별로 별로 멤버 저장하는 함수
    public void addUserMatch(WebSocketSession session , TextMessage message) {
        System.out.println("session = " + session);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            String matchId = jsonNode.get("matchId").asText();
            connectedList.computeIfAbsent(matchId, k -> new Vector<>()).add(session);
            System.out.println(connectedList);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    // 회원이 방에서 나갈때
    public boolean leaveRoom(String roomId, WebSocketSession session) {
        List<WebSocketSession> userSessions = roomUsers.get(roomId);
        if (userSessions != null) {
            // 세션을 리스트에서 제거
            userSessions.remove(session);
            System.out.println(roomUsers);

            // 만약 방에 더 이상 사용자가 없다면 방 자체를 삭제할 수 있습니다
            if (userSessions.isEmpty()) {
                roomUsers.remove(roomId);
            }
            return true;
        }
        return false;
    }
}   // CheerChatController end
