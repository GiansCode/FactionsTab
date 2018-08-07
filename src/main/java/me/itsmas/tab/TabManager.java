package me.itsmas.tab;

import me.itsmas.tab.util.UtilServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class TabManager extends BukkitRunnable implements Listener
{
    private final FactionsTab plugin;

    TabManager(FactionsTab plugin)
    {
        this.plugin = plugin;

        UtilServer.registerListener(this);

        runTaskTimer(plugin, 20L, 20L);
    }

    private Map<Player, Scoreboard> scoreboards = new HashMap<>();

    private void updateTabName(Player target, Player viewer)
    {
        Scoreboard scoreboard = getScoreboard(viewer);

        Team team = getTeam(scoreboard, target);
        team.addPlayer(target);

        String injector = plugin.getFactionsManager().getRelationInjector(target, viewer);
        team.setPrefix(injector);
    }

    private Team getTeam(Scoreboard scoreboard, Player player)
    {
        Team team = scoreboard.getTeam(player.getName());

        if (team == null)
        {
            team = scoreboard.registerNewTeam(player.getName());
        }

        return team;
    }

    private Scoreboard getScoreboard(Player player)
    {
        return scoreboards.get(player);
    }

    private Scoreboard createScoreboard(Player player)
    {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);

        return scoreboard;
    }

    /* Listeners */
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        scoreboards.put(player, createScoreboard(player));

        update(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        scoreboards.remove(player).getTeams().forEach(Team::unregister);

        removeOthers(player);
    }

    @Override
    public void run()
    {
        Bukkit.getOnlinePlayers().forEach(this::updateAll);
    }

    private void updateAll(Player player)
    {
        Bukkit.getOnlinePlayers().forEach(other -> updateTabName(other, player));
    }

    private void update(Player player)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
        {
            if (player != other)
            {
                updateTabName(other, player);
            }
        });

        updateOthers(player);
    }

    private void updateOthers(Player target)
    {
        Bukkit.getOnlinePlayers().forEach(other ->
        {
            if (other != target)
            {
                updateTabName(target, other);
            }
        });
    }

    private void removeOthers(Player target)
    {
        Bukkit.getOnlinePlayers().forEach(player ->
        {
            if (player != target)
            {
                Team team = getScoreboard(player).getTeam(target.getName());

                team.unregister();
            }
        });
    }
}
