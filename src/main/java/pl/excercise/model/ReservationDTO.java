package pl.excercise.model;

public class ReservationDTO {

    private long Id;
    private String guestName;
    private String guestSurname;
    private long roomId;

    public ReservationDTO() {
    }

    private ReservationDTO(long Id, String guestName, String guestSurname, long roomId) {
        Id = Id;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.roomId = roomId;
    }

    public ReservationDTO withId(long Id) {
        this.Id = Id;
        return this;
    }

    public ReservationDTO withGuestName(String guestName) {
        this.guestName = guestName;
        return this;
    }

    public ReservationDTO withGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
        return this;
    }

    public ReservationDTO withRoomId(long roomId) {
        this.roomId = roomId;
        return this;
    }

    public ReservationDTO build() {
        return new ReservationDTO(Id, guestName, guestSurname, roomId);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "Id=" + Id +
                ", guestName='" + guestName + '\'' +
                ", guestSurname='" + guestSurname + '\'' +
                ", roomId=" + roomId +
                '}';
    }

    public long getId() {
        return Id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
