/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.widgets.Canvas;

public interface Panel extends PanelChangeListener
{
    Canvas getView();
    String getName();
}
