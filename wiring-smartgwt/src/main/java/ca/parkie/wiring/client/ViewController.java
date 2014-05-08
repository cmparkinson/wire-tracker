/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.widgets.Canvas;

import java.util.ArrayList;
import java.util.List;

public class ViewController
{
    private List<PanelChangeListener> listeners = new ArrayList<PanelChangeListener>();
    private Panel currentlyDisplayed;

    private Canvas mainViewArea;

    public boolean showPanel(Panel panel)
    {
        if (currentlyDisplayed != null)
        {
            try
            {
                for (PanelChangeListener l : listeners)
                    l.panelWillHide(currentlyDisplayed);
            }
            catch (VetoPanelChangeException e)
            {
                return false;
            }

            removePanelChangeListener(currentlyDisplayed);

            removeCanvas(currentlyDisplayed.getView());
        }

        displayCanvas(panel.getView());
        currentlyDisplayed = panel;

        registerPanelChangeListener(panel);

        for (PanelChangeListener l : listeners)
            l.panelDisplayed(panel);

        return true;
    }

    protected void removeCanvas(Canvas canvas)
    {
        mainViewArea.removeChild(canvas);
    }

    protected void displayCanvas(Canvas canvas)
    {
        mainViewArea.addChild(canvas);
    }

    public void registerPanelChangeListener(PanelChangeListener listener)
    {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void removePanelChangeListener(PanelChangeListener listener)
    {
        listeners.remove(listener);
    }

    public Canvas getMainViewArea()
    {
        return mainViewArea;
    }

    public void setMainViewArea(Canvas mainViewArea)
    {
        this.mainViewArea = mainViewArea;
    }
}
