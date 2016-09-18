package pl.excercise.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String zipCode;
    private String city;

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public Address() {
    }

    public Address(String street, String zipCode, String city) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public Address withStreet(String street) {
        this.street = street;
        return this;
    }

    public Address withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public Address withCity(String city) {
        this.city = city;
        return this;
    }

    public Address build() {
        return new Address(street, zipCode, city);
    }
}
