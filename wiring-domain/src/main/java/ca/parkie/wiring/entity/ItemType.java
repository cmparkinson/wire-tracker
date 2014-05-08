/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

public enum ItemType
{
    Item("Item"),
    NIC("NIC"),
    PatchPanel("Patch Panel"),
    Server("Server"),
    Switch("Switch"),
    SwitchChassis("Switch Chassis"), // TODO Remove
    SwitchModule("Switch Module"),
    Rack("Rack"),
    WallJack("Wall Jack");

    private String type;

    ItemType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return type;
    }

    public static ItemType getTypeFromString(String type)
    {
        for (ItemType t : values())
            if (t.toString().equals(type))
                return t;

        return null;
    }
}
