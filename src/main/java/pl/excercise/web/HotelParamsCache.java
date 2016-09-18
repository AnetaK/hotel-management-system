package pl.excercise.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.excercise.model.Address;
import pl.excercise.model.Location;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Startup
@Singleton
@Lock(LockType.READ)
public class HotelParamsCache {

    @PersistenceContext
    EntityManager em;

    Location hotelLocation = new Location()
            .withHotelName("Hotel")
            .withAddress(new Address()
                    .withCity("City")
                    .withStreet("Street")
                    .withZipCode("00-000")
                    .build())
            .build();

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelParamsCache.class);

    @PostConstruct
    public void initialize() {
        em.persist(hotelLocation);
        LOGGER.trace("Hotel data persisted to DB");
    }

    public Location getHotelLocation() {
        return hotelLocation;
    }


}
