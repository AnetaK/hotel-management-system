package pl.excercise.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.excercise.model.room.RoomEntity;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class RoomServiceIT {

    @EJB
    RoomService service;

    @PersistenceContext
    EntityManager em;

    @Deployment
    public static WebArchive deployment() {

        File[] libs = Maven.resolver()
                .resolve("org.apache.logging.log4j:log4j-core:2.5",
                        "org.apache.logging.log4j:log4j-api:2.5")
                .withTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(libs)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addClass(RoomService.class)
                .addClass(RoomEntity.class);

    }

    @Test
    public void should_sth(){
        for (int i = 0; i < 5; i++) {
            RoomEntity room = new RoomEntity();
            room.withRoomType(new RandomRooms().getRandomType())
                    .withWindowsExposure(new RandomRooms().getRandomExposure())
                    .withBookedDates(new RandomRooms().getRandomDates())
                    .build();

            em.persist(room);

        }
        List<RoomEntity> allRooms = service.findAllRooms();

        assertThat(allRooms.size(),is(equalTo(0)));

    }
}
