#!/usr/bin/env bash

CONTAINER_ENGINE=podman

$CONTAINER_ENGINE compose up -d --no-recreate

$CONTAINER_ENGINE exec container-kafka rpk topic create log hop1 hop2 hop3 final