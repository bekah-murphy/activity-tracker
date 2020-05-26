package com.activity.tracker.service;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import com.activity.tracker.converter.ActivityToActivityEntityConverter;
import com.activity.tracker.domain.Action;
import com.activity.tracker.domain.Activity;
import com.activity.tracker.domain.AverageActivityTime;
import com.activity.tracker.repository.ActivityEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class ActivityService {

    private static final String ACTIVITY_AVERAGE_KEY = "activity_avg";
    private static final String ACTIVITY_INDEX = "activity";

    private final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RestHighLevelClient elasticSearchClient;
    private final ActivityToActivityEntityConverter activityToActivityEntityConverter;

    public ActivityService(RestHighLevelClient elasticSearchClient,
                           ElasticsearchTemplate elasticsearchTemplate,
                           ActivityToActivityEntityConverter activityToActivityEntityConverter) {
        this.elasticSearchClient = elasticSearchClient;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.activityToActivityEntityConverter = activityToActivityEntityConverter;
    }

    public ResponseEntity save(Activity activity) {
        logger.debug("Activity Service: Indexing Document for activity action {} with time {}", activity.getAction(), activity.getTime());
        elasticSearchClient.indexAsync(buildIndexRequest(activityToActivityEntityConverter.convert(activity)),
            RequestOptions.DEFAULT, new ActionIndexListener());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private IndexRequest buildIndexRequest(ActivityEntity activityEntity) {
        try {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.source(new ObjectMapper().writeValueAsString(activityEntity), XContentType.JSON);
            indexRequest.index(ACTIVITY_INDEX);
            indexRequest.type("default");
            return indexRequest;
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing Json request" +
                activityEntity.getAction() + " at " + Instant.now().toString(), e);
        }
    }

    public List<AverageActivityTime> getAverageTimeForAllActions() {

        List<AverageActivityTime> averageActivityTimes = new ArrayList<>();
        Set<Action> actions = EnumSet.allOf(Action.class);

        for (Action action : actions) {
            averageActivityTimes.add(new AverageActivityTime(action, (int) Math.round(getInternalAverage(action).getValue())));
        }

        return averageActivityTimes;
    }

    private InternalAvg getInternalAverage(Action action) {
        Aggregations aggregations = elasticsearchTemplate.query(buildSearchQuery(action.name()), SearchResponse::getAggregations);
        logger.debug("Activity Service: Action {} Average {}", action, aggregations.get(ACTIVITY_AVERAGE_KEY));
        return aggregations.get(ACTIVITY_AVERAGE_KEY);

    }

    private SearchQuery buildSearchQuery(String action) {
        return new NativeSearchQueryBuilder()
            .withQuery(matchQuery("action", action))
            .addAggregation(AggregationBuilders.avg(ACTIVITY_AVERAGE_KEY).field("time"))
            .build();
    }
}
