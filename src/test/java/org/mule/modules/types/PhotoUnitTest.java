/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.Photo;
import org.mule.module.facebook.types.Photo.Image;
import org.mule.module.facebook.types.Photo.Tag;

import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
 
public class PhotoUnitTest
{
    private Photo photo;
    private Tag tag;
    private Image image;
    
    @Before
    public void setup()
    {
        final String stringDate = "2009-06-25T19:14:25+0000";
        List<Tag> tags = new ArrayList<Photo.Tag>();
        List<Comment> comments = new ArrayList<Comment>();
        List<Image> images = new ArrayList<Photo.Image>();
        this.photo = new Photo(mock(CategorizedFacebookType.class), "", "", 1, 2, "", "", 3, stringDate, stringDate,
                               tags, comments, 4L, images);
        tag = new Tag(1, 2, stringDate);
        image = new Image(1, 2, "");
    }
    
    @Test
    public void testPhotoGettersAndSetters()
    {
        assertNotNull(photo.getComments());
        assertNotNull(photo.getCreatedTime());
        assertNotNull(photo.getUpdatedTime());
        assertNotNull(photo.getFrom());
        assertNotNull(photo.getHeight());
        assertNotNull(photo.getWidth());
        assertNotNull(photo.getLikes());
        assertNotNull(photo.getIcon());
        assertNotNull(photo.getImages());
        assertNotNull(photo.getPicture());
        assertNotNull(photo.getPosition());
        assertNotNull(photo.getSource());
        assertNotNull(photo.getLink());
        assertNotNull(photo.getTags());
    }
    
    @Test
    public void testTagGettersAndSetters()
    {
        assertNotNull(tag.getX());
        assertNotNull(tag.getY());
        assertNotNull(tag.getCreatedTime());
    }
    
    @Test
    public void testImageGettersAndSetters()
    {
        assertNotNull(image.getHeight());
        assertNotNull(image.getWidth());
        assertNotNull(image.getSource());
    }
}

