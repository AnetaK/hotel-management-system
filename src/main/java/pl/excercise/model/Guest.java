package pl.excercise.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Guest implements Serializable{

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Guest() {
    }

    public Guest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Guest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Guest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Guest build() {
        return new Guest(firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        if (firstName != null ? !firstName.equals(guest.firstName) : guest.firstName != null) return false;
        return lastName != null ? lastName.equals(guest.lastName) : guest.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Guest{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
