package pl.excercise.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CancelReservation {
    private static final Logger LOGGER = LogManager.getLogger(CancelReservation.class);

    @PersistenceContext
    EntityManager em;

    public void cancel(long id, List<String> datesToCancel) {

        em.createQuery("update Reservation r set r.cancelledFlag = true where r.id=:id ")
                .setParameter("id", id);

        Reservation reservation = em.find(Reservation.class, id);

        LOGGER.trace("reservation.getCancelledFlag() = " + reservation.getCancelledFlag());

        RoomEntity roomEntity = reservation.getRoom();

        List<String> roomDates = roomEntity.getBookedDates();
        LOGGER.trace("Number of booked dates before cancelling: " + roomDates.size());

        roomDates.removeIf(r -> datesToCancel.contains(r));
        LOGGER.trace("Number of booked dates after cancelling: " + roomDates.size());

        em.createQuery("update RoomEntity re set re.bookedDates=:bookedDates where re.id=:id ")
                .setParameter("id", roomEntity.getId())
                .setParameter("bookedDates", roomDates);

        LOGGER.trace("Reservation {} is cancelled", id);

        LOGGER.trace("room dates aftes cancelling reservation" + em.find(RoomEntity.class, roomEntity.getId()));

    }
}
