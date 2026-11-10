#!/bin/sh
# Compiles all sources into the "out" folder
cd "$(dirname "$0")"
rm -rf out
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java") && echo "Build successful. Run: ./run.sh  or  ./run.sh --demo"
