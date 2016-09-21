package pl.excercise.web;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
            .withHotelName("HireMe Hotel")
            .withAddress(new Address()
                    .withCity("Gdańsk")
                    .withStreet("al. Zwycięstwa 13A")
                    .withZipCode("80-219")
                    .build())
            .build();

    private static final Logger LOGGER = LogManager.getLogger(HotelParamsCache.class);

    @PostConstruct
    public void initialize() {
        em.persist(hotelLocation);
        LOGGER.trace("Hotel data persisted to DB");
    }

    public Location getHotelLocation() {
        return hotelLocation;
    }


}
