# Kata with Legacy code - ExcelService

[![Build Status](https://travis-ci.org/newlight77/kata-legacy-excelservice.svg?branch=master)](https://travis-ci.org/newlight77/kata-legacy-excelservice)
[![Maintainability](https://api.codeclimate.com/v1/badges/0a7b2d54b65bab7fa33a/maintainability)](https://codeclimate.com/github/newlight77/kata-legacy-excelservice/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/0a7b2d54b65bab7fa33a/test_coverage)](https://codeclimate.com/github/newlight77/kata-legacy-excelservice/test_coverage)
[![BCH compliance](https://bettercodehub.com/edge/badge/newlight77/kata-legacy-excelservice?branch=master)](https://bettercodehub.com/)

## Purpose

The initial codebase shows the technical dept when we neglect to do what should have been done before pushing it to production. From there, we will start working on bringing changes without breaking functionalities as intended initially.

## Data Model

__sondage__
```json
{
    "id" : "idSondage",
    "sommaire" : "sommaire",
    "client" : "nom de client",
    "adresseClient" : "adresseClient",
    "questions" : [{
        "id" : "idQuestion1",
        "question" : "question1"
    }, 
    {
       "id" : "idQuestion2",
       "question" : "question2"
   }] 
}
```

__campagne__
```json
{
    "id" : "idCampagne",
    "sondageId" : "idSondage",
    "client" : "nom de client",
    "adresseEffectues" : [ {
        "id" : "idAdresseEffectue1",
        "adresse" : {
          "numVoie" : "10",
          "nomVoie" : "rue de Rivoli",
          "codePostal" : "75001",
          "commune" : "Paris"
        },
        "status" : "EFFECTUE"
    }, {
        "id" : "idAdresseEffectue2",
        "adresse" : {
          "numVoie" : "40",
          "nomVoie" : "rue du Louvre",
          "codePostal" : "75001",
          "commune" : "Paris"
        },
        "status" : "A_FAIRE"
    }] 
}
```

## Architecture 

This application is a backend to expose REST API. Data are persisted in a H2 memory database.

