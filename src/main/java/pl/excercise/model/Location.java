package pl.excercise.model;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hotelName;

    @Embedded
    private Address address;

    public String getHotelName() {
        return hotelName;
    }

    public Address getAddress() {
        return address;
    }

    public Location() {
    }

    public Location(String hotelName, Address address) {
        this.hotelName = hotelName;
        this.address = address;
    }

    public Location withHotelName(String hotelName) {
        this.hotelName = hotelName;
        return this;
    }

    public Location withAddress(Address address) {
        this.address = address;
        return this;
    }

    public Location build() {
        return new Location(hotelName, address);
    }

    @Override
    public String toString() {
        return "Location{" +
                "hotelName='" + hotelName + '\'' +
                ", address=" + address +
                '}';
    }
}
