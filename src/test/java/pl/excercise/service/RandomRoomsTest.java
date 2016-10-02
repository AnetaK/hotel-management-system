package pl.excercise.service;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class RandomRoomsTest {

    RandomRooms randomRooms = new RandomRooms();

    @Test
    public void should_return_dates_before_a_year_from_now() throws Exception {

        List<String> randomDates = randomRooms.getRandomDates();
        LocalDate maxDate = LocalDate.now().plusDays(366);
        LocalDate date = LocalDate.parse(randomDates.get(0));

        assertThat("Dates list is empty", randomDates.size(), is(not(0)));
        assertThat("First generated date has wrong format", date.isBefore(maxDate), is(true));

    }

}