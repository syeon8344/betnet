package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.dao.BusDao;
import web.model.dto.BusDto;

@Service
public class BusService {
    @Autowired
    BusDao busDao;
    // 예약
    public boolean busLog(BusDto busDto){
        System.out.println("busDto = " + busDto);
        return busDao.busLog(busDto);
    }

}
