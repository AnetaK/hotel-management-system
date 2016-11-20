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

    @ElementCollection
    @Column(length=10000)
    private List<String> bookedDates;

    private String windowsExposure;

    public String getRoomType() {
        return roomType;
    }

    public List<String> getBookedDates() {
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


    public RoomEntity(String roomType, List<String> bookedDates, String windowsExposure) {
        this.roomType = roomType;
        this.bookedDates = bookedDates;
        this.windowsExposure = windowsExposure;
    }

    public RoomEntity withRoomType(String roomType) {
        this.roomType = roomType;
        return this;
    }

    public RoomEntity withBookedDates(List<String> bookedDates) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomEntity that = (RoomEntity) o;

        if (roomType != null ? !roomType.equals(that.roomType) : that.roomType != null) return false;
        if (bookedDates != null ? !bookedDates.equals(that.bookedDates) : that.bookedDates != null) return false;
        return windowsExposure != null ? windowsExposure.equals(that.windowsExposure) : that.windowsExposure == null;

    }

    @Override
    public int hashCode() {
        int result = roomType != null ? roomType.hashCode() : 0;
        result = 31 * result + (bookedDates != null ? bookedDates.hashCode() : 0);
        result = 31 * result + (windowsExposure != null ? windowsExposure.hashCode() : 0);
        return result;
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
