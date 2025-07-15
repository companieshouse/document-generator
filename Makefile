artifact_name       := document-generator
artifact_core_name  := document-generator-api
version             := unversioned

.PHONY: all
all: build

.PHONY: submodules
submodules:
	git submodule init
	git submodule update

.PHONY: clean
clean:
	mvn clean
	rm -f ./$(artifact_name).jar
	rm -f ./$(artifact_name)-*.zip
	rm -rf ./build-*

.PHONY: build
build: submodules
	mvn versions:set -DnewVersion=$(version) -DgenerateBackupPoms=false
	mvn package -DskipTests=true
	mkdir ./target
	cp ./$(artifact_core_name)/target/$(artifact_core_name)-$(version).jar ./target/$(artifact_name)-$(version).jar
	mv ./$(artifact_core_name)/target/$(artifact_core_name)-$(version).jar ./$(artifact_name).jar

.PHONY: test
test: test-unit

.PHONY: test-unit
test-unit: clean
	mvn test

.PHONY: package
package:
ifndef version
	$(error No version given. Aborting)
endif
	$(info Packaging version: $(version))
	mvn versions:set -DnewVersion=$(version) -DgenerateBackupPoms=false
	mvn package -DskipTests=true
	$(eval tmpdir:= $(shell mktemp -d build-XXXXXXXXXX))
	mkdir $(tmpdir)/document-generator-common
	cp -r ./document-generator-common/api-enumerations $(tmpdir)/document-generator-common
	cp ./target/$(artifact_name)-$(version).jar $(tmpdir)/$(artifact_name).jar
	cd $(tmpdir); zip -r ../$(artifact_name)-$(version).zip *
	rm -rf $(tmpdir)

.PHONY: dist
dist: clean build package

.PHONY: sonar
sonar:
	mvn sonar:sonar

.PHONY: sonar-pr-analysis
sonar-pr-analysis:
	mvn sonar:sonar -P sonar-pr-analysis

.PHONY: dependency-check
dependency-check: build package
	mvn install -DskipTests
	/opt/scripts/dependency-check-runner --repo-name=document-generator

.PHONY: dependency-check-local
dependency-check-local: build package
	mvn install -DskipTests
	docker run --rm -e DEPENDENCY_CHECK_SUPPRESSIONS_HOME=/opt -v "$$(pwd)":/app -w /app 416670754337.dkr.ecr.eu-west-2.amazonaws.com/dependency-check-runner --repo-name=document-generator
