************************************************************************************************************************
                                                                                                           README
************************************************************************************************************************

Target : 
--------
To build a small search app to search a bunch of campaigns by title and tagline.

Language and library: 
------------------------
Java - 1.7, Maven, json-simple, JUnit.

Contents of the tar :
----------------------
The tar consists of maven and the search-app folder

Assumptions:
---------------
1. Availability of JRE to run the project
2. Setting of JAVA_HOME to run unit test cases


Brief description of the final project:
-----------------------------------------

1. A java console based application that search campaigns both by title and tagline and returns results sorted by relevance.
2. Indexes used : forward and Inverted indexes.
3. Sorts the result based on cosine similarity to the query string
4. Haven't applied any threshold on the cosine value, so a campaingn is matched even if there is a 1 word match with that of the query tokens.
5. Components of the search app : 
	JSONImporter : to import the campaigns from the JSON file  
	SearchEngine : Builds and maintains indexes and facilitates the search of campaign based on title and tagline. Sorts the results based on cosine similarity with the query string.
	Tokenizer : A simple way to tokenize the tokens of both query string and campaign text.
6. The project is eclipse friendly with all the dependencies setup. So it is easily importable in eclipse.
	    

Instructions to run :
---------------------

1. Untar the contents of the project.tar.
2. Navigate to the search app folder in your terminal.
3. Issue the following command to run the project
     java -cp target/search-app-1.0-SNAPSHOT-jar-with-dependencies.jar  com.search.app.App 

Intructions to run unit tests:
--------------------------------
1. This is possible only if JAVA_HOME is set.
2. Untar the contents of project.tar
3. Terminal to the extracted directory.
4. Make setup.sh executable by "chmod u+x setup.sh".
5. Then issue the command  ". ./setup.sh"
6. The above instructions helps setting up the maven (which is temporary, lives until the life of the terminal).
7. cd to search-app/
8. Issue "mvn compile clean"
9. Issue "mvn test" 
10. Issue "mvn package" 


Interacting with the application:
------------------------------------
1.  Once the application is started and it has finished initializing its dataStore and indexes, you are prompted with the following text providing  3 options.
      "Enter your option from the list 1. Display all campaigns  2. Search by title and tagline 3. Exit"
2. Choose an option by typing its corresponding number. eg., 1
3. If you choose "1", it displays all the available campaigns in the following format :
********************************************************************************************************
Campaign - <Serial Number>
********************************************************************************************************
<title>
<tagline>
<link to the campaign itself>
<link to the photograph-small>

After all the campaigns are listed, you are asked whether you are intending to view one of them in browser.
Type "yes" and type an integer in [1,9] to open a campaign in browser.

4. If you choose "2", you are prompted to enter a search query. Type your search query and hit enter to see the relevant results based on title and tagline of all the campaigns.
     In the same way as the previous option you are prompted whether you want to open up the campaign in the browser.

References:
-------------
Maven : http://www.mkyong.com/maven/
JSONParser : http://examples.javacodegeeks.com/core-java/json/java-json-parser-example/

