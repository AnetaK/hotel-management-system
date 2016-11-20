package pl.excercise.database;

import pl.excercise.model.Guest;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReservationDB {

    @PersistenceContext
    EntityManager em;

    public void persistReservation(Reservation reservation) {
        em.persist(reservation);
    }

    public List<Reservation> extractReservationsForGuest(Guest guest) {
        return em.createQuery("select r from Reservation r " +
                "where r.guest=:guest ")
                .setParameter("guest", guest)
                .getResultList();
    }

    public Reservation extractReservationById(long id) {
        return em.find(Reservation.class,id);
    }

    public void setCancelledFlag(long id) {
        em.createQuery("update Reservation set cancelledFlag = true where id=:id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}
