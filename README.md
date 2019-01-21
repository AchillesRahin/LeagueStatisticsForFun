# LeagueStatisticsForFun
various calls to collect json data from riot api and use it to give data for personal research or analytics. 

must use your own personal riot api key to get project to work.

Only External jar needed was the java-json.jar 

Outside of the Runner package all other packages are independently used and can be used as backend 
for a desktop app/webapp/etc

Runner package was made to test the use of data collection.

Caching would be important if made into full project rather than personal one as calls will take long
time.

Currently the project runs standalone without db. If one were to want dbs for the current info or analytics available
in project the suggestion would be as below including caching suggestion. 

Simple caching could create dbs as below. 
Doing this will simply allow for collecting duo partner history/finding what champs you are good or weak against
Caching match history with this limited info will limit what is possible to above + season limits. 
More extensive DTOs would be necessary to create further analytics. 

dbs -
-matches table
stores details of individual matchids
winning team will always be player 1-5
matchid|player1-5|player6-10

-player/match table
stores playerid|matchid|date|season|champion played

-playerinfo table
stores player accountid|ign|playerid
