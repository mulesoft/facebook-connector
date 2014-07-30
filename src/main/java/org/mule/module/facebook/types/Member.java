/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */



/* * Copyright (c) 2012 Zauber S.A. -- All rights reserved */
 package org.mule.module.facebook.types;

import com.restfb.types.NamedFacebookType;
 
/**
 * Class representing a member returned when calling graph api {group}/members
 * */
public class Member extends NamedFacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;
    
    private boolean administrator;

    
    public boolean getAdministrator()
    {
        return administrator;
    }
    
    public void setAdministrator(boolean administrator)
    {
        this.administrator = administrator;
    }

}