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

