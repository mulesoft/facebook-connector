 package org.mule.module.facebook.types;

import java.io.Serializable;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;
 
public class NamedFacebookTypeList implements Serializable
{
    @Facebook
    private List<NamedFacebookType> data;
}

