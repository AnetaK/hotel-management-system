package pl.excercise.database;

import pl.excercise.model.room.ParametrizedRoom;
import pl.excercise.model.room.RoomEntity;
import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RoomDB {

    @PersistenceContext
    EntityManager em;

    public List<RoomEntity> extractRooms() {
        List<RoomEntity> rooms = em.createQuery("select r from RoomEntity r ")
                .getResultList();

        return rooms;
    }

    public List<RoomEntity> extractAvailableRooms(List<String> datesRange, RoomType roomType, WindowsExposure windowsExposure){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<RoomEntity> criteria = builder.createQuery(RoomEntity.class);
        Root<RoomEntity> roomEntityRoot = criteria.from(RoomEntity.class);

        criteria.select(roomEntityRoot);

        List<Predicate> predicates = datesRange.stream().map(s -> builder.isNotMember(s, roomEntityRoot.get("bookedDates")))
                .collect(Collectors.toList());

        predicates.add(builder.like(roomEntityRoot.get("roomType"), roomType.toString()));
        predicates.add(builder.like(roomEntityRoot.get("windowsExposure"), windowsExposure.toString()));

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        criteria.orderBy(builder.asc(roomEntityRoot.get("id")));

        List<RoomEntity> rooms = em.createQuery(criteria)
                .getResultList();

        return rooms;
    }

    public RoomEntity extractRoomById(long id) {
        return em.find(RoomEntity.class,id);
    }

    public void updateBookedDates(long id, List<String> bookedDates) {
        em.createNativeQuery("update  RoomEntity_bookedDates set bookedDates = :bookedDates where RoomEntity_id=:id  ")
                .setParameter("id", id)
                .setParameter("bookedDates", bookedDates)
                .executeUpdate();

    }
}
