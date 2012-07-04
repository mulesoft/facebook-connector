 package org.mule.module.facebook.types;

import java.io.Serializable;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;
 
public class NamedFacebookTypeList implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Facebook
    private List<NamedFacebookType> data;

    
    public List<NamedFacebookType> getData()
    {
        return data;
    }
}