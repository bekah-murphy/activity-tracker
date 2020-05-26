package com.activity.tracker.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AverageActivityTime {

    public AverageActivityTime(Action action, Integer avg) {
        this.action = action;
        this.avg = avg;
    }

    private Action action;

    private Integer avg;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }
}
