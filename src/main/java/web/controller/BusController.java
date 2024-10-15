package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.BusService;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired private BusService busService;
}
