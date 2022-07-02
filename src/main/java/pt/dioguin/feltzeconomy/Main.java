package pt.dioguin.feltzeconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pt.dioguin.feltzeconomy.commands.Money;
import pt.dioguin.feltzeconomy.listeners.PlayerJoin;
import pt.dioguin.feltzeconomy.utils.Formatter;
import pt.dioguin.feltzeconomy.utils.SQLite;

public class Main extends JavaPlugin {

    private static Formatter formatter;
    private static Main instance;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        instance = this;
        formatter = new Formatter();

        getCommand("money").setExecutor(new Money());
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        SQLite.openConnection();
        SQLite.createTable();
        SQLite.loadPlayers();

    }

    @Override
    public void onDisable() {
        SQLite.savePlayers();
    }

    public static Main getInstance() {
        return instance;
    }

    public static Formatter getFormatter() {
        return formatter;
    }
}
