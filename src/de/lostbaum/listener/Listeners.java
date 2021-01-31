package de.lostbaum.listener;

import de.lostbaum.main.Main;
import de.lostbaum.utils.ActionBar;
import de.lostbaum.utils.ConfigLocationUtil;
import de.lostbaum.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Listeners implements Listener {

    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = (Player) e.getPlayer();
        p.getInventory().clear();
        e.setJoinMessage(Utils.PREFIX + Utils.JOINMESSAGE.replace("%player%", p.getName()));
        ItemStack item = new ItemStack(Material.LEGACY_MUSHROOM_SOUP);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.SOUPSELECTORNAME);
        item.setItemMeta(itemMeta);
        p.getInventory().setItem(0, item);
        p.setHealth(20);
        p.setFoodLevel(20);
        ActionBar.sendActionBarMessage(p, String.valueOf(Utils.PREFIX + Utils.ACTIONBARJOIN.replace("%player%", e.getPlayer().getName())));
        ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin, "Spawn");
        if(locationUtil.loadLocation() != null) {
            p.teleport(locationUtil.loadLocation());
        } else {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Utils.PREFIX + "§cFELHER: DER SPAWN WURDE NICHT GESETZT!"));
            p.sendMessage(String.valueOf(Utils.PREFIX + "§7Der Spawn wurde noch nicht gesetzt. Wende dich an einen Administrator, damit er den Spawn setzt."));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();
        e.setQuitMessage(Utils.PREFIX + Utils.QUITMESSAGE.replace("%player%", p.getName()));
        InteractListener.setPoints(p.getUniqueId(), 0);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHealth(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().clear();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = (Player) e.getPlayer();
        InteractListener.inSoup.remove(p);
        ItemStack item = new ItemStack(Material.LEGACY_MUSHROOM_SOUP);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.SOUPSELECTORNAME);
        item.setItemMeta(itemMeta);
        p.getInventory().setItem(0, item);
        p.setHealth(20);
        p.setFoodLevel(20);
        ActionBar.sendActionBarMessage(p, String.valueOf(Utils.PREFIX + Utils.ACTIONBARDEATH));
        ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin, "Spawn");
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if(locationUtil.loadLocation() != null) {
                    p.teleport(locationUtil.loadLocation());
                } else {
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Utils.PREFIX + "§cFELHER: DER SPAWN WURDE NICHT GESETZT!"));
                    p.sendMessage(String.valueOf(Utils.PREFIX + "§7Der Spawn wurde noch nicht gesetzt. Wende dich an einen Administrator, damit er den Spawn setzt."));
                }
            }
        }, 1);

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL || e.getCause() == EntityDamageEvent.DamageCause.DROWNING)
            e.setCancelled(true);
        }
    }



    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }
}
