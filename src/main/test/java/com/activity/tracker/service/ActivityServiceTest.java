package test.java.com.activity.tracker.service;

import static org.junit.Assert.assertEquals;

import com.activity.tracker.ActivitySpringBootApplication;
import com.activity.tracker.domain.Action;
import com.activity.tracker.domain.Activity;
import com.activity.tracker.domain.AverageActivityTime;
import com.activity.tracker.repository.ActionRepository;
import com.activity.tracker.repository.ActivityEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO:
 * This service would not be pushed to production until these tests pass
 * Given the time constraints of this assignment, I was not able to get a test node of elastic running
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitySpringBootApplication.class)
public class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActionRepository actionRepository;

    @Ignore
    @Test
    public void shouldIndexActivityDocument() {
        activityService.save(new Activity(Action.JUMP, 100));
        activityService.save(new Activity(Action.HOP, 50));
        activityService.save(new Activity(Action.RUN, 700));

        assertEquals(Collections.singletonList(new ActivityEntity(Action.JUMP, 100)), actionRepository.findActivitiesByAction(Action.JUMP));
        assertEquals(Collections.singletonList(new ActivityEntity(Action.HOP, 50)), actionRepository.findActivitiesByAction(Action.HOP));
        assertEquals(Collections.singletonList(new ActivityEntity(Action.RUN, 700)), actionRepository.findActivitiesByAction(Action.HOP));
    }

    @Ignore
    @Test
    public void shouldReturnAverageOfIndexedDocuments() {

        activityService.save(new Activity(Action.JUMP, 100));
        activityService.save(new Activity(Action.JUMP, 300));
        activityService.save(new Activity(Action.HOP, 50));
        activityService.save(new Activity(Action.HOP, 0));

        List<AverageActivityTime> actualAverageActivityTimes = activityService.getAverageTimeForAllActions();
        assertEquals(getExpectedAverageForEachAction(), actualAverageActivityTimes);
    }

    public static List<AverageActivityTime> getExpectedAverageForEachAction() {
        List<AverageActivityTime> averageActivityTimes = new ArrayList<>();
        averageActivityTimes.add(new AverageActivityTime(Action.HOP, 25));
        averageActivityTimes.add(new AverageActivityTime(Action.JUMP, 200));
        return averageActivityTimes;
    }
}
