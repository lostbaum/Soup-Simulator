package de.lostbaum.main;

import de.lostbaum.commands.SetSpawn;
import de.lostbaum.listener.InteractListener;
import de.lostbaum.listener.InventoryClick;
import de.lostbaum.listener.Listeners;
import de.lostbaum.utils.ConfigLocationUtil;
import de.lostbaum.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§eSoup§7] §7";

    private Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDas Plugin wurde aktiviert!");
        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        InteractListener.createFiles();
        Utils.createFiles();

        getCommand("set").setExecutor(new SetSpawn(this));
        ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin, "Spawn");
        if(locationUtil.loadLocation() != null) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Utils.PREFIX + "§aAlle Spawn-Locations wurden erfolgreich geladen!"));
            Bukkit.getWorld(locationUtil.loadLocation().getWorld().getName()).setDifficulty(Difficulty.PEACEFUL);
        } else
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Utils.PREFIX + "§cFELHER: DER SPAWN WURDE NICHT GESETZT!"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cDas Plugin wurde deaktiviert!");
    }

    public Main getPlugin() {
        return plugin;
    }
}
