package pl.excercise.service;

import pl.excercise.model.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ExtractReservations {

    @PersistenceContext
    EntityManager em;

    public List<Reservation> extract(){
        return em.createQuery("select r from Reservation r ").getResultList();
    }
}
