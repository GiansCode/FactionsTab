package me.itsmas.tab.util;

import me.itsmas.tab.FactionsTab;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class UtilServer
{
    private UtilServer() {}

    private static final FactionsTab plugin = JavaPlugin.getPlugin(FactionsTab.class);

    static FactionsTab getPlugin()
    {
        return plugin;
    }

    public static void registerListener(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public static void dispatchCommand(String command)
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
