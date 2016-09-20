package pl.excercise.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.Guest;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class PersistReservation {

    private static final Logger LOGGER = LogManager.getLogger(PersistReservation.class);

    @PersistenceContext
    EntityManager em;

    public void persist(GuestSessionScoped guest, ParametrizedRoom room, int id){

        LocalDate startDate = LocalDate.parse(room.getAvailableFrom());
        LocalDate endDate = LocalDate.parse(room.getAvailableTo());
        long daysBetween = DAYS.between(startDate, endDate);
        LOGGER.trace("NumberOfDays for calculation" + daysBetween);

        List<String> bookedDates = new ArrayList<>();

        for (int i = 0; i < daysBetween; i++) {
            String date = startDate.plusDays(i).toString();
            bookedDates.add(date);
        }

        RoomEntity roomEntity = new RoomEntity()
                .withRoomType(room.getRoomType())
                .withWindowsExposure(room.getWindowsExposure())
                .withBookedDates(bookedDates)
                .build();

        roomEntity.setId(id);

        Reservation reservation = new Reservation()
                .withGuest(new Guest().withFirstName(guest.getFirstName()).withLastName(guest.getLastName()).build())
                .withRoom(roomEntity)
                .withBookedFrom(room.getAvailableFrom())
                .withBookedTo(room.getAvailableTo());

        System.out.println("reservation = " + reservation.toString());

        em.persist(reservation);

        LOGGER.debug("Reservation persisted succesfully");
// TODO: 20.09.16 dodaj wpis w datach - room entity

    }


}
