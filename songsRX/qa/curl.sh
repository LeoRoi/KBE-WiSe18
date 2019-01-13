#!/usr/bin/env bash

# get tests

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL XML SONGS ------------"
curl -X GET -H "Accept: application/xml" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG NUMBER 0 IN JSON ------------"
curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs/0"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG NUMBER 3 IN XML ------------"
curl -X GET -H "Accept: application/xml" -v "http://localhost:8080/songsRX/rest/songs/3"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- GET WITH NON-EXISTING ID ---------------"
curl -X GET \
     -H "Accept: *" \
     -v "http://localhost:8080/songsRX/rest/songs/2222"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- GET WITH BAD ID ---------------"
curl -X GET \
     -H "Accept: *" \
     -v "http://localhost:8080/songsRX/rest/songs/notANumber"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- GET WITH NO/BAD PARAM ---------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/NO"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

# delete tests

echo "--- DELETE SONG 0 JSON ------------"
curl -X DELETE -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs/0"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETE SONG 1 JSON ------------"
curl -X DELETE -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs/1"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

# post tests

echo "--- POSTING A SONG IN JSON ------------------"
curl -X POST \
     -H "Content-Type: application/json" \
     -d "@einSong.json" \
     -v "http://localhost:8080/songsServlet"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING THE NEW SONG NUMBER IN JSON --"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsServlet?songId=11"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A NON-JSON LOVE SONG ----"
curl -X POST \
     -H "Content-Type: application/json" \
     -d "@notALoveSong.txt" \
     -v "http://localhost:8080/songsServlet"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING AN EMPTY FILE ----"
curl -X POST \
     -H "Content-Type: application/json" \
     -d "@emptyFile.txt" \
     -v "http://localhost:8080/songsServlet"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

# post new xml

# put tests