package pl.excercise.model.room;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class RoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String roomType;


    @OneToMany(mappedBy="roomEntity")
    private List<ReservationDate> bookedDates;


    private String windowsExposure;

    public String getRoomType() {
        return roomType;
    }

    public List<ReservationDate> getBookedDates() {
        return bookedDates;
    }

    public String getWindowsExposure() {
        return windowsExposure;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomEntity() {
    }


    public RoomEntity(String roomType, List<ReservationDate> bookedDates, String windowsExposure) {
        this.roomType = roomType;
        this.bookedDates = bookedDates;
        this.windowsExposure = windowsExposure;
    }

    public RoomEntity withRoomType(String roomType) {
        this.roomType = roomType;
        return this;
    }

    public RoomEntity withBookedDates(List<ReservationDate> bookedDates) {
        this.bookedDates = bookedDates;
        return this;
    }

    public RoomEntity withWindowsExposure(String windowsExposure) {
        this.windowsExposure = windowsExposure;
        return this;
    }

    public RoomEntity build() {
        return new RoomEntity(roomType, bookedDates, windowsExposure);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", bookedDates=" + bookedDates +
                ", windowsExposure=" + windowsExposure +
                '}';
    }
}
