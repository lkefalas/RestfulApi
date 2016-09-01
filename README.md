# RestfulApi
A simple app with a rest api interface representing a phone agenda. Currently it doesn't do anything of this sort :P 

It uses a docker maven plugin to connect to a mysql database.

To run this project you need Maven, Apache and Docker installed in your PC.

Donwload and install Apache Maven:
https://maven.apache.org/download.cgi

Download and install Apache Tomcat:
https://www.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html

Download the JDBC MySql connector from:
https://dev.mysql.com/downloads/file/?id=462849
And copy it to the {apache_dir}/lib

Download Apache from 

To run this project from Eclipse:
1. File -> Import -> Maven -> Existing Maven Projects -> Find the pom file of the project
2. Run -> Run Configurations -> Maven -> New Launch configuration
3. Enter the workspace of this project and as a goal enter clean install
4. Deploy it to an Apache server

Open a browser and enter: http://127.0.0.1:8080/RestfulApi/rest/

TADA!!!
#To be continued
