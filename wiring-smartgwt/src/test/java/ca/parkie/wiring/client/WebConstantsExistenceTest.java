/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.webparam.WebParamTest;
import org.junit.Test;


public class WebConstantsExistenceTest extends WebParamTest
{
    @Test
    public void testConstants()
    {
        testClass(WebConstants.class);
    }
}
