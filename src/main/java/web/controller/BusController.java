package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.dto.BusDto;
import web.service.BusService;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired BusService busService;

    // 예약로그
    @PostMapping
    public boolean busLog(BusDto busDto){
        System.out.println("busDto = " + busDto);
        return busService.busLog(busDto);
    }
}
