/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import java.util.Map;

public interface Validator<T>
{
    boolean validateObject(T entity, Map<String, String> errorMap);
    boolean validateObject(T entity);
    String getMessage();
}
