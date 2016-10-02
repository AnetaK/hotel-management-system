package pl.excercise.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateTest {

    Validate validate = new Validate();

    @Test
    public void should_return_true() {

        String startDate = "2016-09-20";
        String endDate = "2016-09-21";

        boolean result = validate.validateDate(startDate, endDate);

        assertThat(result, is(equalTo(true)));

    }

    @Test
    public void should_return_false() {

        String startDate = "2016-09-22";
        String endDate = "2016-09-21";

        boolean result = validate.validateDate(startDate, endDate);

        assertThat(result, is(equalTo(false)));

    }

    @Test
    public void should_return_true_for_equal_dates() {

        String startDate = "2016-09-21";
        String endDate = "2016-09-21";

        boolean result = validate.validateDate(startDate, endDate);

        assertThat(result, is(equalTo(true)));

    }

    @Test
    public void should_return_false_for_empty_firstName(){
        String firstName = "";
        String lastName = "Kowalski";

        boolean result = validate.validateNameContent(firstName,lastName);

        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void should_return_false_for_empty_lastName(){
        String firstName = "Jan";
        String lastName = "";

        boolean result = validate.validateNameContent(firstName,lastName);

        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void should_return_false_for_empty_both_values(){
        String firstName = "";
        String lastName = "";

        boolean result = validate.validateNameContent(firstName,lastName);

        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void should_return_ftrue_for_valid_name(){
        String firstName = "Jan";
        String lastName = "Kowalski";

        boolean result = validate.validateNameContent(firstName,lastName);

        assertThat(result, is(equalTo(true)));
    }

}