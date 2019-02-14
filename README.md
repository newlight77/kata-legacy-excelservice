# Kata with Legacy code - ExcelService

[![Build Status](https://travis-ci.org/newlight77/kata-legacy-excelservice.svg?branch=master)](https://travis-ci.org/newlight77/kata-legacy-excelservice)
[![Maintainability](https://api.codeclimate.com/v1/badges/0a7b2d54b65bab7fa33a/maintainability)](https://codeclimate.com/github/newlight77/kata-legacy-excelservice/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/0a7b2d54b65bab7fa33a/test_coverage)](https://codeclimate.com/github/newlight77/kata-legacy-excelservice/test_coverage)
[![BCH compliance](https://bettercodehub.com/edge/badge/newlight77/kata-legacy-excelservice?branch=master)](https://bettercodehub.com/)

## Purpose

The initial codebase shows the technical dept when we neglect to do what should have been done before pushing it to production. From there, we will start working on bringing changes without breaking functionalities as intended initially.

## Data Model

__survey__
```json
{
    "id" : "surveyId",
    "sommary" : "sommary",
    "client" : "client's name",
    "clientAddress" : {
        "id" : "addressId",
        "streetNumber" : "10",
        "streetName" : "rue de Rivoli",
        "postalCode" : "75001",
        "city" : "Paris"
    },
    "questions" : [{
        "id" : "questionId1",
        "question" : "question1"
    }, 
    {
       "id" : "questionId2",
       "question" : "question2"
   }] 
}
```

__campaign__
```json
{
    "id" : "campaignId",
    "surveyId" : "surveyId",
    "addressStatuses" : [ {
        "id" : "addressStatusesId1",
        "address" : {
          "id" : "addressId1",
          "streetNumber" : "10",
          "streetName" : "rue de Rivoli",
          "postalCode" : "75001",
          "city" : "Paris"
        },
        "status" : "DONE"
    }, {
        "id" : "addressStatusesId2",
        "address" : {
          "id" : "addressId2",
          "streetNumber" : "40",
          "streetName" : "rue de Louvre",
          "postalCode" : "75001",
          "city" : "Paris"
        },
        "status" : "TODO"
    }] 
}
```

## Architecture 

This application is a backend to expose REST API. 

Data are persisted in a [H2 memory database](http://localhost:8080/h2-console).

## REST API

The endpoints are :

- `/api/survey/creer`
- `/api/survey/get`
- `/api/survey/campaign/creer`
- `/api/survey/campaign/get`
- `/api/survey/campaign/export`

## Stub

This application consumes curd api for survey and campaign. Here we use [json-server](https://github.com/typicode/json-server#getting-started) to serve it.

Run the json-server :

```bash
npm install -g json-server

# running on port 3000
json-server --watch stub.json

```

## Email

We use mailhog to catch every email sent. 

Run the mailhog server and browse the [ui](http://localhost:8025/#):

```bash
docker run -d -p 3000:80 -v ./stub.json:/data/db.json clue/json-server
```

## Docker-compose

As an alternative, run with docker-compose:

```bash
docker-compse up -d
```