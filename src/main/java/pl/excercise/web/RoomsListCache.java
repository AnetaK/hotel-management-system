package pl.excercise.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.service.RandomRooms;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
@Lock(LockType.READ)
public class RoomsListCache {

    private static final Logger LOGGER = LogManager.getLogger(HotelParamsCache.class);
    private static final int ROOMS_COUNT_INITIALLY_BOOKED = 30;
    private static final int ROOMS_COUNT = 30;

    private List<RoomEntity> roomsList = new ArrayList<>();


    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void initialize() {


        for (int i = 0; i < ROOMS_COUNT_INITIALLY_BOOKED; i++) {
            RoomEntity room = new RoomEntity();
            room.withRoomType(new RandomRooms().getRandomType())
                    .withWindowsExposure(new RandomRooms().getRandomExposure())
                    .withBookedDates(new RandomRooms().getRandomDates())
                    .build();

            em.persist(room);

            roomsList.add(room);
        }
        List<String> dates = new ArrayList<>();
        LocalDate now = LocalDate.now();
        dates.add(now.minusDays(1).toString()); // passed date to initialise RoomEntity_bookedDates

        for (int i = 0; i < ROOMS_COUNT; i++) {
            RoomEntity room = new RoomEntity();
            room.withRoomType(new RandomRooms().getRandomType())
                    .withWindowsExposure(new RandomRooms().getRandomExposure())
                    .withBookedDates(dates)
                    .build();

            em.persist(room);

            roomsList.add(room);
        }

        LOGGER.debug("{} random rooms persisted to DB", roomsList.size());

    }

    public List<RoomEntity> getInitializedRooms() {
        return roomsList;
    }


}
