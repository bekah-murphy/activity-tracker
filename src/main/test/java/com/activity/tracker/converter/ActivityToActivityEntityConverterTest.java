package com.activity.tracker.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.activity.tracker.domain.Action;
import com.activity.tracker.domain.Activity;
import com.activity.tracker.repository.ActivityEntity;
import org.junit.Test;

public class ActivityToActivityEntityConverterTest {

    private ActivityToActivityEntityConverter activityToActivityEntityConverter = new ActivityToActivityEntityConverter();

    @Test
    public void shouldConvertActivityToActivityEntity() {
        final int time = 5;
        final Action action = Action.JUMP;

        ActivityEntity activityEntity = activityToActivityEntityConverter.convert(new Activity(action, time));
        ActivityEntity expectedActivityEntity = getExpectedActivityEntity(action, time);

        assertNotNull(activityEntity.getCreateDate());
        expectedActivityEntity.setCreateDate(activityEntity.getCreateDate());
        assertEquals(expectedActivityEntity, activityEntity);
    }

    private static ActivityEntity getExpectedActivityEntity(Action action, int time) {
        ActivityEntity expectedActivityEntity = new ActivityEntity();
        expectedActivityEntity.setTime(time);
        expectedActivityEntity.setAction(action);
        return expectedActivityEntity;
    }
}
