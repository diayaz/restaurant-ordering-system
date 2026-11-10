@echo off
rem Runs the application. Pass --demo for the scripted demonstration.
cd /d "%~dp0"
if not exist out call build.bat
java -cp out restaurant.Main %*
