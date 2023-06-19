.PHONY: all compile run

all: compile run

compile:
	mvn compile

run:
	mvn -e exec:java