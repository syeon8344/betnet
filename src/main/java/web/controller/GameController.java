package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dao.GameDao;
import web.model.dto.GameDto;
import web.model.dto.SearchDto;
import web.service.GameService;

import java.util.List;
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

    // 게임 리스트 출력
    @GetMapping("/getlist")
    public List<GameDto> getlist(SearchDto searchDto){
        return gameService.getlist(searchDto);
    }   // getlist() end

    // 게임 상세 출력
    @GetMapping("/detail")
    public List<GameDto> getDetail(GameDto gameDto){
        return gameService.getDetail(gameDto);
    }   // getDetail() end

    // 게임구매시 내가 구매한 이력이 있는 경기인지 판단
    @GetMapping("/ispurchased")
    public boolean isPurchased(GameDto gameDto){
        System.out.println("GameController.isPurchased");
        System.out.println("gameDto = " + gameDto);
        return gameService.isPurchased(gameDto);
    }   // isPurchased() end

}   // class GameController
