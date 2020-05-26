package com.activity.tracker.rest;

import com.activity.tracker.domain.Activity;
import com.activity.tracker.domain.AverageActivityTime;
import com.activity.tracker.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity addAction(@RequestBody @Valid Activity activity) {
        return activityService.save(activity);
    }

    @GetMapping("stats")
    public List<AverageActivityTime> getStats() {
       return activityService.getAverageTimeForAllActions();
    }
}
