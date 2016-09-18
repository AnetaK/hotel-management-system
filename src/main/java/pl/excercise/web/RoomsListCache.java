package pl.excercise.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.room.Room;
import pl.excercise.service.RandomRooms;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
@Lock(LockType.READ)
public class RoomsListCache {

    private static final Logger LOGGER = LogManager.getLogger(HotelParamsCache.class);

    private List<Room> roomsList = new ArrayList<>();





    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void initialize() {

        for (Room r:
             roomsList) {
            em.persist(r);
        }

        LOGGER.debug("{} random rooms persisted to DP",roomsList.size());

    }

    public List<Room> getInitialRooms() {
        return roomsList;
    }


    public void createRooms(){
        Room room = new Room();

        for(int i=0;i<30;i++){
            room.withRoomType(new RandomRooms().getRandomType())
                    .withWindowsExposure(new RandomRooms().getRandomExposure())
                    .withBookedDates(new RandomRooms().getRandomDates())
                    .build();
        }

    }



}
