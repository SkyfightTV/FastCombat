package net.skyfighttv.fastcombat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.skyfighttv.fastcombat.utils.file.FileManager;
import net.skyfighttv.fastcombat.utils.file.Files;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

public final class Combat extends BukkitRunnable {
    public static final HashMap<Player, Long> COMBAT = new HashMap<>();
    public static final String PERMISSION;
    private static final int TIME;
    public static final boolean COMMANDS;
    private static final String TEXT;
    public static final String WRONG_TEXT;

    static {
        YamlConfiguration config = FileManager.getValues().get(Files.Config);
        YamlConfiguration lang = FileManager.getValues().get(Files.Lang);
        PERMISSION = config.getString("permission");
        TIME = config.getInt("time");
        COMMANDS = config.getBoolean("commands");
        TEXT = Objects.requireNonNull(lang.getString("combatText"))
                .replaceAll("&", "§");
        WRONG_TEXT = Objects.requireNonNull(lang.getString("inCombatText"))
                .replaceAll("&", "§");
    }

    @Override
    public void run() {
        new HashMap<>(COMBAT).forEach((player, number) -> {
            long time = (System.currentTimeMillis() / 1000) - number;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(TEXT.replaceAll("%time%", String.valueOf(TIME - time))));

            if (time >= TIME)
                COMBAT.remove(player);
        });
    }
}
