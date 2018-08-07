package me.itsmas.tab.util;

import org.bukkit.ChatColor;

public final class UtilString
{
    private UtilString() {}

    public static String colour(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
