
package org.mule.module.facebook.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/facebook</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:42:49-05:00", comments = "Build master.1915.dd1962d")
public class FacebookNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(FacebookNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [facebook] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [facebook] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config-with-oauth", new FacebookConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("authorize", new AuthorizeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("authorize", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("authorize", new AuthorizeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("unauthorize", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("logged-user-details", new LoggedUserDetailsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("logged-user-details", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-posts", new SearchPostsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-posts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-users", new SearchUsersDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-users", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-pages", new SearchPagesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-pages", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-events", new SearchEventsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-events", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-groups", new SearchGroupsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-groups", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("search-checkins", new SearchCheckinsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("search-checkins", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-album", new GetAlbumDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-album", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-album-photos", new GetAlbumPhotosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-album-photos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-album-comments", new GetAlbumCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-album-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event", new GetEventDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-wall", new GetEventWallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-wall", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-no-reply", new GetEventNoReplyDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-no-reply", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-maybe", new GetEventMaybeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-maybe", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-invited", new GetEventInvitedDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-invited", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-attending", new GetEventAttendingDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-attending", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-declined", new GetEventDeclinedDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-declined", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-picture", new GetEventPictureDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-picture", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-photos", new GetEventPhotosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-photos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-event-videos", new GetEventVideosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-event-videos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group", new GetGroupDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group-wall", new GetGroupWallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group-wall", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group-members", new GetGroupMembersDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group-members", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group-picture", new GetGroupPictureDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group-picture", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-link", new GetLinkDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-link", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-link-comments", new GetLinkCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-link-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-note", new GetNoteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-note", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-note-comments", new GetNoteCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-note-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-note-likes", new GetNoteLikesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-note-likes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page", new GetPageDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-wall", new GetPageWallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-wall", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-picture", new GetPagePictureDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-picture", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-page-picture-from-link", new SetPagePictureFromLinkDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-page-picture-from-link", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("set-page-picture-from-source", new SetPagePictureFromSourceDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("set-page-picture-from-source", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-tagged", new GetPageTaggedDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-tagged", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-links", new GetPageLinksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-links", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-photos", new GetPagePhotosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-photos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-groups", new GetPageGroupsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-groups", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-albums", new GetPageAlbumsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-albums", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-statuses", new GetPageStatusesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-statuses", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-videos", new GetPageVideosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-videos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-notes", new GetPageNotesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-notes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-posts", new GetPagePostsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-posts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-events", new GetPageEventsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-events", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-page-checkins", new GetPageCheckinsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-page-checkins", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-photo", new GetPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("tag-photo", new TagPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("tag-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-photo-tags", new GetPhotoTagsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-photo-tags", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-photo-comments", new GetPhotoCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-photo-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-photo-likes", new GetPhotoLikesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-photo-likes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-post", new GetPostDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-post", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-post-comments", new GetPostCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-post-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-status", new GetStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-status-comments", new GetStatusCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-status-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user", new GetUserDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-search", new GetUserSearchDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-search", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-home", new GetUserHomeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-home", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-wall", new GetUserWallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-wall", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-tagged", new GetUserTaggedDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-tagged", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-posts", new GetUserPostsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-posts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-picture", new GetUserPictureDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-picture", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-friends", new GetUserFriendsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-friends", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-activities", new GetUserActivitiesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-activities", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-checkins", new GetUserCheckinsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-checkins", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-interests", new GetUserInterestsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-interests", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-music", new GetUserMusicDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-music", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-books", new GetUserBooksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-books", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-movies", new GetUserMoviesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-movies", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-television", new GetUserTelevisionDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-television", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-likes", new GetUserLikesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-likes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-photos", new GetUserPhotosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-photos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-albums", new GetUserAlbumsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-albums", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-videos", new GetUserVideosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-videos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-groups", new GetUserGroupsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-groups", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-statuses", new GetUserStatusesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-statuses", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-links", new GetUserLinksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-links", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-notes", new GetUserNotesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-notes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-events", new GetUserEventsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-events", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-inbox", new GetUserInboxDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-inbox", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-outbox", new GetUserOutboxDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-outbox", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-updates", new GetUserUpdatesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-updates", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-user-accounts", new GetUserAccountsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-user-accounts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-video", new GetVideoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-video", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-video-comments", new GetVideoCommentsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-video-comments", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-message", new PublishMessageDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-message", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-comment", new PublishCommentDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-comment", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("like", new LikeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("like", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-note", new PublishNoteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-note", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-link", new PublishLinkDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-link", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-event", new PublishEventDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("attend-event", new AttendEventDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("attend-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("tentative-event", new TentativeEventDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("tentative-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("decline-event", new DeclineEventDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("decline-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("invite-user", new InviteUserDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("invite-user", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("uninvite-user", new UninviteUserDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("uninvite-user", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-album", new PublishAlbumDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-album", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-photo", new PublishPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("publish-video", new PublishVideoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("publish-video", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-object", new DeleteObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("dislike", new DislikeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("dislike", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-checkin", new GetCheckinDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-checkin", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application", new GetApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-wall", new GetApplicationWallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-wall", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-picture", new GetApplicationPictureDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-picture", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-tagged", new GetApplicationTaggedDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-tagged", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-links", new GetApplicationLinksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-links", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-photos", new GetApplicationPhotosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-photos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-albums", new GetApplicationAlbumsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-albums", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-statuses", new GetApplicationStatusesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-statuses", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-videos", new GetApplicationVideosDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-videos", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-notes", new GetApplicationNotesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-notes", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-events", new GetApplicationEventsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-events", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application-insights", new GetApplicationInsightsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application-insights", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("download-image", new DownloadImageDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("download-image", "@Processor", ex);
        }
    }

}
