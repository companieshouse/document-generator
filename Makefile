artifact_name       := document-generator
artifact_core_name  := document-generator-core
commit              := $(shell git rev-parse --short HEAD)
tag                 := $(shell git tag -l 'v*-rc*' --points-at HEAD)
version             := $(shell if [[ -n "$(tag)" ]]; then echo $(tag) | sed 's/^v//'; else echo $(commit); fi)
artifactory_publish := $(shell if [[ -n "$(tag)" ]]; then echo release; else echo dev; fi)

.PHONY: all
all: build

.PHONY: clean
clean:
	mvn clean
	rm -f ./$(artifact_core-name).jar
	rm -f ./$(artifact_name)-*.zip
	rm -rf ./build-*

.PHONY: build
build:
	mvn versions:set -DnewVersion=$(version) -DgenerateBackupPoms=false
	mvn package -DskipTests=true
	mv ./$(artifact_core_name)/target/$(artifact_core_name)-$(version).jar ./$(artifact_core_name)/$(artifact_core_name).jar

.PHONY: test
test: test-unit

.PHONY: test-unit
test-unit: clean
	mvn test

.PHONY: package
package:
	@test -s ./$(artifact_core_name)/$(artifact_core_name).jar || { echo "ERROR: Service JAR not found: $(artifact_core_name)"; exit 1; }
	$(eval tmpdir:=$(shell mktemp -d build-XXXXXXXXXX))
	cp ./start.sh $(tmpdir)
	cd $(tmpdir); zip -r ../$(artifact_core_name)-$(version).zip *
	rm -rf $(tmpdir)

.PHONY: dist
dist: clean build package

.PHONY: sonar
sonar:
	mvn sonar:sonar
