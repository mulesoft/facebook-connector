
package org.mule.module.facebook.processors;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.module.facebook.FacebookConnector;
import org.mule.module.facebook.oauth.FacebookConnectorOAuthManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * PublishMessageMessageProcessor invokes the {@link org.mule.module.facebook.FacebookConnector#publishMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)} method in {@link FacebookConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T04:37:34-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class PublishMessageMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object profile_id;
    protected String _profile_idType;
    protected Object msg;
    protected String _msgType;
    protected Object picture;
    protected String _pictureType;
    protected Object link;
    protected String _linkType;
    protected Object caption;
    protected String _captionType;
    protected Object linkName;
    protected String _linkNameType;
    protected Object description;
    protected String _descriptionType;
    protected Object place;
    protected String _placeType;

    public PublishMessageMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets picture
     * 
     * @param value Value to set
     */
    public void setPicture(Object value) {
        this.picture = value;
    }

    /**
     * Sets profile_id
     * 
     * @param value Value to set
     */
    public void setProfile_id(Object value) {
        this.profile_id = value;
    }

    /**
     * Sets description
     * 
     * @param value Value to set
     */
    public void setDescription(Object value) {
        this.description = value;
    }

    /**
     * Sets link
     * 
     * @param value Value to set
     */
    public void setLink(Object value) {
        this.link = value;
    }

    /**
     * Sets caption
     * 
     * @param value Value to set
     */
    public void setCaption(Object value) {
        this.caption = value;
    }

    /**
     * Sets place
     * 
     * @param value Value to set
     */
    public void setPlace(Object value) {
        this.place = value;
    }

    /**
     * Sets msg
     * 
     * @param value Value to set
     */
    public void setMsg(Object value) {
        this.msg = value;
    }

    /**
     * Sets linkName
     * 
     * @param value Value to set
     */
    public void setLinkName(Object value) {
        this.linkName = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(FacebookConnectorOAuthManager.class, false, event);
            final String _transformedProfile_id = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_profile_idType").getGenericType(), null, profile_id));
            final String _transformedMsg = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_msgType").getGenericType(), null, msg));
            final String _transformedPicture = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_pictureType").getGenericType(), null, picture));
            final String _transformedLink = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_linkType").getGenericType(), null, link));
            final String _transformedCaption = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_captionType").getGenericType(), null, caption));
            final String _transformedLinkName = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_linkNameType").getGenericType(), null, linkName));
            final String _transformedDescription = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_descriptionType").getGenericType(), null, description));
            final String _transformedPlace = ((String) evaluateAndTransform(getMuleContext(), event, PublishMessageMessageProcessor.class.getDeclaredField("_placeType").getGenericType(), null, place));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return true;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((FacebookConnector) object).publishMessage(_transformedProfile_id, _transformedMsg, _transformedPicture, _transformedLink, _transformedCaption, _transformedLinkName, _transformedDescription, _transformedPlace);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(String.class)));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(FacebookConnector.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at FacebookConnector at publishMessage retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
