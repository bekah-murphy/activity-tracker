package com.activity.tracker.repository;

import com.activity.tracker.domain.Action;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.Objects;

@Document(indexName="activity", type="default")
public class ActivityEntity {

	@Id
	private String id;
	private Action action;
	private Integer time;
	private Date createDate;

	public ActivityEntity() {
	}

	public ActivityEntity(Action action, Integer time) {
		this.action = action;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "ActivityEntity{" +
			"id='" + id + '\'' +
			", action='" + action + '\'' +
			", time='" + time + '\'' +
			", createDate='" + createDate + '\'' +
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
		ActivityEntity that = (ActivityEntity) o;
		return Objects.equals(id, that.id)
			&& Objects.equals(action, that.action)
			&& Objects.equals(time, that.time)
			&& Objects.equals(createDate, that.createDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, action, time, createDate);
	}
}
