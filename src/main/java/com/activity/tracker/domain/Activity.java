package com.activity.tracker.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity {

    @NotNull
    private Action action;

    @NotNull
    @Min(0)
    private Integer time;

    public Activity(Action action, Integer time) {
        this.action = action;
        this.time = time;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Activity{" +
            ", action='" + action + '\'' +
            ", time='" + time + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity that = (Activity) o;
        return Objects.equals(action, that.action)
            && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, time);
    }
}
