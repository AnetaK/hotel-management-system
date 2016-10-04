package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class RoomService {

    private static final Logger LOGGER = LogManager.getLogger(RoomService.class);

    @PersistenceContext
    EntityManager em;

    public List<RoomEntity> findAllRooms() {
        List<RoomEntity> rooms = em.createQuery("select r from RoomEntity r ")
                .getResultList();

        LOGGER.debug("Result list size = " + rooms.size());
        return rooms;
    }


    public List<RoomEntity> findAvailableRooms(ParametrizedRoom parametrizedRoom) {
        LOGGER.trace("Searching in DB rooms for parameters" + parametrizedRoom.toString());

        DaysCount daysCount = new DaysCount();
        List<String> datesRange = daysCount.returnDaysList(parametrizedRoom.getAvailableFrom(), parametrizedRoom.getAvailableTo());

        System.out.println("datesRange = " + datesRange.toString());

        List<RoomEntity> rooms = em.createQuery(" select distinct r " +
                "from  RoomEntity r left join r.bookedDates b " +
                "where r.roomType = :roomType " +
                "and r.windowsExposure = :windowsExposure " +
                "order by r.id "
                 )
                .setParameter("roomType", parametrizedRoom.getRoomType())
                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                .getResultList();

        for (String date :
                datesRange) {
            rooms.removeIf(r -> r.getBookedDates().contains(date));
        }

//        List<RoomEntity> rooms = em.createQuery(" select distinct r " +
//                "from  RoomEntity r left join r.bookedDates b " +
//                "where b not in :datesRange  " +
//                "and r.roomType = :roomType " +
//                "and r.windowsExposure = :windowsExposure " +
//                "order by r.id "
//        )
//                .setParameter("roomType", parametrizedRoom.getRoomType())
//                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
//                .setParameter("datesRange", datesRange)
//                .getResultList();
// TODO: 04.10.16 zapytanie sql bez dodatkowej operacji z listą

        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }

}
