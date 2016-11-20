package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.database.ReservationDB;
import pl.excercise.database.RoomDB;
import pl.excercise.model.Guest;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class ReservationService {

    private static final Logger LOGGER = LogManager.getLogger(ReservationService.class);

    @EJB
    ReservationDB reservationDB;

    @EJB
    RoomDB roomDB;

    public void createReservation(GuestSessionScoped guest, ParametrizedRoom room, long id) {

        DaysCount daysCount = new DaysCount();
        List<String> bookedDates = daysCount.returnDaysList(room.getAvailableFrom(), room.getAvailableTo());

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
                .withBookedTo(room.getAvailableTo())
                .withCancelledFlag(false)
                .build();

        System.out.println("reservation = " + reservation.toString());

        reservationDB.persistReservation(reservation);

        LOGGER.debug("Reservation persisted succesfully");

        RoomEntity oldRoomEntity = roomDB.extractRoomById(id);

        List<String> extractedBookedDates = oldRoomEntity.getBookedDates();
        LOGGER.trace("Booked dates number before update: " + extractedBookedDates.size());

        extractedBookedDates.addAll(bookedDates);

        roomDB.updateBookedDates(id, extractedBookedDates);

        RoomEntity newRoomEntity = roomDB.extractRoomById(id);
        LOGGER.trace("Booked dates number after update: " + newRoomEntity.getBookedDates().size());

    }

    public List<Reservation> extractReservationsForGuest(GuestSessionScoped guest) {
        List<Reservation> resultList = reservationDB.extractReservationsForGuest(guest);

        LOGGER.trace("Reservations number extracted from DB: " + resultList.size());

        if (resultList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return resultList;
    }

    public void cancelReservation(long id) {
        Reservation reservation = reservationDB.extractReservationById(id);

        DaysCount dates = new DaysCount();
        List<String> datesToRemove = dates.returnDaysList(reservation.getBookedFrom(), reservation.getBookedTo());

        long roomId = reservation.getRoom().getId();
        reservationDB.setCancelledFlag(id);

        Reservation newReservation = reservationDB.extractReservationById(id);
        LOGGER.trace("Cancelled flag: " + newReservation.getCancelledFlag());

        RoomEntity roomEntity = roomDB.extractRoomById(roomId);

        List<String> roomDates = roomEntity.getBookedDates();
        LOGGER.trace("Number of booked dates before cancelling: " + roomDates.size());

        roomDates.removeIf(r -> datesToRemove.contains(r));

        roomDB.updateBookedDates(roomEntity.getId(), roomDates);

        RoomEntity newRoomEntity = roomDB.extractRoomById(roomId);

        LOGGER.trace("Number of booked dates after cancelling: " + newRoomEntity.getBookedDates().size());

        LOGGER.trace("Reservation {} is cancelled", id);

    }


}
