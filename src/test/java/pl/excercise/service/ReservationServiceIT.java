package pl.excercise.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.excercise.model.Guest;
import pl.excercise.model.GuestSessionScoped;
import pl.excercise.model.Reservation;
import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(Arquillian.class)
public class ReservationServiceIT {

    @Deployment
    public static WebArchive deployment() {

        File[] libs = Maven.resolver()
                .resolve("org.apache.logging.log4j:log4j-core:2.6.2",
                        "org.apache.logging.log4j:log4j-api:2.6.2")
                .withTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(libs)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("log4j2.xml", "log4j2.xml")
                .addClass(RoomService.class)
                .addClass(RandomRooms.class)
                .addClass(RoomEntity.class)
                .addClass(DaysCount.class)
                .addClass(ParametrizedRoom.class)
                .addClass(RoomType.class)
                .addClass(WindowsExposure.class)
                .addClass(ReservarionService.class)
                .addClass(GuestSessionScoped.class)
                .addClass(Reservation.class)
                .addClass(Guest.class);

    }

    @EJB
    ReservarionService service;

    @Inject
    GuestSessionScoped guest;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;


    @InSequence(1)
    @Test
    public void initialise() {

        for (int i = 0; i < 5; i++) {

            try {
                utx.begin();
                em.joinTransaction();
                List<String> dates = new ArrayList<>();
                dates.add(
                        LocalDate.parse("2016-05-10").plusDays(i).toString()
                );
                dates.add(
                        LocalDate.parse("2016-05-10").plusDays(i + 2).toString()
                );

                RoomEntity room = new RoomEntity();
                room.withRoomType(RoomType.ExclusiveRoom.toString())
                        .withWindowsExposure(WindowsExposure.EAST.toString())
                        .withBookedDates(dates)
                        .build();
                em.persist(room);
                utx.commit();
                em.clear();

            } catch (NotSupportedException e) {
                e.printStackTrace();
            } catch (SystemException e) {
                e.printStackTrace();
            } catch (HeuristicMixedException e) {
                e.printStackTrace();
            } catch (HeuristicRollbackException e) {
                e.printStackTrace();
            } catch (RollbackException e) {
                e.printStackTrace();
            }
        }

    }

    @InSequence(2)
    @Test
    public void init2() {

        guest.withFirstName("Any").withLastName("Name").build();
        ParametrizedRoom room = new ParametrizedRoom()
                .withRoomType(RoomType.ExclusiveRoom.toString())
                .withWindowsExposure(WindowsExposure.EAST.toString())
                .withAvailableFrom("2016-05-10")
                .withAvailableTo("2016-05-10")
                .build();

        try {
            utx.begin();
            em.joinTransaction();
            service.createReservation(guest, room, 4l);
            utx.commit();
            em.clear();

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

    @InSequence(3)
    @Test
    public void should_return_one_reservation_for_guest() {

        try {
            utx.begin();
            em.joinTransaction();

            List<Reservation> reservationList = service.extractReservationsForGuest(guest.withFirstName("Any").withLastName("Name").build());

            assertThat(reservationList.size(), is(equalTo(1)));
            assertThat(reservationList.get(0).getId(), is(equalTo(6l)));

            utx.commit();
            em.clear();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

    @InSequence(4)
    @Test
    public void should_cancel_reservation() {

        try {
            utx.begin();
            em.joinTransaction();

            service.cancelReservation(0l);

            utx.commit();
            em.clear();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

    @InSequence(5)
    @Test
    public void should_return_cancel_reservation() {
        try {
            utx.begin();
            em.joinTransaction();

            Reservation reservation = em.find(Reservation.class, 6l);

            assertThat(reservation.getCancelledFlag(), is(equalTo(true)));
            utx.commit();
            em.clear();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

}
