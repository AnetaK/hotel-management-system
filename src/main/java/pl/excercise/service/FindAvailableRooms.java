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

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class FindAvailableRooms {

    private static final Logger LOGGER = LogManager.getLogger(FindAvailableRooms.class);

    @PersistenceContext
    EntityManager em;

    public List<RoomEntity> find(ParametrizedRoom parametrizedRoom) {
        LOGGER.trace("Searching in DB rooms for parameters" + parametrizedRoom.toString());

        List<RoomEntity> rooms = em.createQuery("select r from RoomEntity r " +
                "where r.roomType=:roomType " +
                "and r.windowsExposure=:windowsExposure ")
                .setParameter("roomType", parametrizedRoom.getRoomType())
                .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                .getResultList();

        LOGGER.debug("Number of rooms that meet the type and exposure conditions: " + rooms.size());

        LocalDate startDate = LocalDate.parse(parametrizedRoom.getAvailableFrom());
        LocalDate endDate = LocalDate.parse(parametrizedRoom.getAvailableTo());
        long daysBetween = DAYS.between(startDate, endDate);
        LOGGER.trace("NumberOfDays for calculation" + daysBetween);

        for (int i = 0; i < daysBetween + 1; i++) {
            String date = startDate.plusDays(i).toString();

            rooms.removeIf(r -> r.getBookedDates().contains(date));
        }

        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }
}
