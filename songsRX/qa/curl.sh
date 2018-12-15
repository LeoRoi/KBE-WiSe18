#!/usr/bin/env bash

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL XML SONGS ------------"
curl -X GET -H "Accept: application/xml" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG NUMBER 0 IN JSON ------------"
curl -X GET -H "Accept: application/xml" -v "http://localhost:8080/songsRX/rest/songs/0"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG NUMBER 2 IN XML ------------"
curl -X GET -H "Accept: application/xml" -v "http://localhost:8080/songsRX/rest/songs/2"
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

echo "--- DELETE SONG 0 JSON ------------"
curl -X DELETE -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs/0"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"