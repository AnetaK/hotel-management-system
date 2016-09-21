package pl.excercise.service;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateDateTest {

    ValidateDate validateDate = new ValidateDate();

    @Test
    public void should_return_true() {

        String startDate = "2016-09-20";
        String endDate = "2016-09-21";

        boolean result = validateDate.validate(startDate, endDate);

        assertThat(result, is(equalTo(true)));

    }

    @Test
    public void should_return_false() {

        String startDate = "2016-09-22";
        String endDate = "2016-09-21";

        boolean result = validateDate.validate(startDate, endDate);

        assertThat(result, is(equalTo(false)));

    }

    @Test
    public void should_return_true_for_equal_dates() {

        String startDate = "2016-09-21";
        String endDate = "2016-09-21";

        boolean result = validateDate.validate(startDate, endDate);

        assertThat(result, is(equalTo(true)));

    }

}