package pl.excercise.model;

import javax.enterprise.context.SessionScoped;
import javax.persistence.Embeddable;
import java.io.Serializable;

@SessionScoped
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
    public String toString() {
        return "Guest{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
