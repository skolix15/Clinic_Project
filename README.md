------INSTRUCTIONS FOR ADDING THE EXTERNAL LIBRARIES AND DATABASE DUMPS------

Two external libraries are used for the operation of the program. 
One library concerns the production of sales and supply charts (JFreeChart), 
while the second concerns the connectivity with the database (JConnector).

So, in order to compile and run the program successfuly,
it is necessary to add in your build path
the two libraries that have been mentioned above.

JFreeChart:
The library that is used for the production of the charts is JFreeChart
and the .jar files of the this library are saved in the folder JFreeChartLib.

JConnector: 
If you have installed the MySQL Workbench using the MySQL Installer, normally you have
also installed the JConnector which can be found usually in the following path:
C:\Program Files (x86)\MySQL\Connector J (the_version_you_have)
If not, you should probably install the JConnector from the official website of MySQL.

DATABASE:
In the folder Database Dumps you will find the dump of the database that's used for the program.
In order to use it you must:
1. Create a database (schema) in MySQL Workbench with the namw "mydb19"
2. Import the dump folder as is, in the database "mydb19" 

Afterwards you're good to go to manage your Clinic's supply chain and human resources!!!
