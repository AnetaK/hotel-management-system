package pl.excercise.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Stateless
public class ExtractReservations {
    private static final Logger LOGGER = LogManager.getLogger(ExtractReservations.class);

    @PersistenceContext
    EntityManager em;

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
