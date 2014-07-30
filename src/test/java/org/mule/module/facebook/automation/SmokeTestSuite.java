/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.facebook.automation.testcases.AttendEventTestCases;
import org.mule.module.facebook.automation.testcases.DeclineEventTestCases;
import org.mule.module.facebook.automation.testcases.DeleteObjectTestCases;
import org.mule.module.facebook.automation.testcases.GetAlbumTestCases;
import org.mule.module.facebook.automation.testcases.GetEventInvitedTestCases;
import org.mule.module.facebook.automation.testcases.GetLinkCommentsTestCases;
import org.mule.module.facebook.automation.testcases.GetLinkTestCases;
import org.mule.module.facebook.automation.testcases.GetNoteTestCases;
import org.mule.module.facebook.automation.testcases.GetPhotoTagsTestCases;
import org.mule.module.facebook.automation.testcases.GetPhotoTestCases;
import org.mule.module.facebook.automation.testcases.GetStatusCommentsTestCases;
import org.mule.module.facebook.automation.testcases.InviteUserTestCases;
import org.mule.module.facebook.automation.testcases.LikeTestCases;
import org.mule.module.facebook.automation.testcases.LoggedUserDetailsTestCases;
import org.mule.module.facebook.automation.testcases.PublishAlbumTestCases;
import org.mule.module.facebook.automation.testcases.PublishCommentTestCases;
import org.mule.module.facebook.automation.testcases.PublishEventTestCases;
import org.mule.module.facebook.automation.testcases.PublishLinkTestCases;
import org.mule.module.facebook.automation.testcases.PublishMessageTestCases;
import org.mule.module.facebook.automation.testcases.PublishNoteTestCases;
import org.mule.module.facebook.automation.testcases.PublishPhotoTestCases;
import org.mule.module.facebook.automation.testcases.PublishVideoTestCases;
import org.mule.module.facebook.automation.testcases.SmokeTests;
import org.mule.module.facebook.automation.testcases.TagPhotoTestCases;
import org.mule.module.facebook.automation.testcases.TentativeEventTestCases;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({
	AttendEventTestCases.class,
	DeclineEventTestCases.class,
	DeleteObjectTestCases.class,
	GetAlbumTestCases.class,
	GetEventInvitedTestCases.class,
	GetLinkCommentsTestCases.class,
	GetLinkTestCases.class,
	GetNoteTestCases.class,
	GetPhotoTagsTestCases.class,
	GetPhotoTestCases.class,
	GetStatusCommentsTestCases.class,
	InviteUserTestCases.class,
	LikeTestCases.class,
	LoggedUserDetailsTestCases.class,
	PublishAlbumTestCases.class,
	PublishCommentTestCases.class,
	PublishEventTestCases.class,
	PublishLinkTestCases.class,
	PublishMessageTestCases.class,
	PublishNoteTestCases.class,
	PublishPhotoTestCases.class,
	PublishVideoTestCases.class,
	TagPhotoTestCases.class,
	TentativeEventTestCases.class
})

public class SmokeTestSuite {
	
}