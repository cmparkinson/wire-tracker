/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.SwitchDao;
import ca.parkie.wiring.entity.ManagedPort;
import ca.parkie.wiring.entity.Switch;
import ca.parkie.wiring.entity.SwitchModule;
import ca.parkie.wiring.service.NamingConvention;
import ca.parkie.wiring.service.SwitchPortName;
import ca.parkie.wiring.service.SwitchPortNameGenerator;
import ca.parkie.wiring.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwitchServiceImpl extends AbstractService<Switch> implements SwitchService, ApplicationContextAware
{
    @Autowired
    private SwitchDao dao;

    private ApplicationContext context;

    @Override
    protected void copyEntity(Switch source, Switch destination)
    {
        destination.setName(source.getName());
        destination.setSize(source.getSize());
        destination.setVersion(source.getVersion());
    }

    @Override
    protected GenericDao<Switch> getDao()
    {
        return dao;
    }

    public Switch configureSwitch(Switch sw, int portCount, int portSpeed, int uplinkCount, int uplinkSpeed, NamingConvention naming)
    {
        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        SwitchPortNameGenerator generator = getNameGenerator(naming);

        generator.setPortSpeed(portSpeed);
        generator.setModuleNumber(0);

        SwitchModule accessModule = new SwitchModule();
        accessModule.setParentSwitch(sw);

        List<ManagedPort> ports = new ArrayList<ManagedPort>();

        // Generate the normal ports
        for (int i = 0; i < portCount; i++)
        {
            ManagedPort port = new ManagedPort();
            SwitchPortName portName = generator.getNext();
            port.setSpeed(portSpeed);
            port.setAbbrev(portName.getAbbrev());
            port.setName(portName.getName());
            port.setDevice(accessModule);
            ports.add(port);
        }

        accessModule.setPorts(ports);

        // Generate the uplink ports
        generator.setPortSpeed(uplinkSpeed);

        SwitchModule uplinkModule = new SwitchModule();
        uplinkModule.setParentSwitch(sw);

        ports = new ArrayList<ManagedPort>();

        for (int i = 0; i < uplinkCount; i++)
        {
            ManagedPort port = new ManagedPort();
            SwitchPortName portName = generator.getNext();
            port.setSpeed(portSpeed);
            port.setAbbrev(portName.getAbbrev());
            port.setName(portName.getName());
            port.setDevice(accessModule);
            ports.add(port);
        }

        uplinkModule.setPorts(ports);
        uplinkModule.setUplink(true);

        List<SwitchModule> modules = new ArrayList<SwitchModule>();
        modules.add(accessModule);
        modules.add(uplinkModule);

        sw.setModules(modules);
        return sw;
    }

    @Transactional(readOnly = true)
    public Switch updateSwitch(Switch detachedSwitch, int portCount, int portSpeed, int uplinkCount, int uplinkSpeed, NamingConvention naming)
    {
        if (portCount <= 0)
            throw new IllegalArgumentException("Port count must be greater than zero.");

        Switch sw = dao.findById(detachedSwitch.getId());
        copyEntity(detachedSwitch, sw);

        SwitchPortNameGenerator generator = getNameGenerator(naming);

        // Update the access ports
        updatePorts(sw.getModules().get(0), portCount, portSpeed, generator);

        // Update the uplink ports
        updatePorts(sw.getModules().get(1), uplinkCount, uplinkSpeed, generator);

        return sw;
    }

    public void setApplicationContext(ApplicationContext context)
    {
        this.context = context;
    }

    private void updatePorts(SwitchModule module, int portCount, int portSpeed, SwitchPortNameGenerator generator)
    {
        List<ManagedPort> ports = module.getPorts();
        int existingPortCount = ports.size();

        // Add or remove ports
        addRemovePorts(module, portCount, portSpeed, generator);

        // Change the port speed if necessary
        ManagedPort firstPort = ports.get(0);
        if (firstPort.getSpeed() != portSpeed)
            for (ManagedPort port : ports)
                port.setSpeed(portSpeed);

        generator.setPortSpeed(portSpeed);

        // Change the naming scheme
        for (ManagedPort port : ports)
        {
            SwitchPortName name = generator.getNext();
            port.setAbbrev(name.getAbbrev());
            port.setName(name.getName());
        }
    }

    private void addRemovePorts(SwitchModule module, int portCount, int portSpeed, SwitchPortNameGenerator generator)
    {
        generator.setPortSpeed(portSpeed);

        List<ManagedPort> ports = module.getPorts();
        int currentPortCount = ports.size();
        if (currentPortCount > portCount)
        {
            generator.setStartPort(currentPortCount);
            for (int i = currentPortCount; i < portCount; i++)
            {
                ManagedPort port = new ManagedPort();
                SwitchPortName name = generator.getNext();
                port.setSpeed(portSpeed);
                port.setAbbrev(name.getAbbrev());
                port.setName(name.getName());
                port.setDevice(module);
                ports.add(port);
            }
        }
        else if (currentPortCount < portCount)
        {
            /* List.subList() can't be used by itself here due to EclipseLink bug:
              https://bugs.eclipse.org/bugs/show_bug.cgi?id=241322
            */

            ports = new ArrayList<ManagedPort>(ports.subList(0, portCount));
            module.setPorts(ports);
        }
    }

    private SwitchPortNameGenerator getNameGenerator(NamingConvention naming)
    {
        SwitchPortNameGenerator generator;

        switch (naming)
        {
            case Cisco:
            default:
                generator = (SwitchPortNameGenerator) context.getBean("ciscoPortGenerator");
        }

        return generator;
    }
}
