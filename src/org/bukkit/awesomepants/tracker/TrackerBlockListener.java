package org.bukkit.awesomepants.tracker;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Tracker block listener
 * @author Kaletam
 * 
 */
public class TrackerBlockListener extends BlockListener
{
    private final Tracker plugin;

    public TrackerBlockListener(final Tracker plugin)
    {
	this.plugin = plugin;
    }
}
