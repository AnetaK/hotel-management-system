package pl.excercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.excercise.model.room.RoomEntity;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @Embedded
    private Guest guest;

    @JsonProperty
    @ManyToOne
    private RoomEntity room;

    boolean cancelledFlag;
    String bookedFrom;
    String bookedTo;

    public Long getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public boolean getCancelledFlag() {
        return cancelledFlag;
    }

    public String getBookedFrom() {
        return bookedFrom;
    }

    public String getBookedTo() {
        return bookedTo;
    }

    public Reservation() {
    }

    public Reservation(Guest guest, RoomEntity room, boolean cancelledFlag, String bookedFrom, String bookedTo) {
        this.guest = guest;
        this.room = room;
        this.cancelledFlag = cancelledFlag;
        this.bookedFrom = bookedFrom;
        this.bookedTo = bookedTo;
    }

    public Reservation withGuest(Guest guest) {
        this.guest = guest;
        return this;
    }

    public Reservation withRoom(RoomEntity room) {
        this.room = room;
        return this;
    }

    public Reservation withBookedFrom(String bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public Reservation withBookedTo(String bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public Reservation withCancelledFlag(boolean cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
        return this;
    }

    public Reservation build() {
        return new Reservation(guest, room, cancelledFlag, bookedFrom, bookedTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (cancelledFlag != that.cancelledFlag) return false;
        if (guest != null ? !guest.equals(that.guest) : that.guest != null) return false;
        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        if (bookedFrom != null ? !bookedFrom.equals(that.bookedFrom) : that.bookedFrom != null) return false;
        return bookedTo != null ? bookedTo.equals(that.bookedTo) : that.bookedTo == null;

    }

    @Override
    public int hashCode() {
        int result = guest != null ? guest.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (cancelledFlag ? 1 : 0);
        result = 31 * result + (bookedFrom != null ? bookedFrom.hashCode() : 0);
        result = 31 * result + (bookedTo != null ? bookedTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", guest=" + guest +
                ", room=" + room +
                ", cancelledFlag=" + cancelledFlag +
                ", bookedFrom='" + bookedFrom + '\'' +
                ", bookedTo='" + bookedTo + '\'' +
                '}';
    }
}
