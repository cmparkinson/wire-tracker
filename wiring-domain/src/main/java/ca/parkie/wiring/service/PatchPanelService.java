/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

import ca.parkie.wiring.entity.PatchPanel;

public interface PatchPanelService extends Service<PatchPanel>
{
    PatchPanel configurePatchPanel(PatchPanel panel, int portCount);

    PatchPanel updatePatchPanel(PatchPanel detachedEntity, int portCount);
}
