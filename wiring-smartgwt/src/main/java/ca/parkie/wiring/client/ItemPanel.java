/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class ItemPanel extends VLayout implements Panel
{
    protected ListGrid grid;
    protected ItemDetailTabPane tabPane;

    public ItemPanel()
    {
        setWidth100();
        setHeight100();

        setAlign(VerticalAlignment.TOP);

        setMembersMargin(5);
        setLayoutMargin(10);

        grid = new RefreshableListGrid();
        grid.setWidth(500);
        grid.setHeight(200);
        grid.setAutoFetchData(true);
        grid.setShowFilterEditor(true);
        grid.setFilterOnKeypress(true);
        grid.setFetchDelay(750);
        grid.setMargin(5);

        DataSource ds = DataSourceFactory.get(ItemDataSource.ID);
        grid.setDataSource(ds);

        ListGridField name = new ListGridField("name", 300);
        ListGridField type = new ListGridField("type");
        type.setCanSort(false);

        grid.setFields(name, type);
        grid.addSelectionChangedHandler(new SelectionChangedHandler()
        {
            public void onSelectionChanged(SelectionEvent selectionEvent)
            {
                tabPane.showSelectedRecord();
            }
        });

        grid.addKeyPressHandler(new KeyPressHandler()
        {
            public void onKeyPress(KeyPressEvent keyPressEvent)
            {
                String key = keyPressEvent.getKeyName();
                if (key.equals(Constants.KEY_DELETE) && grid.getSelectedRecord() != null)
                    confirmDelete();
            }
        });

        SortSpecifier nameSort = new SortSpecifier("name", SortDirection.ASCENDING);
        grid.setSort(new SortSpecifier[] { nameSort });

        SectionStack sectionStack = new SectionStack();
        sectionStack.setWidth100();
        sectionStack.setHeight100();
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);

        SectionStackSection gridSection = new SectionStackSection("Available Items");
        gridSection.setCanCollapse(false);
        gridSection.setExpanded(true);

        gridSection.addItem(grid);

        sectionStack.addSection(gridSection);

        tabPane = new ItemDetailTabPane(grid);
        SectionStackSection detailSection = new SectionStackSection("Details");
        detailSection.setCanCollapse(false);
        detailSection.setExpanded(true);

        detailSection.addItem(tabPane);
        sectionStack.addSection(detailSection);

        // TODO Localize
        final SelectItem typeList = new SelectItem();
        ItemType[] typeArray = ItemType.values();
        String[] sArray = new String[typeArray.length];
        for (int i = 0; i < typeArray.length; i++)
            sArray[i] = typeArray[i].toString();
        typeList.setValueMap(sArray);
        typeList.setShowTitle(false);

        DynamicForm newItemForm = new DynamicForm();

        // TODO Localize
        ButtonItem createNewButton = new ButtonItem("Create");
        createNewButton.setStartRow(false);
        createNewButton.setEndRow(true);

        createNewButton.addClickHandler(new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent)
            {
                String value = typeList.getValueAsString();

                if (value == null || value.length() == 0)
                    return;

                ItemType type = ItemType.getTypeFromString(value);
                tabPane.createNewRecord(type);
            }
        });

        newItemForm.setFields(typeList, createNewButton);

        addMember(newItemForm);
        addMember(sectionStack);
    }

    private void confirmDelete()
    {
        final Record record = grid.getSelectedRecord();
        if (record == null)
            return;

        // TODO Localize
        SC.ask("Confirm Delete", "Are you sure you want to delete the selected item?", new BooleanCallback()
        {
            public void execute(Boolean confirm)
            {
                if (confirm)
                {
                    ViewUpdateHandler handler = new ViewUpdateHandler();
                    handler.addRecordViewer(tabPane);

                    DSRequest dsRequest = new DSRequest();
                    handler.attachToRequest(dsRequest);

                    // Attach the record to the remove request to check for updates before removal
                    Record copy = grid.getDataSource().copyRecord(record);
                    dsRequest.setData(copy);

                    grid.getDataSource().removeData(record, new EmptyCallback(), dsRequest);
                }
            }
        });
    }

    public Canvas getView()
    {
        return this;
    }

    public String getName()
    {
        // TODO Localize
        return "Available Items";
    }

    public void panelWillHide(Panel panel) throws VetoPanelChangeException
    {
    }

    public void panelDisplayed(Panel panel)
    {
    }
}
