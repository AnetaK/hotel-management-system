package pl.excercise.model.room;


public class RoomDTO {
    Long id;
    RoomEntity roomEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", roomEntity=" + roomEntity +
                '}';
    }
}
