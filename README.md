DataBase Installation Directions:-

Step 1- Download HSQLDB bundle from this link -> https://sourceforge.net/projects/hsqldb/files/
        Click on HSQLDB and the download of zip will start immediately.

Step 2- Extract the zip and place it into C:\ directory.

Step 3- Create a default database for HSQLDB. For that, create properties file named "server.properties". 
        Below are the properties to be mentioned in server.properties-
        server.database.0 = file:hsqldb/demodb
        server.dbname.0 = testdb

Step 4- Place this server.properties file into HSQLDB home directory that is C:\hsqldb- 2.4.0\hsqldb\.

Step 5- Execute below commands-
        \>cd C:\hsqldb-2.4.0\hsqldb
        hsqldb>java -classpath lib/hsqldb.jar org.hsqldb.server.Server

Step 6- Later, you will get to find the following folder structure of the hsqldb directory in the HSQLDB home directory that is 
        C:\hsqldb-2.4.0\hsqldb. Those files are temp file, lck file, log file, properties file, and script file of demodb database created by 
        HSQLDB database server.

Step 7- Start database server using below commands
        \>cd C:\hsqldb-2.4.0\hsqldb
        hsqldb>java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0
        file:hsqldb/demodb --dbname.0 testdb

TechStack:-
Maven
Java 8

Database:-
HSQLDB 

Dependencies used:-
Jackson- for ObjectMapper
Logback- for Logs
HSQLDB - for database

Steps to run the project:-
1.) Clone project into your local machine.

2.)Install Maven and set M2_HOME as the location of Maven and path variable as "%M2_HOME%\bin" in the environment variables.

3.) Install JDK 8 and set JAVA_HOME as the location of JDK 8 and path variable as "%JAVA_HOME%\bin" in the environment variables.

3.) From the project run below commands :-
mvn compile
mvn clean install

4.) A target folder will be created in the project. From this folder run below command to make the execution of project successful-
java -jar <name of jar> <path of logfile.txt>

