package pl.excercise.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class DaysCount {
    private static final Logger LOGGER = LogManager.getLogger(DaysCount.class);

    public List<String> returnDaysList(String startDate, String endDate) {

        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        long daysBetween = DAYS.between(startLocalDate, endLocalDate);
        LOGGER.trace("NumberOfDays for calculation" + daysBetween);

        List<String> bookedDates = new ArrayList<>();

        for (int i = 0; i < daysBetween + 1; i++) {
            String date = startLocalDate.plusDays(i).toString();
            bookedDates.add(date);
        }

        return bookedDates;

    }
}
