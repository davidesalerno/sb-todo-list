#!/usr/bin/env bash

CONTAINER_ENGINE=podman

$CONTAINER_ENGINE exec container-kafka rpk topic delete log hop1 hop2 hop3 final
$CONTAINER_ENGINE compose down -v

