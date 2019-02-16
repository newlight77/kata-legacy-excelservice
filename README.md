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

- `/api/surveys`
- `/api/campaigns`
- `/api/campaigns/export`

## Stub

This application consumes curd api for survey and campaign. Here we use [json-server](https://github.com/typicode/json-server#getting-started) to serve it.

The endpoints are :

- `/api/surveys`
- `/api/campaigns`

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
docker run -d -p 8025:8025 -p 1025:1025 --name=mailhog mailhog/mailhog
```

## Docker-compose

As an alternative, run with docker-compose:

```bash
docker-compse up -d
```

## Refactoring

The ExportCampaignService has been refactored many times. 

Here are the steps :

1. Open-Close Principe : unit test before refactoring
2. Clean Code : cleaner unit test
3. Clean Code : smaller methods in ExportCampaignService
4. Single Responsibility Principle : applied to Campaign and Survey resources
5. Builder pattern : split ExportCampaignService
6. Builder pattern : cleaner
7. Builder pattern to Fluent Builder


This may help if you would like to practice and go through all those steps. 

Here is a git history snapshot :

- aa638cb (HEAD -> master, origin/master, __step8-recap__) recap and update readme
- 100ab7f (__step7-builder-fluent__) refactoring to user builder fluent pattern
- 0af39e6 (__step6-builder-clean__) cleaner CampaignExcelBuilder : encapsulate everything inside builder class
- 7638816 (__step5-builder__) renaming builder methods in CampaignExcelBuilder
- 08587f2 refactoring by splitting ExportCampaignService to use a Builder
- 8e1fc30 (__step4-srp__) single responsability : splitting campaign and survey services
- 5758e33 single responsability : splitting campaign and survey services
- f3f8a8a (__step3-service-refacto__) refactoring export service into small methods
- 5d92cf4 (__step2-ut-refacto__) refactoring UT, loading json from file
- 2a02add (__step1-adding-ut__) adding a UT for exportCampaignService assert the excel content
- 7ee9120 (__before-refacto__) using lombok