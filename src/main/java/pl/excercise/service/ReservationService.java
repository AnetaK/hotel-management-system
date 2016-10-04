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
import java.util.Collections;
import java.util.List;

@Stateless
public class ReservationService {

    private static final Logger LOGGER = LogManager.getLogger(ReservationService.class);

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

    public void cancelReservation(long id) {
        Reservation reservation = em.find(Reservation.class, id);

        DaysCount dates = new DaysCount();
        List<String> datesToRemove = dates.returnDaysList(reservation.getBookedFrom(), reservation.getBookedTo());

        long roomId = reservation.getRoom().getId();
        em.createQuery("update Reservation set cancelledFlag = true where id=:id ")
                .setParameter("id", id)
                .executeUpdate();

        Reservation newReservation = em.find(Reservation.class, id);
        LOGGER.trace("Cancelled flag: " + newReservation.getCancelledFlag());

        RoomEntity roomEntity = em.find(RoomEntity.class, roomId);

        List<String> roomDates = roomEntity.getBookedDates();
        LOGGER.trace("Number of booked dates before cancelling: " + roomDates.size());

        roomDates.removeIf(r -> datesToRemove.contains(r));

        updateBookedDates(roomEntity.getId(), roomDates);

        RoomEntity newRoomEntity = em.find(RoomEntity.class, roomId);

        LOGGER.trace("Number of booked dates after cancelling: " + newRoomEntity.getBookedDates().size());

        LOGGER.trace("Reservation {} is cancelled", id);

    }

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

        em.persist(reservation);

        LOGGER.debug("Reservation persisted succesfully");

        RoomEntity oldRoomEntity = em.find(RoomEntity.class, id);

        List<String> extractedBookedDates = oldRoomEntity.getBookedDates();
        LOGGER.trace("Booked dates number before update: " + extractedBookedDates.size());

        extractedBookedDates.addAll(bookedDates);

        updateBookedDates(id, extractedBookedDates);

        RoomEntity newRoomEntity = em.find(RoomEntity.class, id);
        LOGGER.trace("Booked dates number after update: " + newRoomEntity.getBookedDates().size());

    }

    private void updateBookedDates(long id, List<String> bookedDates) {
        em.createNativeQuery("delete from RoomEntity_bookedDates where  RoomEntity_id=:id ").setParameter("id", id).executeUpdate();
        em.createNativeQuery("insert into RoomEntity_bookedDates (RoomEntity_id, bookedDates) values (:id, :bookedDates) ")
                .setParameter("id", id)
                .setParameter("bookedDates", bookedDates)
                .executeUpdate();
    }
}
