/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

public interface PanelChangeListener
{
    void panelWillHide(Panel panel) throws VetoPanelChangeException;
    void panelDisplayed(Panel panel);
}
