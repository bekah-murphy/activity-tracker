package com.activity.tracker.service;

import com.activity.tracker.rest.ActivityController;
import org.elasticsearch.action.ActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ActionIndexListener implements ActionListener {

    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Override
    public void onResponse(Object o) {
        logger.info(o.toString());
    }

    @Override
    public void onFailure(Exception exception) {
        logger.error("Action Index Listener: Error indexing document ", exception);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error indexing document", exception);
    }
}
