commit              := $(shell git rev-parse --short HEAD)
tag                 := $(shell git tag -l 'v*-rc*' --points-at HEAD)
version             := $(shell if [[ -n "$(tag)" ]]; then echo $(tag) | sed 's/^v//'; else echo $(commit); fi)
artifactory_publish := $(shell if [[ -n "$(tag)" ]]; then echo release; else echo dev; fi)

.PHONY: all
all: build

.PHONY: clean
clean:
    mvn clean

.PHONY: build
build:
    mvn compile

.PHONY: test
test: test-unit

.PHONY: test-unit
test-unit:
    mvn test

.PHONY: package
package:
    mvn versions:set -DnewVersion=$(version) -DgenerateBackupPoms=false
    mvn package -DskipTests=true

.PHONY: dist
dist: clean package

.PHONY: publish
publish:
    mvn jar:jar deploy:deploy -DpublishRepo=$(artifactory_publish)

.PHONY: sonar
sonar:
    mvn sonar:sonar