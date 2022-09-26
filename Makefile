PROJECT_SRC="$(PWD)/src/main/kotlin/co/practices/parkinglot"

.PHONY: build
build:
	@kotlinc ${PROJECT_SRC} -include-runtime -d out/parking-lot.jar

.PHONY: run
run:
	@java -jar out/parking-lot.jar

.PHONY: test
test:
	@./gradlew test

.PHONY: all
all: build run
