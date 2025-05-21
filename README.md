# document-generator

The Companies house Document Generator for generating documents

## Requirements

In order to build document-generator locally you will need the following:

- [Java 21](https://www.oracle.com/java/technologies/downloads/?er=221886#java21)
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

This project does not currently support the `docker-chs-development` development mode since there is no builder for multi-module maven projects. 

However, you can run the project within `docker-chs-development` by using the below instructions:

### Create the docker image

``` bash
make clean && make submodules && mvn package -DskipTests=true jib:dockerBuild -Dimage=document-generator-local
```

### Temporary change in the `docker-chs-development` project

In file `services/modules/docgen/document-generator.docker-compose.yaml`, comment out the AWS ECR image and uncomment the 
local image and add a new line for the local docker image.

``` yaml
document-generator:
    # image: 416670754337.dkr.ecr.eu-west-2.amazonaws.com/document-generator:latest
    image: document-generator-local
```

## Terraform ECS

### What does this code do?

The code present in this repository is used to define and deploy a dockerised container in AWS ECS.
This is done by calling a [module](https://github.com/companieshouse/terraform-modules/tree/main/aws/ecs) from terraform-modules. Application specific attributes are injected and the service is then deployed using Terraform via the CICD platform 'Concourse'.


Application specific attributes | Value                                | Description
:---------|:-----------------------------------------------------------------------------|:-----------
**ECS Cluster**        |document-generation                                      | ECS cluster (stack) the service belongs to
**Load balancer**      |{env}-chs-apichgovuk <br> {env}-chs-apichgovuk-private  | The load balancer that sits in front of the service
**Concourse pipeline**     |[Pipeline link](https://ci-platform.companieshouse.gov.uk/teams/team-development/pipelines/document-generator) <br> [Pipeline code](https://github.com/companieshouse/ci-pipelines/blob/master/pipelines/ssplatform/team-development/document-generator)                                  | Concourse pipeline link in shared services


### Contributing
- Please refer to the [ECS Development and Infrastructure Documentation](https://companieshouse.atlassian.net/wiki/spaces/DEVOPS/pages/4390649858/Copy+of+ECS+Development+and+Infrastructure+Documentation+Updated) for detailed information on the infrastructure being deployed.

### Testing
- Ensure the terraform runner local plan executes without issues. For information on terraform runners please see the [Terraform Runner Quickstart guide](https://companieshouse.atlassian.net/wiki/spaces/DEVOPS/pages/1694236886/Terraform+Runner+Quickstart).
- If you encounter any issues or have questions, reach out to the team on the **#platform** slack channel.

### Vault Configuration Updates
- Any secrets required for this service will be stored in Vault. For any updates to the Vault configuration, please consult with the **#platform** team and submit a workflow request.

### Useful Links
- [ECS service config dev repository](https://github.com/companieshouse/ecs-service-configs-dev)
- [ECS service config production repository](https://github.com/companieshouse/ecs-service-configs-production)
