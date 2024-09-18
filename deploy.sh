#!/bin/bash

# Gradle 빌드 및 shadowJar 생성
./gradlew clean build shadowJar

# SAM 빌드
sam build

# SAM 배포 (변경 세트 확인 없이)
sam deploy --no-confirm-changeset
