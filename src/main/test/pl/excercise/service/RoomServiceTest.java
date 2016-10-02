package pl.excercise.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.excercise.model.room.RoomEntity;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    @InjectMocks
    RoomService roomService;

    @Mock
    EntityManager em;

    RoomEntity room = new RoomEntity();

    @Test
    public void should_return_available_rooms() throws Exception {
//        //given
//        when(em.find(RoomEntity.class, 1l))
//                .thenReturn(room.withRoomType(new RandomRooms().getRandomType())
//                        .withWindowsExposure(new RandomRooms().getRandomExposure())
//                        .build());
//        ParametrizedRoom parametrizedRoom = new ParametrizedRoom().withAvailableFrom("2016-06-15");
//
//        //when
//        boolean createReservation = roomService.createReservation(new GuestSessionScoped(), parametrizedRoom, 0);
//        System.out.println("createReservation = " + createReservation);
//
//        //then
//        verify(createReservation)  ;

//        SearchEventEntity entity = new SearchEventEntity();
//        entity.setEvent(new SearchEvent());
//        when(entityManager.find(SearchEventEntity.class, 1l))
//                .thenReturn(entity);
//
//        //when
//        SearchEvent event = store.getSearchEventById(1l);
//
//        //then
//        assertNotNull(event);

    }

    @Test
    public void findAllRooms() throws Exception {
//        List<RoomEntity> returnedRooms = new ArrayList<>();
//        RoomEntity roomEntity = new RoomEntity();
//        roomEntity.withRoomType("RoomType").build();
//        returnedRooms.add(roomEntity);
//        when(em.createQuery("select r from RoomEntity r ")
//                .getResultList())
//                .thenReturn(returnedRooms);
//
//        List<RoomEntity> rooms = roomService.findAllRooms();
//
//        assertNotNull(rooms);

    }

    @Test
    public void cancel() throws Exception {

    }

    @Test
    public void persist() throws Exception {

    }

    @Test
    public void extract() throws Exception {

    }

}