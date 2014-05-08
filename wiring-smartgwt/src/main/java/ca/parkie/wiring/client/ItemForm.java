/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.*;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;


abstract public class ItemForm extends DynamicForm
{
    private boolean addSaveButton = true;

    protected ViewUpdateHandler viewUpdateHandler;

    protected DSCallback saveSuccessCallback;

    protected ItemForm(String dataSourceId)
    {
        setDataSource(DataSourceFactory.get(dataSourceId));
    }

    public void setAddSaveButton(boolean addSaveButton)
    {
        this.addSaveButton = addSaveButton;
    }

    public boolean isAddingSaveButton()
    {
        return addSaveButton;
    }

    public void setViewUpdateHandler(ViewUpdateHandler handler)
    {
        this.viewUpdateHandler = handler;
    }

    public void setSaveSuccessCallback(DSCallback saveSuccessCallback)
    {
        this.saveSuccessCallback = saveSuccessCallback;
    }

    @Override
    public void setFields(FormItem... fields)
    {
        FormItem[] array;

        if (addSaveButton)
        {
            SpacerItem spacer = new SpacerItem();
            // TODO Localize
            ButtonItem save = new ButtonItem("Save");
            save.setStartRow(false);
            save.addClickHandler(new SaveClickHandler());

            array = new FormItem[fields.length + 2];
            System.arraycopy(fields, 0, array, 0, fields.length);
            System.arraycopy(new FormItem[] { spacer, save }, 0, array, fields.length, 2);
        }
        else
            array = fields;

        super.setFields(array);
    }

    protected class SaveClickHandler implements ClickHandler
    {
        public void onClick(final ClickEvent clickEvent)
        {
            if (viewUpdateHandler != null)
            {
                DSRequest request = new DSRequest();
                viewUpdateHandler.attachToRequest(request);

                DSCallback successCallback = saveSuccessCallback == null ? new EmptyCallback() : saveSuccessCallback;
                saveData(successCallback, request);
            }
            else
                saveData();
        }
    }
}

