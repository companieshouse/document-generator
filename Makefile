artifact_name  := document-generator
commit         := $(shell git rev-parse --short HEAD)
tag            := $(shell git tag -l 'v*-rc*' --points-at HEAD)
version        := $(shell if [[ -n "$(tag)" ]]; then echo $(tag) | sed 's/^v//'; else echo $(commit); fi)

.PHONY: all
all: build

.PHONY: submodules
submodules: api-enumerations/.git

.PHONY: api-enumerations/.git
	git submodule init
	git submodule update

.PHONY: clean
clean:
	mvn clean
	rm -f *.zip
	rm -rf build-*
	rm -f log4j2.xml

.PHONY: test
test: test-unit

.PHONY: test-unit
test-unit:
	mvn test

.PHONY: build
build: submodules
	mvn package
	# Moving JAR to repo root so the path relative to the start script is the same in dev as it is in the build artefact
	mv ./target/$(artifact_name).jar ./$(artifact_name).jar

.PHONY: package
package:
	$(eval tmpdir:=$(shell mktemp -d build-XXXXXXXXXX))
	# Will fail build until jars moved to sub module
	# cp ./$(artifact_name).jar $(tmpdir)
	cp ./start.sh $(tmpdir)
	cp -r ./api-enumerations $(tmpdir)
	cd $(tmpdir) && zip -r ../$(artifact_name)-$(version).zip *
	rm -rf $(tmpdir)

.PHONY: dist
dist: clean build package

.PHONY: sonar
sonar:
	mvn sonar:sonar
