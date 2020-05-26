package com.activity.tracker.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AverageActivityTime {

    public AverageActivityTime(String action, Integer avg) {
        this.action = action;
        this.avg = avg;
    }

    private String action;

    private Integer avg;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }
}
