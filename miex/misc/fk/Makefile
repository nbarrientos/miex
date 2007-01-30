#!/bin/bash

all: clean build

build:
	g++ -Wall -I include/ -c Miex.cc -o Miex.o -fPIC
	g++ -o Miex.so Miex.o -shared

clean:
	rm -rf *.o *.so
