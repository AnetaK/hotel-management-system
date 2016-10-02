package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        List<RoomEntity> rooms = em.createNativeQuery(" select distinct r.id, r.roomType, r.windowsExposure " +
                "from  RoomEntity r, RoomEntity_bookedDates b " +
                "where b.bookedDates not in  :datesRange " +
                //        "where :datesRange <> any (select from b )  " +
                        // TODO: 02.10.16 subsety nie dzialają zupełnie dobrze - nie bierze skrajnych dat
                "and r.id = b.RoomEntity_id " +
                "and r.roomType = :roomType " +
                "and r.windowsExposure = :windowsExposure " +
                "order by r.id "
                ,RoomEntity.class)
                .setParameter("roomType", parametrizedRoom.getRoomType())
                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                .setParameter("datesRange",datesRange)
                .getResultList();

        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }

}
