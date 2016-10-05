package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

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


        List<RoomEntity> rooms = em.createNativeQuery(
                "with " +
                        " dates (RoomEntity_id, bookedDates ) as ( select " +
                        "RoomEntity_id, bookedDates from RoomEntity_bookedDates )  " +

                        "select DISTINCT r.id " +
                        "from RoomEntity r, dates d " +
                        "where  " +
                        "d.RoomEntity_id = r.id " +
                        "and :bookedDates not in  " +
                        " ( select " +
                        "b.bookedDates from RoomEntity_bookedDates b " +
                        "where r.id =  b.RoomEntity_id )  " +

                        //    "where r.id = b.RoomEntity_id "
                        //     ") " +
                        "order by r.id ").setParameter("bookedDates", datesRange)
                .getResultList();


        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }

}
