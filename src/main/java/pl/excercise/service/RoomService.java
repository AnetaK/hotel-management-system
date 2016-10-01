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
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class RoomService {

    private static final Logger LOGGER = LogManager.getLogger(RoomService.class);

    @PersistenceContext
    EntityManager em;

    public List<RoomEntity> findAvailableRooms(ParametrizedRoom parametrizedRoom) {
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

    public List<RoomEntity> findAllRooms() {
        List<RoomEntity> rooms = em.createQuery("select r from RoomEntity r ")
                .getResultList();

        LOGGER.debug("Result list size = " + rooms.size());

        return rooms;
    }

    public void cancel(long id, List<String> datesToCancel) {

        em.createQuery("update Reservation set cancelledFlag = true where id=:id ")
                .setParameter("id", id)
                .executeUpdate();

        Reservation reservation = em.find(Reservation.class, id);

        LOGGER.trace("Reservation cancelled flag = " + reservation.getCancelledFlag());

        RoomEntity roomEntity = reservation.getRoom();

        List<String> roomDates = roomEntity.getBookedDates();
        LOGGER.trace("Number of booked dates before cancelling: " + roomDates.size());

        roomDates.removeIf(r -> datesToCancel.contains(r));
        LOGGER.trace("Number of booked dates after cancelling: " + roomDates.size());

        em.createQuery("update RoomEntity re set re.bookedDates=:bookedDates where re.id=:id ")
                .setParameter("id", id)
                .setParameter("bookedDates", roomDates);

        LOGGER.trace("Reservation {} is cancelled", id);

    }

    public void persist(GuestSessionScoped guest, ParametrizedRoom room, long id) {

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

        em.createQuery("update RoomEntity re set re.bookedDates=:bookedDates where re.id=:id ")
                .setParameter("id", id)
                .setParameter("bookedDates", extractedBookedDates);

        LOGGER.trace("Booked dates number after update: " + extractedBookedDates.size());

    }

    public List<Reservation> extract(GuestSessionScoped guest) {
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
}
