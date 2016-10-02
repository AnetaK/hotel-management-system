package pl.excercise.service;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DaysCountTest {

    DaysCount daysCount = new DaysCount();

    @Test
    public void should_return_3_days() {

        String startDate = "2016-09-20";
        String endDate = "2016-09-22";

        List<String> bookedDates = daysCount.returnDaysList(startDate, endDate);

        assertThat("Wrong number of days", bookedDates.size(), is(equalTo(3)));
        assertTrue("Wrong content", bookedDates.containsAll(Arrays.asList("2016-09-20", "2016-09-21", "2016-09-22")));

    }

}