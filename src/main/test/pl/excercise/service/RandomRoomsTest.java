package pl.excercise.service;

import org.junit.Test;
import pl.excercise.model.room.RoomType;
import pl.excercise.model.room.WindowsExposure;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RandomRoomsTest {

    RandomRooms randomRooms = new RandomRooms();

    @Test
    public void should_return_dates_before_a_year_from_now() throws Exception {

        List<LocalDate> randomDates = randomRooms.getRandomDates();
        LocalDate maxDate = LocalDate.now().plusDays(366);

        assertThat("Dates list is empty",randomDates.size(),is(not(0)));
        assertThat("First generated date has wrong format",randomDates.get(0).isBefore(maxDate),is(true));

    }

    @Test
    public void should_return_type_from_enum_RoomType() throws Exception {
        RoomType randomType = randomRooms.getRandomType();

        assertThat("Generated type is not in enum",randomType, instanceOf(RoomType.class));

    }

    @Test
    public void should_return_type_from_enum_WindowsExposure() throws Exception {
        WindowsExposure randomType = randomRooms.getRandomExposure();

        assertThat("Generated type is not in enum",randomType, instanceOf(WindowsExposure.class));
    }

}