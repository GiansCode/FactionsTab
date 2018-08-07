package me.itsmas.tab.factions;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.itsmas.tab.FactionsTab;
import me.itsmas.tab.util.UtilString;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class FactionsManager implements Listener
{
    private final FactionsTab plugin;

    public FactionsManager(FactionsTab plugin)
    {
        this.plugin = plugin;

        this.staffPermission = plugin.getConfig("staff.permission");
        this.staffInjector = UtilString.colour(plugin.getConfig("staff.injector"));

        assignInjectors();
    }

    private void assignInjectors()
    {
        for (Relation relation : Relation.values())
        {
            String injector = plugin.getConfig("relations." + relation.name().toLowerCase());

            injectors.put(relation, UtilString.colour(injector));
        }
    }

    private final String staffPermission;
    private final String staffInjector;

    private final Map<Relation, String> injectors = new HashMap<>();

    public String getRelationInjector(Player target, Player viewer)
    {
        FPlayer fTarget = FPlayers.getInstance().getByPlayer(target);
        FPlayer fViewer = FPlayers.getInstance().getByPlayer(viewer);

        String injector = injectors.get(fViewer.getRelationTo(fTarget));

        if (target.hasPermission(staffPermission))
        {
            injector += staffInjector;
        }

        return injector;
    }
}
