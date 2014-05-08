/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.*;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.JSOHelper;

abstract public class AbstractRestDataSource extends RestDataSource
{
    // URL format: /prefix/type/url
    static public String URL_PREFIX = "/wiring/rest";
    static private DSDataFormat FORMAT = DSDataFormat.JSON;

    protected AbstractRestDataSource(String url)
    {
        setDataFormat(FORMAT);

        OperationBinding fetchBinding = new OperationBinding();
        fetchBinding.setDataProtocol(DSProtocol.POSTMESSAGE);
        fetchBinding.setOperationType(DSOperationType.FETCH);

        OperationBinding removeBinding = new OperationBinding();
        removeBinding.setDataProtocol(DSProtocol.POSTMESSAGE);
        removeBinding.setOperationType(DSOperationType.REMOVE);

        OperationBinding addBinding = new OperationBinding();
        addBinding.setDataProtocol(DSProtocol.POSTMESSAGE);
        addBinding.setOperationType(DSOperationType.ADD);

        OperationBinding updateBinding = new OperationBinding();
        updateBinding.setDataProtocol(DSProtocol.POSTMESSAGE);
        updateBinding.setOperationType(DSOperationType.UPDATE);

        setFetchDataURL(URL_PREFIX + "/fetch/" + url);
        setUpdateDataURL(URL_PREFIX + "/update/" + url);
        setRemoveDataURL(URL_PREFIX + "/remove/" + url);
        setAddDataURL(URL_PREFIX + "/add/" + url);

        setOperationBindings(fetchBinding, addBinding, removeBinding, updateBinding);
    }

    @Override
    protected void transformResponse(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
        super.transformResponse(dsResponse, dsRequest, data);

        switch (dsRequest.getOperationType())
        {
            case ADD:
                processAddTransform(dsResponse, dsRequest, data);
                break;
            case UPDATE:
                processUpdateTransform(dsResponse, dsRequest, data);
                break;
            case REMOVE:
                processRemoveTransform(dsResponse, dsRequest, data);
                break;
            case FETCH:
                processFetchTransform(dsResponse, dsRequest, data);
                break;
            default:
        }
    }

    protected void processAddTransform(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
        processCommitTransform(dsResponse, dsRequest, data);
    }

    protected void processUpdateTransform(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
        processCommitTransform(dsResponse, dsRequest, data);
    }

    protected void processRemoveTransform(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
        if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS)
        {
            ViewUpdateHandler handler = ViewUpdateHandler.retrieveFromRequest(dsRequest);
            handler.resetViews();
        }
    }

    protected void processFetchTransform(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
    }

    private void processCommitTransform(DSResponse dsResponse, DSRequest dsRequest, Object data)
    {
        ServerResponse response = new ServerResponse((JavaScriptObject) data);
        Record record;

        if (dsResponse.getStatus() != RPCResponse.STATUS_SUCCESS)
        {
            // Check for a record refresh
            record = response.getRefreshedRecord();
            if (record != null)
            {
                DSResponse cacheUpdateResponse = new DSResponse();
                cacheUpdateResponse.setData(new Record[]{record});
                updateCaches(cacheUpdateResponse);
            }
            else if (response.recordNotFound())
            {
                ViewUpdateHandler handler = ViewUpdateHandler.retrieveFromRequest(dsRequest);
                if (handler != null)
                    handler.resetViews();
            }
        }
        else
        {
            record = response.getData();
        }

        if (record == null)
            return;

        ViewUpdateHandler handler = ViewUpdateHandler.retrieveFromRequest(dsRequest);
        if (handler != null)
            handler.updateViews(record);
    }
}
