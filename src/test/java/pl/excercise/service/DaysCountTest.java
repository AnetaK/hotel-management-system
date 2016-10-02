package pl.excercise.service;


import org.junit.Test;
import pl.excercise.model.room.ReservationDate;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DaysCountTest {

    DaysCount daysCount = new DaysCount();

    @Test
    public void should_return_3_days() {

        String startDate = "2016-09-20";
        String endDate = "2016-09-22";

        List<ReservationDate> bookedDates = daysCount.returnDaysList(startDate, endDate);

        assertThat("Wrong number of days", bookedDates.size(), is(equalTo(3)));
        assertThat("Wrong first date ", bookedDates.get(0).getReservationDate(), is(equalTo(LocalDate.parse("2016-09-20"))));
        assertThat("Wrong second date ", bookedDates.get(1).getReservationDate(), is(equalTo(LocalDate.parse("2016-09-21"))));
        assertThat("Wrong third date", bookedDates.get(2).getReservationDate(), is(equalTo(LocalDate.parse("2016-09-22"))));


    }

}