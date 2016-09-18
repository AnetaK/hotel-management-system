package pl.excercise.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.model.room.RoomType;
import pl.excercise.web.HotelParamsCache;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class FindAvailableRooms {

    private static final Logger LOGGER = LogManager.getLogger(FindAvailableRooms.class);

    @PersistenceContext
    EntityManager em;

    public List<RoomEntity> find(ParametrizedRoom parametrizedRoom) {
        LOGGER.trace("Searching in DB rooms for parameters" + parametrizedRoom.toString());
        List<RoomEntity> rooms = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(parametrizedRoom.getAvailableFrom());
        LocalDate endDate = LocalDate.parse(parametrizedRoom.getAvailableTo());
        long daysBetween = DAYS.between(startDate, endDate);
        LOGGER.trace("NumberOfDays for calculation" + daysBetween);

        for (long i = 0; i < daysBetween + 1; i++) {

            LocalDate date = startDate.minusDays(i);
            List<RoomEntity> roomsTmp = em.createQuery("select r from RoomEntity r " +
                    "where r.roomType=:roomType " +
                    "and r.windowsExposure=:windowsExposure " +
                    "and not :date member of r.bookedDates ")
                    .setParameter("roomType", parametrizedRoom.getRoomType())
                    .setParameter("windowsExposure", parametrizedRoom.getWindowsExposure())
                    .setParameter("date", date.toString())
                    .getResultList();

            LOGGER.trace("Rooms tmp: " + roomsTmp.toString());

            if (i == 0) {
                rooms.addAll(roomsTmp);

            } else {
                List<RoomEntity> collect = new ArrayList<>();
                LOGGER.trace("Rooms before lambda: " + rooms.toString());

                for (RoomEntity r :
                        roomsTmp) {

                    collect.addAll(rooms.stream()
                            .filter(r1 -> r1.equals(r))
                            .collect(Collectors.toList()));
                    collect.addAll(rooms.stream()
                            .filter(r1 -> r1.getBookedDates().equals(Collections.emptyList()))
                            .collect(Collectors.toList()));

                }
                rooms = collect;
            }

        }
        LOGGER.debug("Number of rooms that meet the conditions: " + rooms.size());
        LOGGER.trace("Found rooms: " + rooms.toString());

        return rooms;
    }
}
