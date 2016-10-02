package pl.excercise.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.excercise.model.room.*;

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
public class RoomServiceIT {

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
                .addClass(WindowsExposure.class);

    }

    @EJB
    RoomService service;


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
                List<ReservationDate> dates = new ArrayList<>();
                ReservationDate reservationDate = new ReservationDate();
                reservationDate.setReservationDate(LocalDate.parse("2016-05-10").plusDays(i));
                ReservationDate reservationDate2 = new ReservationDate();
                reservationDate.setReservationDate(LocalDate.parse("2016-05-10").plusDays(i+2));
                dates.add(reservationDate);
                dates.add(reservationDate2);

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
    public void should_return_4_rooms() {

        try {
            utx.begin();
            em.joinTransaction();

            List<RoomEntity> availableRooms = service.findAvailableRooms(new ParametrizedRoom()
                    .withRoomType(RoomType.ExclusiveRoom.toString())
                    .withWindowsExposure(WindowsExposure.EAST.toString())
                    .withAvailableFrom("2016-05-10")
                    .withAvailableTo("2016-05-10")
                    .build());

            assertThat(availableRooms.size(), is(equalTo(4)));
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
    public void should_return_1_room() {

        try {
            utx.begin();
            em.joinTransaction();

            List<RoomEntity> availableRooms = service.findAvailableRooms(new ParametrizedRoom()
                    .withRoomType(RoomType.ExclusiveRoom.toString())
                    .withWindowsExposure(WindowsExposure.EAST.toString())
                    .withAvailableFrom("2016-05-10")
                    .withAvailableTo("2016-05-13")
                    .build());

            assertThat(availableRooms.size(), is(equalTo(1)));

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
    public void should_return_no_rooms() {

        try {
            utx.begin();
            em.joinTransaction();

            List<RoomEntity> availableRooms = service.findAvailableRooms(new ParametrizedRoom()
                    .withRoomType(RoomType.ExclusiveRoom.toString())
                    .withWindowsExposure(WindowsExposure.EAST.toString())
                    .withAvailableFrom("2016-05-10")
                    .withAvailableTo("2016-05-20")
                    .build());

            assertThat(availableRooms.size(), is(equalTo(0)));
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
    public void should_return_5_rooms() {

        try {
            utx.begin();
            em.joinTransaction();

            List<RoomEntity> allRooms = service.findAllRooms();

            assertThat(allRooms.size(), is(equalTo(5)));
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
