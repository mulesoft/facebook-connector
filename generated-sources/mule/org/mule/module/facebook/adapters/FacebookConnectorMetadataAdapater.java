
package org.mule.module.facebook.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.module.facebook.FacebookConnector;


/**
 * A <code>FacebookConnectorMetadataAdapater</code> is a wrapper around {@link FacebookConnector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:42:49-05:00", comments = "Build master.1915.dd1962d")
public class FacebookConnectorMetadataAdapater
    extends FacebookConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Facebook";
    private final static String MODULE_VERSION = "2.3.2-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "master.1915.dd1962d";
    private final static String MIN_MULE_VERSION = "3.5";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

}
