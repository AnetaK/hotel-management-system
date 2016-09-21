package pl.excercise.model;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class GuestSessionScoped implements Serializable{

    private String firstName;

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GuestSessionScoped() {
    }

    public GuestSessionScoped(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public GuestSessionScoped withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public GuestSessionScoped withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public GuestSessionScoped build() {
        return new GuestSessionScoped(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Guest{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


}
