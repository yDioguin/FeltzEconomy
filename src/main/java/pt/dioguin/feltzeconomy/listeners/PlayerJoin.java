package pt.dioguin.feltzeconomy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pt.dioguin.feltzeconomy.objects.EcoPlayer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();

        if (!EcoPlayer.getPlayers().containsKey(player.getName())){
            new EcoPlayer(player.getName(), 1000000000);
        }

    }

}
