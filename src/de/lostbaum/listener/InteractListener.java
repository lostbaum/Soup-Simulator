package de.lostbaum.listener;

import de.lostbaum.main.Main;
import de.lostbaum.utils.ActionBar;
import de.lostbaum.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class InteractListener implements Listener {

    private Main plugin;

    public static ArrayList<Player> inSoup = new ArrayList<>();
    public static HashMap<UUID, Integer> soupsSuceed = new HashMap<>();

    public static File folder = new File("plugins/Soup/");

    public static File file = new File("plugins/Soup/stats.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);



    public InteractListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        if(e.getItem() != null) {
                if(e.getItem().hasItemMeta()) {
                    if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.SOUPSELECTORNAME)) {
                            inSoup.add(p);
                            ItemStack item = new ItemStack(Material.STONE_SWORD);
                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setDisplayName(Utils.LEAVESOUP);
                            item.setItemMeta(itemMeta);
                            p.getInventory().setItem(0, item);
                            setPoints(p.getUniqueId(), 0);
                            startSoup(p);
                        } else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.LEAVESOUP)) {
                            inSoup.remove(p);
                            ItemStack item = new ItemStack(Material.LEGACY_MUSHROOM_SOUP);
                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setDisplayName(Utils.SOUPSELECTORNAME);
                            item.setItemMeta(itemMeta);
                            p.getInventory().clear();
                            p.getInventory().setItem(0, item);
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
                            p.setLevel(0);
                            ActionBar.sendActionBarMessage(p, String.valueOf(Utils.PREFIX + Utils.ACTIONBARLEAVESOUP));
                        } else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSoup")) {
                            p.getInventory().clear();
                            ItemStack item = new ItemStack(Material.STONE_SWORD);
                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setDisplayName(Utils.LEAVESOUP);
                            item.setItemMeta(itemMeta);
                            p.getInventory().setItem(0, item);
                            addPoint(p.getUniqueId(), 1);
                            startSoup(p);
                        }
                    }
                }

        } else if(e.getItem() == null) {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(inSoup.contains(p))
                    p.damage(2);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if(p.getHealth() == 0) {
                            p.spigot().respawn();

                        }
                    }
                }, 5L);
            }
        }
    }

    public void startSoup(Player p) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(inSoup.contains(p)) {
                    ActionBar.sendActionBarMessage(p, String.valueOf(Utils.ACTIONBARCOUNTERCOLOR + getPoints(p.getUniqueId())));
                }
            }
        }, 0, 20*2);
        int soup = randomInt(1, 8);
        ItemStack item = new ItemStack(Material.LEGACY_MUSHROOM_SOUP);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eSoup");
        item.setItemMeta(itemMeta);
        p.getInventory().setItem(soup, item);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
        p.setLevel(soup + 1);
    }

    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createFiles() {
        if (!folder.exists())
            folder.mkdir();
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        save();
    }

    public static void addPoint(UUID uuid, int points) {
        cfg.set("Points." + uuid.toString(), Integer.valueOf(getPoints(uuid).intValue() + points));
        save();
    }

    public static Integer getPoints(UUID uuid) {
        return Integer.valueOf(cfg.getInt("Points." + uuid.toString()));
    }

    public static void setPoints(UUID uuid, int points) {
        cfg.set("Points." + uuid.toString(), Integer.valueOf(points));
        save();
    }
}
