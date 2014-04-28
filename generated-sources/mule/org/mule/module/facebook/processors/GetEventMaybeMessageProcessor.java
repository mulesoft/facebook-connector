
package org.mule.module.facebook.processors;

import java.util.List;
import javax.annotation.Generated;
import com.restfb.types.User;
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
import org.mule.common.metadata.DefaultListMetaDataModel;
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
 * GetEventMaybeMessageProcessor invokes the {@link org.mule.module.facebook.FacebookConnector#getEventMaybe(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)} method in {@link FacebookConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-28T02:56:18-05:00", comments = "Build master.1926.b0106b2")
public class GetEventMaybeMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object eventId;
    protected String _eventIdType;
    protected Object since;
    protected String _sinceType;
    protected Object until;
    protected String _untilType;
    protected Object limit;
    protected String _limitType;
    protected Object offset;
    protected String _offsetType;

    public GetEventMaybeMessageProcessor(String operationName) {
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
     * Sets limit
     * 
     * @param value Value to set
     */
    public void setLimit(Object value) {
        this.limit = value;
    }

    /**
     * Sets eventId
     * 
     * @param value Value to set
     */
    public void setEventId(Object value) {
        this.eventId = value;
    }

    /**
     * Sets since
     * 
     * @param value Value to set
     */
    public void setSince(Object value) {
        this.since = value;
    }

    /**
     * Sets offset
     * 
     * @param value Value to set
     */
    public void setOffset(Object value) {
        this.offset = value;
    }

    /**
     * Sets until
     * 
     * @param value Value to set
     */
    public void setUntil(Object value) {
        this.until = value;
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
            final String _transformedEventId = ((String) evaluateAndTransform(getMuleContext(), event, GetEventMaybeMessageProcessor.class.getDeclaredField("_eventIdType").getGenericType(), null, eventId));
            final String _transformedSince = ((String) evaluateAndTransform(getMuleContext(), event, GetEventMaybeMessageProcessor.class.getDeclaredField("_sinceType").getGenericType(), null, since));
            final String _transformedUntil = ((String) evaluateAndTransform(getMuleContext(), event, GetEventMaybeMessageProcessor.class.getDeclaredField("_untilType").getGenericType(), null, until));
            final String _transformedLimit = ((String) evaluateAndTransform(getMuleContext(), event, GetEventMaybeMessageProcessor.class.getDeclaredField("_limitType").getGenericType(), null, limit));
            final String _transformedOffset = ((String) evaluateAndTransform(getMuleContext(), event, GetEventMaybeMessageProcessor.class.getDeclaredField("_offsetType").getGenericType(), null, offset));
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
                    return ((FacebookConnector) object).getEventMaybe(_transformedEventId, _transformedSince, _transformedUntil, _transformedLimit, _transformedOffset);
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
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(User.class))));
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
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at FacebookConnector at getEventMaybe retrieving was successful but result is null");
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
