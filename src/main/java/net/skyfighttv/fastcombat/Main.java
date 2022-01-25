package net.skyfighttv.fastcombat;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    {
        instance = this;
    }

    @Override
    public void onEnable() {
        new Combat().runTaskTimer(this, 20, 20);

        getServer().getPluginManager().registerEvents(new CombatListeners(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
