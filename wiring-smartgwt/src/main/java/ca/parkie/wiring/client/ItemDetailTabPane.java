/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.data.*;
import com.smartgwt.client.widgets.DataBoundComponent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class ItemDetailTabPane extends TabSet implements RecordViewer
{
    private final int DETAIL_TAB = 0, EDIT_TAB = 1;

    private DetailViewer defaultViewer;

    private DetailViewer viewer;
    private ItemForm form;

    private ListGrid grid;

    private Tab editTab;
    private Tab detailTab;

    public ItemDetailTabPane(ListGrid grid)
    {
        this.grid = grid;

        defaultViewer = new DetailViewer();
        defaultViewer.setMargin(20);
        // TODO Localize
        defaultViewer.setEmptyMessage("Select an item from the list above.");
        defaultViewer.setFields(new DetailViewerField());

        detailTab = new Tab("Details");
        detailTab.setWidth(75);
        detailTab.setPane(defaultViewer);

        editTab = new Tab("Edit");
        editTab.setWidth(75);
        editTab.setPane(defaultViewer);

        addTab(detailTab);
        addTab(editTab);
    }

    private ItemType getItemTypeFromRecord(Record record)
    {
        return ItemType.getTypeFromString(record.getAttribute("type"));
    }

    private void refreshComponent(DataBoundComponent component, Record record, DSCallback callback)
    {
        DataSource ds = component.getDataSource();

        Criteria criteria = buildFetchCriteria(ds, record);
        component.fetchData(criteria, callback);
    }

    private Criteria buildFetchCriteria(DataSource dataSource, Record record)
    {
        String pkField = dataSource.getPrimaryKeyFieldName();
        String pk = record.getAttribute(pkField);

        return new Criteria(pkField, pk);
    }

    private void createComponents(ItemType type)
    {
        viewer = DetailViewerFactory.createViewer(type);
        detailTab.setPane(viewer);

        ViewUpdateHandler updateHandler = new ViewUpdateHandler();

        // TODO Change viewer class to avoid this potentially unsafe cast
        updateHandler.addRecordViewer(this);
        updateHandler.addRecordViewer((RecordViewer) grid);

        form = FormFactory.createForm(type, updateHandler);

        // Switch the tabs to detail if the save was successful
        DSCallback tabSwitch = new DSCallback()
        {
            public void execute(DSResponse response, Object rawData, DSRequest request)
            {
                selectTab(DETAIL_TAB);
            }
        };
        form.setSaveSuccessCallback(tabSwitch);

        editTab.setPane(form);
    }

    private void fetchRecord(DataSource ds, Record record)
    {
        Criteria criteria = buildFetchCriteria(ds, record);
        viewer.fetchData(criteria, new DSCallback()
        {
            // Update the form with the retrieved data
            public void execute(DSResponse dsResponse, Object raw, DSRequest dsRequest)
            {
                Record[] data = dsResponse.getData();
                if (data.length == 1)
                    form.editRecord(data[0]);
            }
        });
    }

    public void createNewRecord(ItemType type)
    {
        if (!confirmRecordChange())
            return;

        grid.deselectAllRecords();

        createComponents(type);

        selectTab(EDIT_TAB);
    }

    public boolean confirmRecordChange()
    {
        // TODO Implement save logic
        return true;
    }

    public void showSelectedRecord()
    {
        Record record = grid.getSelectedRecord();
        displayRecord(record, true);
    }

    public void displayRecord(Record record)
    {
        displayRecord(record, false);
    }

    public void displayRecord(Record record, boolean fetch)
    {
        if (record == null)
            return;

        if (fetch)
        {
            ItemType type = getItemTypeFromRecord(record);
            createComponents(type);

            DataSource ds = viewer.getDataSource();
            fetchRecord(ds, record);

            // Store the DataSource as an attribute on the record
            record.setAttribute(Constants.ATTR_DATASOURCE, (Object) ds);
        }
        else
        {
            viewer.setData(new Record[] { record });
            form.editRecord(record);
        }
    }

    public void resetViewer()
    {
        grid.deselectAllRecords();

        editTab.setPane(defaultViewer);

        detailTab.setPane(defaultViewer);
        selectTab(DETAIL_TAB);
    }
}
