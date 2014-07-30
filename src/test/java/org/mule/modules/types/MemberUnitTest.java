/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.Member;
 
public class MemberUnitTest
{
    private Member member;
    
    @Before
    public void setup()
    {
        member = new Member();
    }
    
    @Test
    public void testGettersAndSetters()
    {
        member.setAdministrator(true);
        assertTrue(member.getAdministrator());
    }
}

