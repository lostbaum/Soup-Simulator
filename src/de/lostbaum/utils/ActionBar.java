package de.lostbaum.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBar {

    public static void sendActionBarMessage(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(message));
    }

}
