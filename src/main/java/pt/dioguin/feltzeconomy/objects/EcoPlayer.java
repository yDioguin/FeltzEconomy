package pt.dioguin.feltzeconomy.objects;

import pt.dioguin.feltzeconomy.Main;

import java.util.HashMap;

public class EcoPlayer {

    private static final HashMap<String, EcoPlayer> players = new HashMap<>();

    private double money;

    public EcoPlayer(String player, double money) {
        this.money = money;
        players.put(player, this);
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getFormattedMoney() {
        return Main.getFormatter().formatNumber(this.money);
    }

    public static HashMap<String, EcoPlayer> getPlayers() {
        return players;
    }
}
