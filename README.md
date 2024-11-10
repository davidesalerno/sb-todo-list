# SpringBoot ToDo List Service

## Introduction
This service is a really simple SpringBoot service that is exposing the basic CRUD AI for a ToDo list

It is a service that is used for testing purposes so you cannot expect to use it AS-IS in production.

Due to the fact that it is a test project there are some components that are not needed for a specific business need but 
they are there only for testing some technical behaviour of a web application with these components.

## Description
This project is an API service for a ToDo list and the core object is the Task that has got the following schema:

```
{
    "id": 0,
    "task": "string",
    "completed": true
}
```

The exposed endpoints are:

- GET /api/v1/tasks/ -> Will provide all Tasks
- GET /api/v1/tasks/completed -> Will provide all completed Tasks
- GET /api/v1/tasks/incomplete -> Will provide all incomplete Tasks
- POST /api/v1/tasks/ -> Create a new Task
- PUT /api/v1/tasks/ID -> Update the Task with ID
- DELETE /api/v1/tasks/ID -> Delete the Task with ID

You can use [swagger-ui](http://localhost:8080/swagger-ui/index.html) to test them.

Task are persisted on PostgreSQL on the ```task``` table.

The service is using also Kafka in order to log the request received on the topics ```log hop1 hop2 hop3 final```.

The requests are logged into the ```log``` collection on MongoDB.

## Dev notes
### Dependencies
In order to have the service up & running you need an instance of 
- PostgreSQL
- Kafka
- MongoDB

### Local environment with Compose
If you quickly need a local environment you can use the scripts:
- start-devcontainers.sh
- stop-devcontainers.sh
into the ```hacks``` folder.

They are using [Podman](https://podman.io/) and [Podman Compose](https://podman-desktop.io/docs/compose) but you can easily switch to [Docker](https://www.docker.com/) setting the ```CONTAINER_ENGINE``` env variable to ```docker```.



