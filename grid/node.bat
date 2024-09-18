@echo off
echo Starting Selenium Node...
java -jar C:\Users\karth\Downloads\selenium-server-4.24.0.jar node --detect-drivers true --publish-events tcp://192.168.0.164:4442 --subscribe-events tcp://192.168.0.164:4443
pause