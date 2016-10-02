package pl.excercise.service;

import java.time.LocalDate;

public class Validate {
    public boolean validateDate(String stringStartDate, String stringEndDate) {

        LocalDate startDate = LocalDate.parse(stringStartDate);
        LocalDate endDate = LocalDate.parse(stringEndDate);

        return (startDate.isBefore(endDate) || startDate.isEqual(endDate));

    }

    public boolean validateNameContent(String firstName, String lastName) {
        return (!firstName.isEmpty() && !lastName.isEmpty());
    }
}
