package pl.excercise.service;

import java.time.LocalDate;

public class ValidateDate {
    public boolean validate(String stringStartDate, String stringEndDate) {

        LocalDate startDate = LocalDate.parse(stringStartDate);
        LocalDate endDate = LocalDate.parse(stringEndDate);

        return (startDate.isBefore(endDate) || startDate.isEqual(endDate));

    }
}
