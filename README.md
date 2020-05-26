# Activity Tracker Application

The Activity Tracker Application is a Spring Boot application that logs the time spent on an activity action such as jumping, running, and hopping.
After an action is logged the service can return statistics on a given action, such as the average time
spent performing a given action.

## The architecture is as follows:

- When the activity tracker server receives a request from the client it creates a new thread and indexes the activity 
document asynchronously into Elasticsearch.
- Elasticsearch calls back to the activity tracking server when the asynchronous request 
is complete notifying of any errors.
- After an activity event is saved, the client will request the average time spent on each activity action.
Currently this request is not asynchronous, though Elasticsearch provides support for it. Reason for this
being with the time constraints, I was not able to design a proper system to give the client
feedback on when the query to retrieve statistics has finished. Optimally, the async request
for searching data would return the ID of the asynchronous search. The client could continue polling
this ID to receive updates until the search is complete.

## Quick Start Guide

## Prerequisites
- Java 1.8 must be installed 

## Steps:

- Start elasticsearch in Docker container


    docker-compose up
    
- Start the activity tracking service spring boot application by running the main class in ActivitySpringBootApplication

- Execute an HTTP Request to Activity Service. Please reference api.http and execute the desired curl commands to test the local environment.

Create Activity Request:

    POST /activity? HTTP/1.1
    Host: localhost:8089
    Content-Type: application/json

    { "action": "run", "time": 250}
    
Create Activity Response:
    
    HTTP/1.1 202 
    Content-Length: 0
    Date: Tue, 26 May 2020 19:39:57 GMT
    
Get Activity Statistics Request:

    GET /activity/stats HTTP/1.1
    Host: localhost:8089
    
Get Activity Statistics Response:

    HTTP/1.1 200 
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 May 2020 19:38:47 GMT
    
    [
      {
        "action": "jump",
        "avg": 500
      },
      {
        "action": "run",
        "avg": 375
      },
      {
        "action": "hop",
        "avg": 0
      }
    ]    
   

## Final Thoughts
- The client does not know when the activity has actually been indexed. This means the client could
request to get the current average and it will not contain all records. One idea on how this could be fixed,
is to return a unique ID to the client in the POST response. It could continue polling the server until
it gets a response code alerting the client the message has succeeded, or has failed
- Batching requests asynchronously to Elasticsearch could be a future enhancement to this service as it is much faster
- If metrics are only ever retrieved for each action individually, would it be worth it to create an index for 
each action?
- Caching can be utilized in future implementations to save the most recent statistics
- Circuit breakers is also in back of mind for this project
- It was a stretch goal for the developer of this project to use Elasticsearch for the first time.
Please make a PR or leave a comment to start a discussion on what improvements may be heplful moving forward.
