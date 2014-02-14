
package org.mule.module.facebook.connection;

import javax.annotation.Generated;


/**
 * Exception thrown when the release connection operation of the
 *  connection manager fails.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T03:46:38-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class UnableToReleaseConnectionException
    extends Exception
{

     /**
     * Create a new exception
     *
     * @param throwable Inner exception
     */
    public UnableToReleaseConnectionException(Throwable throwable) {
        super(throwable);
    }
}
