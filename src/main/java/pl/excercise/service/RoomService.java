package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        LocalDate localDate = LocalDate.parse(parametrizedRoom.getAvailableTo());
        List<String> datesRange = daysCount.returnDaysList(parametrizedRoom.getAvailableFrom(), localDate.toString());

        System.out.println("datesRange = " + datesRange.toString());

//        List<RoomEntity> rooms = em.createQuery(" select distinct r.id, r.roomType, r.windowsExposure " +
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

        List<String> datesRange1 = em.createQuery(" select distinct b from RoomEntity r left join r.bookedDates b where b not in :datesRange").setParameter("datesRange", datesRange)
                .getResultList();
        System.out.println("datesRange1.toString() = " + datesRange1.toString());

        String query = " ";
        for (String date :
                datesRange) {
            query = query + "\'" + date + "\' member of b and ";
        }


        List<RoomEntity> rooms2 = em.createQuery(" select distinct r from RoomEntity r  join r.bookedDates b where  :dummy not member of b and :dummy1 not member of b  ")
                .setParameter("dummy", "2016-05-10")
                .setParameter("dummy1", "2016-05-11")
                .getResultList();
        System.out.println("rooms2.toString() = " + rooms2.toString());

        if (query.endsWith("and ")) {
            query = query.substring(0, query.lastIndexOf("and") - 1) + " \'1\' = ";
        }
        System.out.println("query" + query);
        Query query1 = em.createQuery(query);
        Query query2 = em.createQuery(" select distinct r.id, r.roomType, r.windowsExposure " +
                "from  RoomEntity r left join r.bookedDates b " +
                "where " +
                " r.roomType = :roomType " +
                "and r.windowsExposure = :windowsExposure " +
                "and :query = \'1\' " +
                " order by r.id "
        )
                .setParameter("roomType", parametrizedRoom.getRoomType())
                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                .setParameter("query", query1);

        List<RoomEntity> rooms = em.createQuery(" select distinct r.id, r.roomType, r.windowsExposure " +
                "from  RoomEntity r left join r.bookedDates b " +
                "where " +
                " r.roomType = :roomType " +
                "and r.windowsExposure = :windowsExposure " +
                "and :query = \'1\' " +
                " order by r.id "
        )
                .setParameter("roomType", parametrizedRoom.getRoomType())
                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                .setParameter("query", query)
                .getResultList();

        System.out.println("datesRange1 = " + datesRange1.toString());

        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }

}
