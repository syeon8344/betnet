package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.dto.BusDto;
import web.service.BusService;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired BusService busService;

    @PostMapping("/Reservation")
    public boolean busReservation(BusDto busDto){
        System.out.println("BusController.busReservation");
        System.out.println("busDto = " + busDto);
        return busService.busReservation(busDto);
    }

    @GetMapping("/check")
    public List<BusDto> busCheck(String gameCode){
        System.out.println("gameCode = " + gameCode);
        return busService.busCheck(gameCode);
    }



}
