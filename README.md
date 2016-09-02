# RestfulApi
A simple app with a rest api interface representing a phone agenda. Currently it doesn't do anything of this sort :P 

It uses a docker maven plugin to connect to a mysql database.

To run this project you need Maven and Docker installed in your PC.

* Donwload and install Apache Maven:
https://maven.apache.org/download.cgi
* Download and install Docker:
https://docs.docker.com/engine/installation/

To **run** the Api with Maven:
* Go to the root directory of the project
* mvn clean install jetty:run

To **stop** the Api:
* mvn jetty:stop docker:stop

Open a browser and enter: http://127.0.0.1:8080/RestfulApi/rest/

TADA!!!
#To be continued
