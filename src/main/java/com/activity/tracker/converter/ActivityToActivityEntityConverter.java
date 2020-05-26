package com.activity.tracker.converter;

import com.activity.tracker.domain.Activity;
import com.activity.tracker.repository.ActivityEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class ActivityToActivityEntityConverter implements Converter<Activity, ActivityEntity> {

    @Override
    public ActivityEntity convert(Activity source) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setAction(source.getAction());
        activityEntity.setCreateDate(Date.from(Instant.now()));
        activityEntity.setTime(source.getTime());
        return activityEntity;
    }
}
