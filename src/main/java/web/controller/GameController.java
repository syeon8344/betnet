package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dao.GameDao;
import web.model.dto.GameDto;
import web.service.GameService;

import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameService gameService;

    // 게임 구매
    @PostMapping("/purchase")
    public int gamePeurchase(@RequestBody GameDto gameDto){
        System.out.println("GameController.gamePeurchase");
        System.out.println("gameDto = " + gameDto);
        return gameService.gamePeurchase(gameDto);
    }   // end method gamePurchase
}   // class GameController
