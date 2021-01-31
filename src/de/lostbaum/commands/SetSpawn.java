package de.lostbaum.commands;

import de.lostbaum.main.Main;
import de.lostbaum.utils.ConfigLocationUtil;
import de.lostbaum.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

    private Main plugin;

    public SetSpawn(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission(String.valueOf(Utils.PERMISSIONSET))) {
                if(args.length == 0) {
                    p.sendMessage(String.valueOf(Utils.PREFIX + Utils.HELPSET));
                } else {
                    if(args[0].equalsIgnoreCase("spawn")) {
                        if(args.length == 1) {
                            new ConfigLocationUtil(plugin, p.getLocation(), "Spawn").saveLocation();
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            p.sendMessage(String.valueOf(Utils.PREFIX + Utils.SPAWNSET));
                        } else

                        p.sendMessage(String.valueOf(Utils.PREFIX + Utils.HELPSET));
                    }

                }
            } else
                p.sendMessage(String.valueOf(Utils.PREFIX + Utils.NOPERMISSION));
        }
        return false;
    }

}
