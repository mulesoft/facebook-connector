Facebook Anypoint Connector Release Notes
=========================================

Date: 30-Jul-2014

Version: 2.3.4

Supported API versions: [v1.0](https://developers.facebook.com/docs/graph-api/reference/v1.0)

Supported Mule Runtime Versions: 3.4.x, 3.5.x

Features and functionality
--------------------------

| Node   	| Supported operations                                                                                                                                                                                                                                                                                                  	|
|--------	|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| Posts  	| Get, Search, Get comments, Publish, Publish comment, Like                                                                                                                                                                                                                                                             	|
| Users  	| Get, Search, Get News Feed, Get Wall, Get picture, Get friends, Get activities, Get interests (music, books, movies, TV, likes), Get photos, Get photos uploaded, Get albums, Get videos, Get videos uploaded, Get statuses, Get links, Get notes, Get events, Get inbox, Get outbox, Get updates, Get taggable friends 	|
| Pages  	| Get, Search, Get Wall, Get profile picture, Set profile picture, Get tagged posts, Get links, Get photos, Get groups, Get albums, Get statuses, Get videos, Get notes, Get posts, Get events, Get checkins                                                                                                            	|
| Events 	| Get, Get Wall, Get users per RSVP (No reply, Declined, Maybe, Attending), Get picture, Get photos, Get videos, Publish, RSVP                                                                                                                                                                                            	|
| Groups 	| Get, Get Wall, Get members, Get picture                                                                                                                                                                                                                                                                                 	|
| Albums 	| Get, Get photos, Get comments, Publish, Publish photo                                                                                                                                                                                                                                                                   	|
| Video  	| Get, Get comments, Publish                                                                                                                                                                                                                                                                                              	|
| Notes  	| Get, Get comments, Get likes, Publish                                                                                                                                                                                                                                                                                    	|

Closed issues in release
------------------------
The following new operations have been implemented in this release:
* List of videos that were published by user (Get user videos uploaded).
* List of photos that were published by user (Get user photos uploaded).

Known issues
------------

* Deleting videos always returns "Unsupported delete request" because of an [API bug](https://developers.facebook.com/bugs/1595003230723916/)
* Searching for checkins is broken because of an [API bug](https://developers.facebook.com/bugs/536595293095881)
