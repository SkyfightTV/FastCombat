package net.skyfighttv.fastcombat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class CombatListeners implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void onDamage(EntityDamageByEntityEvent event) {
        if (!event.isCancelled()
                && event.getEntity() instanceof Player
                && event.getDamager() instanceof Player) {
            assert Combat.PERMISSION != null;
            if (!event.getEntity().hasPermission(Combat.PERMISSION))
                Combat.COMBAT.put((Player) event.getEntity(), System.currentTimeMillis()/1000);
            if (!event.getDamager().hasPermission(Combat.PERMISSION))
                Combat.COMBAT.put((Player) event.getDamager(), System.currentTimeMillis()/1000);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        if (Combat.COMBAT.containsKey(event.getPlayer()))
            event.getPlayer().damage(20);
    }

    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent event) {
        if (!Combat.COMMANDS
                && Combat.COMBAT.containsKey(event.getPlayer())) {
            event.getPlayer().sendMessage(Combat.WRONG_TEXT);
            event.setCancelled(true);
        }
    }
}
