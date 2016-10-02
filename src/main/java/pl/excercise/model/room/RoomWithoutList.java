package pl.excercise.model.room;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RoomWithoutList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String roomType;

    private String windowsExposure;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getWindowsExposure() {
        return windowsExposure;
    }

    public void setWindowsExposure(String windowsExposure) {
        this.windowsExposure = windowsExposure;
    }

    @Override
    public String toString() {
        return "RoomWithoutList{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", windowsExposure='" + windowsExposure + '\'' +
                '}';
    }
}
