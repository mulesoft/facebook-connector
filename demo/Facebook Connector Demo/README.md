Facebook Connector Demo Application
===================================

Description:
Based on the user URL input this demo application performs the following -

* / - Return the logged user details.
* /photos - Returns a list of user uploaded photos.
* /videos - Returns a list of user uploaded videos.
* /taggable_friends - Returns a list of user taggable friends.
* /tweet - Searches for the likes of one of your friends, and then tweets the link of that like.


To be able to successfully run this application, this config values must be set in mule.properties file:
    -Config values for the facebook connector
    -Config values for twitter connector

Usage:
Once mule is up and running you must call http://localhost:8081/ for the app to return logged user details or http://localhost:8081/photos for the
app to return list of user uploaded photos.
