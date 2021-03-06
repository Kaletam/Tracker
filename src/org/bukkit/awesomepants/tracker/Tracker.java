package org.bukkit.awesomepants.tracker;

// Java imports
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

// org.bukkit imports
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Server;

// Other imports
import com.nijikokun.bukkit.Permissions.Permissions;

// TODO: Add more quotes.
// TODO: Add support for text file and/or SQL DB storage of quotes.
// TODO: Distinguish between a quote and its author?
// TODO: Implement console command execution.

/**
 * Tracker for Bukkit
 *
 * @author Kaletam
 */
public class Tracker extends JavaPlugin
{
    private final TrackerPlayerListener playerListener = new TrackerPlayerListener(this);
    private final TrackerBlockListener blockListener = new TrackerBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>(); // Not a clue what this is generated for. *shrug*
    public static final Logger log = Logger.getLogger("Minecraft"); // Get the Minecraft logger for, er, logging purposes.
    private Permissions permissions = null;
    private boolean permissionsEnabled = false;

    public Tracker(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
    {
	super(pluginLoader, instance, desc, folder, plugin, cLoader);

	// Plugin generator tells us this. Dunno, but we'll keep it for reference.
	// NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }

    @Override
    public void onEnable()
    {
	// Register our events
	PluginManager pm = getServer().getPluginManager();

	// Borrowed shamelessly from VoidMage. Only temporary, to remind me of how these work. We probably won't use them for this plugin.
	// pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
	// pm.registerEvent(Event.Type.BLOCK_PLACED, this.blockListener, Event.Priority.Normal, this);
	// pm.registerEvent(Event.Type.PLUGIN_DISABLE, this.playerListener, Event.Priority.Normal, this);
	// pm.registerEvent(Event.Type.BLOCK_DAMAGED, this.blockListener, Event.Priority.Normal, this);

	// HotSwap used as a template for this one. Again, we probably won't use it here.
	// pm.registerEvent(Event.Type.PLAYER_COMMAND, this.playerListener, Priority.Normal, this);

	// Load Permissions settings. Currently unused, but included for future implementation.
	this.setupPermissions();

	// TODO: Consider turning pdfFile into a field of the class, especially if we reference it anywhere else.
	PluginDescriptionFile pdfFile = this.getDescription();
	// Instead of just outputting to the console, let's log it.
	//System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	log.log(Level.INFO, String.format("[%s] version [%s] enabled.", pdfFile.getName(), pdfFile.getVersion()));
    }

    @Override
    public void onDisable()
    {
	// I'll take the shell generator's word for this, for now.
	// NOTE: All registered events are automatically unregistered when a plugin is disabled

	// Instead of just outputting to the console, let's log it.
	//System.out.println("Goodbye world!");
	PluginDescriptionFile pdfFile = this.getDescription();
	log.log(Level.INFO, String.format("[%s] version [%s] signing off!", pdfFile.getName(), pdfFile.getVersion()));
    }

    // Dunno what this does yet.
    public boolean isDebugging(final Player player)
    {
	if (debugees.containsKey(player))
	{
	    return debugees.get(player);
	}
	else
	{
	    return false;
	}
    }

    // Dunno what this does yet.
    public void setDebugging(final Player player, final boolean value)
    {
	debugees.put(player, value);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
	// This *should* be superfluous, since we're only registering /tracker, but we'll keep it for now.
	String commandName = command.getName().toLowerCase();

	if ((sender instanceof Player)) // If executed by the player.
	{
	    Player p = (Player) sender;

	    // This method for getting subcommands stolen from HotSwap.
	    SubCommands sub = null;

	    try
	    {
		sub = SubCommands.valueOf(args[0].toUpperCase());
	    }
	    catch (Exception ex) // Don't actually do anything, just return false (triggering display of usage as per plugin.yml).
	    {
		return false;
	    }

	    switch (sub)
	    {
		case QUOTE:

		    return true;
		case SEND:

		    return true;
		case BROADCAST:

		    return true;
		default:
		    return false;
	    }
	}
	else // TODO: Figure out if !(sender instanceof Player) implies a console executed command.
	{
	    // Don't do anything right now.
	    //log.log(Level.INFO, "We're in onCommand, !(sender instanceof Player).");
	    //System.out.println("Console test!");
	    return false; // Right now, we don't actually succeed or fail, but for the console, let's output usage for testing purposes.
	}
    }

    // Stolen from VoidMage.
    public void setupPermissions()
    {
	Plugin p = this.getServer().getPluginManager().getPlugin("Permissions");

	if (this.permissions == null)
	{
	    if (p != null)
	    {
		this.permissions = (Permissions) p;
		this.permissionsEnabled = true;
		//log.log(Level.INFO, String.format("[%s] Permission system enabled.", this.getDescription().getName()));
	    }
	    else
	    {
		//log.log(Level.INFO, String.format("[%s] Permission system not enabled. Using default.", this.getDescription().getName()));
	    }
	}
    }

    // This method for getting subcommands stolen from HotSwap.
    private enum SubCommands
    {
	QUOTE, SEND, BROADCAST
    }
}
