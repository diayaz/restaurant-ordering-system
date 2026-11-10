@echo off
rem Compiles all sources into the "out" folder
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
del sources.txt
echo Build finished. Run: run.bat  or  run.bat --demo
