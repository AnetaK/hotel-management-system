package pl.excercise.model;

import pl.excercise.model.room.RoomEntity;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Guest guest;

    @ManyToOne
    private RoomEntity room;

    int cancelledFlag = 0;

    public Long getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public int getCancelledFlag() {
        return cancelledFlag;
    }

    public Reservation() {
    }

    public Reservation(Guest guest, RoomEntity room, int cancelledFlag) {
        this.guest = guest;
        this.room = room;
        this.cancelledFlag = cancelledFlag;
    }

    public Reservation withGuest(Guest guest) {
        this.guest = guest;
        return this;
    }

    public Reservation withRoom(RoomEntity room) {
        this.room = room;
        return this;
    }

    public Reservation withCancelledFlag(int cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
        return this;
    }

    public Reservation build() {
        return new Reservation(guest, room, cancelledFlag);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", guest=" + guest +
                ", room=" + room +
                ", cancelledFlag=" + cancelledFlag +
                '}';
    }
}
