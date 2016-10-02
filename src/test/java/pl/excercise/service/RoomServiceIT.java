package pl.excercise.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    RandomRooms randomRooms;


    @EJB
    RoomService service;


    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;


    @Before
    public void initialise() {

            RoomEntity room = new RoomEntity();


        try {
            utx.begin();
            em.joinTransaction();

            room.withRoomType(randomRooms.getRandomType())
                    .withWindowsExposure(randomRooms.getRandomExposure())
                    .withBookedDates(randomRooms.getRandomDates())
                    .build();
            em.persist(room);
            utx.commit();
            // clear the persistence context (first-level cache)
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

    @Test
    public void should_return_5_rooms(){




        //}
      //  List<RoomEntity> allRooms = service.findAllRooms();

     //   assertThat(allRooms.size(),is(equalTo(0)));

    }
}
