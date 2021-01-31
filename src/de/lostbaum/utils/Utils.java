package de.lostbaum.utils;

import com.sun.org.apache.bcel.internal.generic.NOP;
import de.lostbaum.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Utils {

    private Main plugin;

    public static ArrayList<Player> inSoup = new ArrayList<>();
    public static HashMap<UUID, Integer> soupsSuceed = new HashMap<>();

    public static File folder = new File("plugins/Soup/");

    public static File file = new File("plugins/Soup/settings.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static String JOINMESSAGE;
    public static String QUITMESSAGE;
    public static String PREFIX;
    public static String SOUPSELECTORNAME;
    public static String LEAVESOUP;
    public static String ACTIONBARJOIN;
    public static String ACTIONBARLEAVESOUP;
    public static String ACTIONBARDEATH;
    public static String PERMISSIONSET;
    public static String NOPERMISSION;
    public static String SPAWNSET;
    public static String HELPSET;
    public static String ACTIONBARCOUNTERCOLOR;

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOINMESSAGE = ChatColor.translateAlternateColorCodes('&', cfg.getString("Messages.JoinMessage"));
        QUITMESSAGE = ChatColor.translateAlternateColorCodes('&', cfg.getString("Messages.QuitMessage"));
        PREFIX = ChatColor.translateAlternateColorCodes('&', cfg.getString("Messages.Prefix"));
        SOUPSELECTORNAME = ChatColor.translateAlternateColorCodes('&', cfg.getString("Item.SoupSelector"));
        LEAVESOUP = ChatColor.translateAlternateColorCodes('&', cfg.getString("Item.LeaveSoup"));
        ACTIONBARJOIN = ChatColor.translateAlternateColorCodes('&', cfg.getString("Actionbar.Join"));
        ACTIONBARLEAVESOUP = ChatColor.translateAlternateColorCodes('&', cfg.getString("Actionbar.LeaveSoup"));
        ACTIONBARDEATH = ChatColor.translateAlternateColorCodes('&', cfg.getString("Actionbar.Death"));
        PERMISSIONSET = ChatColor.translateAlternateColorCodes('&', cfg.getString("Permission.Set"));
        NOPERMISSION = ChatColor.translateAlternateColorCodes('&', cfg.getString("Permission.NoPermission"));
        SPAWNSET = ChatColor.translateAlternateColorCodes('&', cfg.getString("Set.Spawn"));
        HELPSET = ChatColor.translateAlternateColorCodes('&', cfg.getString("Set.Help"));
        ACTIONBARCOUNTERCOLOR = ChatColor.translateAlternateColorCodes('&', cfg.getString("Actionbar.ColorForSoupCounter"));
    }

    public static void createFiles() {
        if (!folder.exists())
            folder.mkdir();
        if (!file.exists())
            try {
                file.createNewFile();
                cfg.set("Messages.JoinMessage", "&e%player% &7hat den Server betreten!");
                cfg.set("Messages.QuitMessage", "&e%player% &7hat den Server verlassen!");
                cfg.set("Messages.Prefix", "&7[&eSoup&7] &7");
                cfg.set("Item.SoupSelector", "&eSoup Simulator");
                cfg.set("Item.LeaveSoup", "&eSoup Simulator verlassen");
                cfg.set("Actionbar.Join", "&7Herzlich willkommen, %player%");
                cfg.set("Actionbar.LeaveSoup", "&7Du hast das Soup verlassen");
                cfg.set("Actionbar.Death", "&7Du bist gestorben");
                cfg.set("Permission.Set", "cmd.set");
                cfg.set("Permission.NoPermission", "&cDazu hast du keine Rechte!");
                cfg.set("Set.Spawn", "&7Du hast den Spawn gesetzt");
                cfg.set("Set.Help", "&7Bitte benutze &e/set <Spawn>");
                cfg.set("Actionbar.ColorForSoupCounter", "&e&l");
            } catch (IOException e) {
                e.printStackTrace();
            }
        save();
    }

}
