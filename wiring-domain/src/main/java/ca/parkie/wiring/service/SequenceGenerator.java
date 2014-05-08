/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

public interface SequenceGenerator
{
    void reset();
    String getNext();
    void setStart(String start);
}
