 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.NamedFacebookTypeList;

import com.restfb.types.NamedFacebookType;
 
public class NamedFacebookTypeListUnitTest
{
    private NamedFacebookTypeList list;
    
    @Before
    public void setup()
    {
        list = new NamedFacebookTypeList();
    }
    
    @Test
    public void testGettersAndSetters()
    {
        final List<NamedFacebookType> mockList = new ArrayList<NamedFacebookType>();
        mockList.add(mock(NamedFacebookType.class));
        mockList.add(mock(NamedFacebookType.class));
        list.setData(mockList);
        assertEquals(list.getData().size(), 2);
    }
}
