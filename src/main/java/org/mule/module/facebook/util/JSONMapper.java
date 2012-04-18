/**
 * Mule Facebook Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.facebook.util;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.mule.modules.utils.MuleSoftException;

/**
 * Mapper to serialize Facebook responses to {@link Map}s
 * */
public class JSONMapper
{
    private static ObjectMapper mapper = new ObjectMapper();
    
    private JSONMapper()
    {
        
    }
    
    public static Map<String, Object> toMap(String response)
    {
        try
        {
            return mapper.readValue(response, new TypeReference<Map<String, Object>>(){});
        }
        catch (Exception e)
        {
            throw MuleSoftException.soften(e);
        }
    }

}