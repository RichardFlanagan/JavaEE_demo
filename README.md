# JavaEE_demo
A demo using JavaEE to create a REST library system

This system uses HSQLDB as a database: 
http://hsqldb.org/

A handy command to launch the DB (replace <dbname>):
java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/hemrajdb --dbname.0 <dbname>

You also require Tomcat8:
http://tomcat.apache.org/download-80.cgi


To operate the system (using the zip file package):
1. Run the server by navigating to the workspace and running hsqldb/launchDB.bat
2. Run the Tomcat server
3. Run LaunchClient.java
4. The root URL is: http://localhost:8080/A00193644_RichardFlanagan/library


![alt tag](http://imgur.com/244sgHq.png)
