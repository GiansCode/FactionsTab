package me.itsmas.tab;

import me.itsmas.tab.factions.FactionsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsTab extends JavaPlugin
{
    private FactionsManager factionsManager; FactionsManager getFactionsManager() { return factionsManager; }
    private TabManager tabManager; TabManager getTabManager() { return  tabManager; }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        factionsManager = new FactionsManager(this);
        tabManager = new TabManager(this);
    }

    public <T> T getConfig(String path)
    {
        return getConfig(path, null);
    }

    @SuppressWarnings("unchecked")
    private <T> T getConfig(String path, Object defaultValue)
    {
        return (T) getConfig().get(path, defaultValue);
    }
}
