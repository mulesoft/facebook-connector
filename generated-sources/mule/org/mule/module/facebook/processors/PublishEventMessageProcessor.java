
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
 * PublishEventMessageProcessor invokes the {@link org.mule.module.facebook.FacebookConnector#publishEvent(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)} method in {@link FacebookConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-24T12:40:55-05:00", comments = "Build master.1920.518defc")
public class PublishEventMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object profile_id;
    protected String _profile_idType;
    protected Object event_name;
    protected String _event_nameType;
    protected Object start_time;
    protected String _start_timeType;
    protected Object end_time;
    protected String _end_timeType;
    protected Object description;
    protected String _descriptionType;
    protected Object location;
    protected String _locationType;
    protected Object location_id;
    protected String _location_idType;
    protected Object privacy_type;
    protected String _privacy_typeType;

    public PublishEventMessageProcessor(String operationName) {
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
     * Sets privacy_type
     * 
     * @param value Value to set
     */
    public void setPrivacy_type(Object value) {
        this.privacy_type = value;
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
     * Sets location
     * 
     * @param value Value to set
     */
    public void setLocation(Object value) {
        this.location = value;
    }

    /**
     * Sets end_time
     * 
     * @param value Value to set
     */
    public void setEnd_time(Object value) {
        this.end_time = value;
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
     * Sets start_time
     * 
     * @param value Value to set
     */
    public void setStart_time(Object value) {
        this.start_time = value;
    }

    /**
     * Sets event_name
     * 
     * @param value Value to set
     */
    public void setEvent_name(Object value) {
        this.event_name = value;
    }

    /**
     * Sets location_id
     * 
     * @param value Value to set
     */
    public void setLocation_id(Object value) {
        this.location_id = value;
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
            final String _transformedProfile_id = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_profile_idType").getGenericType(), null, profile_id));
            final String _transformedEvent_name = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_event_nameType").getGenericType(), null, event_name));
            final String _transformedStart_time = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_start_timeType").getGenericType(), null, start_time));
            final String _transformedEnd_time = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_end_timeType").getGenericType(), null, end_time));
            final String _transformedDescription = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_descriptionType").getGenericType(), null, description));
            final String _transformedLocation = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_locationType").getGenericType(), null, location));
            final String _transformedLocation_id = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_location_idType").getGenericType(), null, location_id));
            final String _transformedPrivacy_type = ((String) evaluateAndTransform(getMuleContext(), event, PublishEventMessageProcessor.class.getDeclaredField("_privacy_typeType").getGenericType(), null, privacy_type));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
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
                    return ((FacebookConnector) object).publishEvent(_transformedProfile_id, _transformedEvent_name, _transformedStart_time, _transformedEnd_time, _transformedDescription, _transformedLocation, _transformedLocation_id, _transformedPrivacy_type);
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
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at FacebookConnector at publishEvent retrieving was successful but result is null");
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
