#!/usr/bin/env bash

time (
    if ! ./gradlew clean build -Dseam=model; then
        exit
    fi

    if ! ./gradlew build -Dseam=rest; then
        exit
    fi
)