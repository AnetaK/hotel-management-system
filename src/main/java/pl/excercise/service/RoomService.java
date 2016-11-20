package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.database.RoomDB;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RoomService {

    private static final Logger LOGGER = LogManager.getLogger(RoomService.class);

    @EJB
    RoomDB roomDB;

    public List<RoomEntity> findAllRooms() {
        List<RoomEntity> rooms = roomDB.extractRooms();

        LOGGER.debug("Result list size = " + rooms.size());
        return rooms;
    }


    public List<RoomEntity> findAvailableRooms(ParametrizedRoom parametrizedRoom) {
        LOGGER.trace("Searching in DB rooms for parameters" + parametrizedRoom.toString());

        DaysCount daysCount = new DaysCount();
        List<String> datesRange = daysCount.returnDaysList(parametrizedRoom.getAvailableFrom(), parametrizedRoom.getAvailableTo());

        System.out.println("datesRange = " + datesRange.toString());

        List<RoomEntity> rooms = roomDB.extractAvailableRooms(datesRange, RoomType.valueOf(parametrizedRoom.getRoomType()), WindowsExposure.valueOf(parametrizedRoom.getWindowsExposure()));


        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }

}
