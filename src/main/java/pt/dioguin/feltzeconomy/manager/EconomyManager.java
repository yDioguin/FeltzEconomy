package pt.dioguin.feltzeconomy.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.dioguin.feltzeconomy.Main;
import pt.dioguin.feltzeconomy.events.PlayerMoneyChangeEvent;
import pt.dioguin.feltzeconomy.objects.EcoPlayer;

import java.util.*;

public class EconomyManager {

    public static void pay(String sender, String receiver, String quantity) {

        Player player = Bukkit.getPlayer(sender);
        Player target = Bukkit.getPlayer(receiver);
        double amount;

        if (quantity.equalsIgnoreCase("nan") || quantity.equalsIgnoreCase("null")){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        try {
            amount = Double.parseDouble(quantity);
        }catch (Exception e){
            try {
                amount = Main.getFormatter().parseString(quantity);
            } catch (Exception ex) {
                player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
                return;
            }
        }

        if (sender.equalsIgnoreCase(receiver)){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Same player").replace("&", "§"));
            return;
        }

        if (amount < 1){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        if (!EcoPlayer.getPlayers().containsKey(receiver) || target == null || !target.isOnline()){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Player doesn't online").replace("&", "§"));
            return;
        }

        EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(sender);
        EcoPlayer targetPlayer = EcoPlayer.getPlayers().get(receiver);

        if (amount > ecoPlayer.getMoney()){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Insufficient founds").replace("&", "§"));
            return;
        }

        PlayerMoneyChangeEvent event = new PlayerMoneyChangeEvent(amount, sender, receiver);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return;
        }

        ecoPlayer.setMoney(ecoPlayer.getMoney() - amount);
        targetPlayer.setMoney(targetPlayer.getMoney() + amount);
        player.sendMessage(Main.getInstance().getConfig().getString("Messages.Send money").replace("&", "§").replace("{formattedMoney}", Main.getFormatter().formatNumber(amount)).replace("{received}", target.getName()));
        target.sendMessage(Main.getInstance().getConfig().getString("Messages.Receive money").replace("&", "§").replace("{formattedMoney}", Main.getFormatter().formatNumber(amount)).replace("{sender}", player.getName()));

    }

    public static void add(Player sender, String receiver, String quantity){

        Player player = Bukkit.getPlayer(receiver);
        double amount;

        if (quantity.equalsIgnoreCase("nan") || quantity.equalsIgnoreCase("null")){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        try {
            amount = Double.parseDouble(quantity);
        }catch (Exception e){
            try {
                amount = Main.getFormatter().parseString(quantity);
            } catch (Exception ex) {
                player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
                return;
            }
        }

        if (amount < 1){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        if (!EcoPlayer.getPlayers().containsKey(receiver)){
            player.sendMessage("§cJogador inexistente.");
            return;
        }

        PlayerMoneyChangeEvent event = new PlayerMoneyChangeEvent(amount, sender.getName(), receiver);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return;
        }

        EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(receiver);
        ecoPlayer.setMoney(ecoPlayer.getMoney() + amount);
        sender.sendMessage("§aVocê adicionou §f" + Main.getFormatter().formatNumber(amount) + " §acoins ao jogador §f" + player.getName());

    }

    public static void remove(Player sender, String receiver, String quantity){

        Player player = Bukkit.getPlayer(receiver);
        double amount;

        if (quantity.equalsIgnoreCase("nan") || quantity.equalsIgnoreCase("null")){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        try {
            amount = Double.parseDouble(quantity);
        }catch (Exception e){
            try {
                amount = Main.getFormatter().parseString(quantity);
            } catch (Exception ex) {
                player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
                return;
            }
        }

        if (amount < 1){
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        if (!EcoPlayer.getPlayers().containsKey(receiver)){
            player.sendMessage("§cJogador inexistente.");
            return;
        }

        PlayerMoneyChangeEvent event = new PlayerMoneyChangeEvent(amount, sender.getName(), receiver);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return;
        }

        EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(receiver);
        ecoPlayer.setMoney(ecoPlayer.getMoney() - amount);
        sender.sendMessage("§aVocê removeu §f" + Main.getFormatter().formatNumber(amount) + " §acoins ao jogador §f" + player.getName());

    }

    public static void set(Player sender, String receiver, String quantity) {

        Player player = Bukkit.getPlayer(receiver);
        double amount;

        if (quantity.equalsIgnoreCase("nan") || quantity.equalsIgnoreCase("null")) {
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        try {
            amount = Double.parseDouble(quantity);
        } catch (Exception e) {
            try {
                amount = Main.getFormatter().parseString(quantity);
            } catch (Exception ex) {
                player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
                return;
            }
        }

        if (amount < 0) {
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.Wrong amount").replace("&", "§"));
            return;
        }

        if (!EcoPlayer.getPlayers().containsKey(receiver)) {
            player.sendMessage("§cJogador inexistente.");
            return;
        }

        PlayerMoneyChangeEvent event = new PlayerMoneyChangeEvent(amount, sender.getName(), receiver);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(receiver);
        ecoPlayer.setMoney(amount);
        sender.sendMessage("§aVocê definiu §f" + Main.getFormatter().formatNumber(amount) + " §acoins ao jogador §f" + player.getName());

    }

    public static List<String> top(){

        List<String> top = new ArrayList<>();
        HashMap<String, Double> amounts = new HashMap<>();
        final int[] position = {1};

        EcoPlayer.getPlayers().forEach((s, ecoPlayer) -> amounts.put(s, ecoPlayer.getMoney()));

        TreeSet<Map.Entry<String, Double>> sortedEntrySet = new TreeSet<Map.Entry<String, Double>>((e1, e2) -> (int) (e2.getValue() - e1.getValue()));
        sortedEntrySet.addAll(amounts.entrySet());

        sortedEntrySet.forEach(user -> {

            if (position[0] > 10) return;
            top.add(" §f" + position[0] + "º " + user.getKey() + ": §a" + Main.getFormatter().formatNumber(user.getValue()));
            position[0]++;

        });

        return top;

    }

}
