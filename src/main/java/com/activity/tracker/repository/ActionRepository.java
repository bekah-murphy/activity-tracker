package com.activity.tracker.repository;

import com.activity.tracker.domain.Action;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends ElasticsearchRepository<ActivityEntity, String> {

    public List<ActivityEntity> findActivitiesByAction(Action action);
}
