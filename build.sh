#!/bin/bash
cd storage-spec
./gradlew build && ./gradlew jar
cd ../storage-local
./gradlew build && ./gradlew jar
cd ../storage-gdrive
./gradlew build && ./gradlew jar
cd ../storage-app
./gradlew build && ./gradlew jar
cd ..
