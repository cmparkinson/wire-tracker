/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class View extends Window implements EntryPoint
{
    public void onModuleLoad()
    {
        Canvas canvas = new Canvas();
        RootPanel.get("root-tag").add(canvas);
        canvas.setWidth100();
        canvas.setHeight100();
        canvas.addChild(this);
        SC.showConsole();
    }

    public View()
    {
        // TODO Localize
        setWidth100();
        setHeight100();
        setTitle("Wiring Manager");
        setShowMinimizeButton(false);
        setShowCloseButton(false);
        setCanDragReposition(false);
        setCanDragResize(false);
        setShowShadow(false);

        ViewController controller = new ViewController();

        HLayout mainLayout = new HLayout();

        SectionStack navigationStack = new SectionStack();
        navigationStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        navigationStack.setHeight100();
        navigationStack.setWidth(185);
        navigationStack.setShowResizeBar(true);

        SectionStackSection inventorySection = new SectionStackSection("Inventory");
        inventorySection.setExpanded(true);
        inventorySection.setCanCollapse(false);
        NavigationTree deviceNavTree = new NavigationTree(controller,
                new ItemPanel(),
                new EmptyPanel("Deploy / Move"));
        inventorySection.addItem(deviceNavTree);
        navigationStack.addSection(inventorySection);

        SectionStackSection wiringSection = new SectionStackSection("Wiring");
        wiringSection.setExpanded(true);
        wiringSection.setCanCollapse(false);
        NavigationTree wiringNavTree = new NavigationTree(controller,
                new EmptyPanel("Update"),
                new EmptyPanel("View"),
                new EmptyPanel("Trace"));
        wiringSection.addItem(wiringNavTree);
        navigationStack.addSection(wiringSection);

        mainLayout.addMember(navigationStack);

        /*
        List<Panel> panels = new ArrayList<Panel>();
        panels.add(itemPanel);
        panels.add(deployedPanel);

        VLayout navLayout = new VLayout();
        navLayout.setHeight100();
        navLayout.setWidth(185);
        navLayout.setShowResizeBar(true);

        NavigationTree navTree = new NavigationTree(controller, panels);
        navLayout.addMember(navTree);

        mainLayout.addMember(navLayout);
        */

        Canvas mainView = new Canvas();
        mainLayout.addMember(mainView);

        controller.setMainViewArea(mainView);

        addItem(mainLayout);
        //addItem(new MainPanel());
    }

    // TODO Remove
    private class EmptyPanel implements Panel
    {
        private Canvas view;
        private String name;

        private EmptyPanel(String name)
        {
            this.name = name;
            view = new Canvas();
        }

        public Canvas getView()
        {
            return view;
        }

        public String getName()
        {
            return name;
        }

        public void panelWillHide(Panel panel) throws VetoPanelChangeException
        {
        }

        public void panelDisplayed(Panel panel)
        {
        }
    }

    private void showLogin()
    {

    }
}
