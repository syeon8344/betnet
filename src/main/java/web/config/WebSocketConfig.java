package web.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import web.controller.ChatController;

@Configuration // 해당 클래스를 스프링 컨테이너 빈 등록
@EnableWebSocket // 웹소켓 빈 등록
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChatController chatcontroller;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler( chatcontroller , "/chat/conn" );
        //registry.addHandler( 컨트롤러객체 , "ws매핑주소정의" );
    }
}