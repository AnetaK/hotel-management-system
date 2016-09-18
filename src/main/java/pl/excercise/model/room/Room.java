package pl.excercise.model.room;


import java.time.LocalDate;
import java.util.List;

public class Room {

    private RoomType roomType;

    private List<LocalDate> bookedDates;

    private WindowsExposure windowsExposure;

    public RoomType getRoomType() {
        return roomType;
    }

    public List<LocalDate> getBookedDates() {
        return bookedDates;
    }

    public WindowsExposure getWindowsExposure() {
        return windowsExposure;
    }

    public Room() {
    }

    public Room(RoomType roomType, List<LocalDate> bookedDates, WindowsExposure windowsExposure) {
        this.roomType = roomType;
        this.bookedDates = bookedDates;
        this.windowsExposure = windowsExposure;
    }

    public Room withRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public Room withBookedDates(List<LocalDate> bookedDates) {
        this.bookedDates = bookedDates;
        return this;
    }

    public Room withWindowsExposure(WindowsExposure windowsExposure) {
        this.windowsExposure = windowsExposure;
        return this;
    }

    public Room build() {
        return new Room(roomType, bookedDates, windowsExposure);
    }

    @Override
    public String toString() {
        return "Room{" +
                ", roomType=" + roomType +
                ", bookedDates=" + bookedDates +
                ", windowsExposure=" + windowsExposure +
                '}';
    }
}
