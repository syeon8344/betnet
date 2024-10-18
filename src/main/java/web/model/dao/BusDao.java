package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import web.model.dto.BusDto;

@Mapper
public interface BusDao {
    boolean busLog(BusDto busDto);

    boolean busReservation(BusDto busDto);

    int busPurchase(BusDto busDto);
}

