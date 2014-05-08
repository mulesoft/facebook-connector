Facebook Anypoint Connector Release Notes
=========================================

Date: 5-May-2014

Version: 2.3.2

Supported API versions: [v1.0](https://developers.facebook.com/docs/graph-api/reference/v1.0)

Supported Mule Runtime Versions: 3.4.x, 3.5.0

Features and functionality
--------------------------

| Node   	| Supported operations                                                                                                                                                                                                                                    	|
|--------	|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| Posts  	| Get, Search, Get comments, Publish, Publish comment, Like                                                                                                                                                                                               	|
| Users  	| Get, Search, Get News Feed, Get Wall, Get picture, Get friends, Get activities, Get interests (music, books, movies, TV, likes), Get photos, Get albums, Get videos, Get statuses, Get links, Get notes, Get events, Get inbox, Get outbox, Get updates 	|
| Pages  	| Get, Search, Get Wall, Get profile picture, Set profile picture, Get tagged posts, Get links, Get photos, Get groups, Get albums, Get statuses, Get videos, Get notes, Get posts, Get events, Get checkins                                              	|
| Events 	| Get, Get Wall, Get users per RSVP (No reply, Declined, Maybe, Attending), Get picture, Get photos, Get videos, Publish, RSVP                                                                                                                            	|
| Groups 	| Get, Get Wall, Get members, Get picture                                                                                                                                                                                                                 	|
| Albums 	| Get, Get photos, Get comments, Publish, Publish photo                                                                                                                                                                                                   	|
| Video  	| Get, Get comments, Publish                                                                                                                                                                                                                              	|
| Notes  	| Get, Get comments, Get likes, Publish                                                                                                                                                                                                                   	|

Closed issues in release
------------------------
The following operations have been fixed in this release:
* Publishing an event
* Attending an event
* Declining an event invitation

Videos are no longer corrupted before they are uploaded.

Known issues
------------

* Deleting videos always returns "Unsupported delete request" because of an [API bug](https://developers.facebook.com/bugs/1595003230723916/)
* Searching for checkins is broken because of an [API bug](https://developers.facebook.com/bugs/536595293095881)
