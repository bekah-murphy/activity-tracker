package com.activity.tracker.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.activity.tracker.domain.Action;
import com.activity.tracker.domain.Activity;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ActivityControllerTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldFailIfActionIsNullOnCreate() {
        Activity activity = new Activity(null, 100);
        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
        assertEquals("must not be null", violations.stream().findFirst().get().getMessage());
        assertNull(violations.stream().findFirst().get().getInvalidValue());
    }

    @Test
    public void shouldFailIfTimeIsNullOnCreate() {
        Activity activity = new Activity(Action.JUMP, null);
        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
        assertEquals("must not be null", violations.stream().findFirst().get().getMessage());
        assertNull(violations.stream().findFirst().get().getInvalidValue());
    }

    @Test
    public void shouldFailIfTimeIsLessThanNegativeOnCreate() {
        Activity activity = new Activity(Action.JUMP, -1);
        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
        assertEquals(violations.stream().findFirst().get().getMessage(), "must be greater than or equal to 0");
        assertEquals(-1, violations.stream().findFirst().get().getInvalidValue());
    }
}
