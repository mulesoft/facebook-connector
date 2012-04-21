Facebook Demo
====================

INTRODUCTION
  This application retrieves an image using facebook rest api and then uploads it to an as2 server.

HOW TO DEMO:
  In order to run this test, you need to setup the following environment variable:
    * as2UrlServer - as2 server to upload the photo
    
  * If running from FacebookFunctionalTestDriver, you just need to run getPhotoFromFbAndSendToAS2 method. 
  * If running from a Mule Container: 
         URL => http://localhost:9090/facebook-get-photo-and-send-to-as2
         Parameters => photo (Example: 347528201978388)
    
HOW IT WORKS:
    This demo application retrieves the given photo by its photo id from facebook
    and then sends it to the as2 server the user specified in the context.