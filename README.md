

=======================================

## About:
Cake Manager Micro Service (fictitious) implementation for microservice

Requirements:

* By accessing the root of the server (/) it should be possible to list the cakes currently in the system.  This
 must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
 the cakes currently in the system as JSON data.

* Accessing the /cakes endpoint with a web browser must show the human presentation of the list of cakes.

* The /cakes endpoint must also allow new cakes to be created.

 System requirements
 ====================================
 java  : 1.8
 maven : 3.5.0

 starting cake manager services :
 mvn spring-boot:run
 or
 command prompt : < jar directory> java -jar cake-manager-app-0.0.1-SNAPSHOT.jar

 cake manager home page url : http://localhost:8282/



