/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.PatchPanelDao;
import ca.parkie.wiring.entity.PatchPanel;
import ca.parkie.wiring.entity.Port;
import ca.parkie.wiring.service.PatchPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatchPanelServiceImpl extends AbstractService<PatchPanel> implements PatchPanelService
{
    @Autowired
    private PatchPanelDao dao;

    public PatchPanel configurePatchPanel(PatchPanel panel, int portCount)
    {
        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        List<Port> ports = new ArrayList<Port>(portCount);
        for (int i = 0; i < portCount; i++)
        {
            Port port = new Port();
            port.setName(String.valueOf(i));
            port.setDevice(panel);
            ports.add(port);
        }

        panel.setPorts(ports);
        return panel;
    }

    public PatchPanel updatePatchPanel(PatchPanel detachedEntity, int portCount)
    {
        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        PatchPanel panel = dao.findById(detachedEntity.getId());
        copyEntity(detachedEntity, panel);

        List<Port> ports = new ArrayList<Port>(panel.getPorts());

        int existingPortCount = ports.size();

        if (existingPortCount < portCount)
        {
            for (int i = existingPortCount; i < portCount; i++)
            {
                Port port = new Port();
                port.setName(String.valueOf(i));
                port.setDevice(panel);
                ports.add(port);
            }
        }
        else if (existingPortCount > portCount)
        {
            /**
             *  Necessary due a bug in EclipseLink that requires Collection containers to implement Cloneable.
             *  (List.subList() returns an inner class that does not implement Cloneable)
             */

            ports = new ArrayList<Port>(ports.subList(0, portCount));
        }
        
        panel.setPorts(ports);
        return panel;
    }

    @Override
    protected void copyEntity(PatchPanel source, PatchPanel destination)
    {
        destination.setName(source.getName());
        destination.setVersion(source.getVersion());
        destination.setSize(source.getSize());
    }

    @Override
    protected GenericDao<PatchPanel> getDao()
    {
        return dao;
    }
}
