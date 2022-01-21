# document-generator

The Companies house Document Generator for generating documents

## Requirements

In order to build document-generator locally you will need the following:

- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Getting started

1. Run make
2. Run ./start.sh

### Environment Variables

The supported environmental variables have been categorised by use case and are as follows.

### Deployment Variables

Name                                      | Description                                                                  | Mandatory | Default | Example
----------------------------------------- | ---------------------------------------------------------------------------- | --------- | ------- | ----------------------------------------
DOCUMENT_RENDER_SERVICE_HOST              | Render service host                                                          | ✓         |         | localhost:valid_port_number
DOCUMENT_BUCKET_NAME                      | Bucket name to store the document                                            | ✓         |         | dev-pdf-bucket/name_of_file
API_URL                                   | Api url to go through `eric`                                                 | ✓         |         | `api.orctel.internal:$ERIC_PORT`
CHS_API_KEY                               | CHS api key encoded and used to make APi calls                               | ✓         |         | valid Api key

## Services using this service

Note that the calling services generally have a long route that involve messaging (in which document-generator-consumer interfaces with document-generator) before there message gets to the document-generator:

- [ch.gov.uk](https://github.com/companieshouse/ch.gov.uk),
- [search.web.ch.gov.uk](https://github.com/companieshouse/search.web.ch.gov.uk)
- abridged accounts: [web](https://github.com/companieshouse/abridged.accounts.web.ch.gov.uk) and [api](https://github.com/companieshouse/abridged.accounts.api.ch.gov.uk)
- small full accounts and cic : [web](https://github.com/companieshouse/company-accounts.web.ch.gov.uk) and [api](https://github.com/companieshouse/company-accounts.api.ch.gov.uk)

## Modules

The following are the modules that exist within document generator.

### document-generator-api

The document-generator-api module is a sub module within document-generator, it holds the api controller which is called passing resourceUri and mimeType to uri /private/documents/generate.
The api deals with requesting the data to be rendered through the document-generator-interface, as well as making the call to the render service to generate a document. The details of the api request and response
can be seen within document-generator-api/swagger-2.0/models

### document-generator-interface

The document-generator-interface module is a sub module within document-generator, it holds the interface DocumentInfoService that contains the methods to getDocumentInfo in order to return the DocumentInfo model.
Which will contain String data, String templateName, String assetId, String path, String descriptionIdentifier and a Map<String, String> description Values.

### document-generator-accounts

The document-generator-accounts module is a sub module within document-generator, it holds the accounts specific implementation of the DocumentInfoService that contains the method getDocumentInfo.
Which returns the DocumentInfo model which will contain  String data, String templateName, String assetId, String path, String descriptionIdentifier and a Map<String, String> description Values.

### document-generator-prosecution

The document-generator-prosecution module is a sub module within document-generator, which is the prosecution implementation of the DocumentInfoService that contains the method getDocumentInfo.
Which returns the DocumentInfo model which will contain  String data, String templateName, String assetId, String path, String descriptionIdentifier and a Map<String, String> description Values for Ultimatum and SJP letters.

## Docker

This project uses `jib` for Docker image builds - in order to build the container please run `make submodules` first and then run `mvn package -DskipTests=true jib:dockerBuild` which will output a Docker image named:

``` bash
169942020521.dkr.ecr.eu-west-1.amazonaws.com/local/document-generator
```
