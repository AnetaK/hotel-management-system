package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.Guest;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.ReservationDate;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Stateless
public class ReservarionService {

    private static final Logger LOGGER = LogManager.getLogger(ReservarionService.class);

    @PersistenceContext
    EntityManager em;

    public List<Reservation> extractReservationsForGuest(GuestSessionScoped guest) {
        List<Reservation> resultList = em.createQuery("select r from Reservation r " +
                "where r.guest.firstName=:firstName and r.guest.lastName=:lastName ")
                .setParameter("firstName", guest.getFirstName())
                .setParameter("lastName", guest.getLastName())
                .getResultList();


        LOGGER.trace("Reservations number extracted from DB: " + resultList.size());

        if (resultList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return resultList;
    }

    public void cancelReservation(long id, List<ReservationDate> datesToCancel) {

        em.createQuery("update Reservation set cancelledFlag = true where id=:id ")
                .setParameter("id", id)
                .executeUpdate();

        Reservation reservation = em.find(Reservation.class, id);

        LOGGER.trace("Reservation cancelled flag = " + reservation.getCancelledFlag());

        RoomEntity roomEntity = reservation.getRoom();

        List<ReservationDate> roomDates = roomEntity.getBookedDates();
        LOGGER.trace("Number of booked dates before cancelling: " + roomDates.size());

        roomDates.removeIf(r -> datesToCancel.contains(r));
        LOGGER.trace("Number of booked dates after cancelling: " + roomDates.size());

        updateBookedDates(roomEntity.getId(), roomDates);

        LOGGER.trace("Reservation {} is cancelled", id);

    }

    public void createReservation(GuestSessionScoped guest, ParametrizedRoom room, long id) {

        DaysCount daysCount = new DaysCount();
        List<ReservationDate> bookedDates = daysCount.returnDaysList(room.getAvailableFrom(), room.getAvailableTo());

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

        em.persist(reservation);

        LOGGER.debug("Reservation persisted succesfully");

        RoomEntity oldRoomEntity = em.find(RoomEntity.class, id);

        List<ReservationDate> extractedBookedDates = oldRoomEntity.getBookedDates();
        LOGGER.trace("Booked dates number before update: " + extractedBookedDates.size());

        extractedBookedDates.addAll(bookedDates);

        updateBookedDates(id, extractedBookedDates);

        LOGGER.trace("Booked dates number after update: " + extractedBookedDates.size());

    }

    private void updateBookedDates(long id, List<ReservationDate> bookedDates) {
        em.createNativeQuery("delete from RoomEntity_bookedDates where  RoomEntity_id=:id ").setParameter("id", id).executeUpdate();
        em.createNativeQuery("insert into RoomEntity_bookedDates (RoomEntity_id, bookedDates) values (:id, :bookedDates) ")
                .setParameter("id", id)
                .setParameter("bookedDates", bookedDates)
                .executeUpdate();
    }
}
