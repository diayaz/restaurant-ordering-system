#!/bin/sh
# Runs the application. Pass --demo for the scripted demonstration.
cd "$(dirname "$0")"
[ -d out ] || ./build.sh
java -cp out restaurant.Main "$@"
