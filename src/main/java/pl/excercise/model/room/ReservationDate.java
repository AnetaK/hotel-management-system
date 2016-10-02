package pl.excercise.model.room;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ReservationDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    LocalDate reservationDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ROOMENTITY_ID")
    private RoomEntity roomEntity;

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReservationDate{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
