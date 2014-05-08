/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.NetworkInterfaceCardDao;
import ca.parkie.wiring.entity.ManagedPort;
import ca.parkie.wiring.entity.NetworkInterfaceCard;
import ca.parkie.wiring.entity.Port;
import ca.parkie.wiring.service.NetworkInterfaceCardService;
import ca.parkie.wiring.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NetworkInterfaceCardServiceImpl extends AbstractService<NetworkInterfaceCard>
        implements NetworkInterfaceCardService, ApplicationContextAware
{
    private ApplicationContext context;

    @Autowired
    private NetworkInterfaceCardDao dao;

    public NetworkInterfaceCard configureNIC(NetworkInterfaceCard nic, int portCount, int portSpeed, boolean alphaNaming)
    {
        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        SequenceGenerator generator = getSequenceGenerator(alphaNaming);

        List<ManagedPort> portList = new ArrayList<ManagedPort>();

        for (int i = 0; i < portCount; i++)
        {
            ManagedPort port = new ManagedPort();
            port.setName(generator.getNext());
            port.setSpeed(portSpeed);
            port.setDevice(nic);
            portList.add(port);
        }

        nic.setPorts(portList);
        return nic;
    }

    @Transactional(readOnly = true)
    public NetworkInterfaceCard updateNIC(NetworkInterfaceCard detachedEntity, int portCount, int portSpeed, boolean alphaNaming)
    {
        SequenceGenerator generator = null;

        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        // Load the entity from the database
        NetworkInterfaceCard nic = dao.findById(detachedEntity.getId());
        copyEntity(detachedEntity, nic);

        List<ManagedPort> ports = nic.getPorts();
        int existingPortCount = ports.size();

        ManagedPort firstPort = ports.get(0);

        // Change the port speed if necessary
        if (firstPort.getSpeed() != portSpeed)
            for (ManagedPort port : ports)
                port.setSpeed(portSpeed);

        // Change the naming scheme if necessary
        if (portIsAlphaNamed(firstPort) != alphaNaming)
        {
            generator = getSequenceGenerator(alphaNaming);
            for (ManagedPort port : ports)
                port.setName(generator.getNext());
        }

        // Update the port total if necessary
        if (portCount != existingPortCount)
        {
            if (portCount > existingPortCount)
            {
                // Get the last port, figure out how it's named, then append the new ports
                ManagedPort lastPort = ports.get(existingPortCount - 1);

                if (generator == null)
                    generator = getSequenceGenerator(alphaNaming);

                generator.setStart(lastPort.getName());

                // Increment the generator
                generator.getNext();

                for (int i = existingPortCount; i < portCount; i++)
                {
                    ManagedPort port = new ManagedPort();
                    port.setName(generator.getNext());
                    port.setSpeed(portSpeed);
                    port.setDevice(nic);
                    ports.add(port);
                }
            }
            else
            {
                /* List.subList() can't be used by itself here due to EclipseLink bug:
                   https://bugs.eclipse.org/bugs/show_bug.cgi?id=241322
                 */

                ports = new ArrayList<ManagedPort>(ports.subList(0, portCount));
            }
        }

        nic.setPorts(ports);
        return nic;
    }

    private SequenceGenerator getSequenceGenerator(boolean alphaNaming)
    {
        if (alphaNaming)
            return context.getBean("alphaGenerator", SequenceGenerator.class);
        else
            return context.getBean("numericGenerator", SequenceGenerator.class);
    }

    private boolean portIsAlphaNamed(Port port)
    {
        try
        {
            Integer.parseInt(port.getName());
            return false;
        }
        catch (NumberFormatException e)
        {
            return true;
        }
    }

    @Override
    protected GenericDao<NetworkInterfaceCard> getDao()
    {
        return dao;
    }

    @Override
    protected void copyEntity(NetworkInterfaceCard source, NetworkInterfaceCard destination)
    {
        destination.setName(source.getName());
        destination.setVersion(source.getVersion());
    }

    public void setApplicationContext(ApplicationContext context)
    {
        this.context = context;
    }
}
