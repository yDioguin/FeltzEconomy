package pt.dioguin.feltzeconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.dioguin.feltzeconomy.Main;
import pt.dioguin.feltzeconomy.manager.EconomyManager;
import pt.dioguin.feltzeconomy.objects.EcoPlayer;

public class Money implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if (!(s instanceof Player)){
            s.sendMessage("§cApenas jogadores podem executar este comando.");
            return true;
        }

        Player player = (Player) s;

        if (args.length == 0){

            EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(player.getName());
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.See your money").replace("&", "§").replace("{formattedMoney}", ecoPlayer.getFormattedMoney()));
            return true;

        }

        if (args.length == 1){

            if (args[0].equalsIgnoreCase("top")){

                player.sendMessage("");
                player.sendMessage("§eTOP §f10 §ejogadores mais ricos.");
                player.sendMessage("");
                EconomyManager.top().forEach(player::sendMessage);
                player.sendMessage("");

                return true;

            }

            String target = args[0];

            if (!EcoPlayer.getPlayers().containsKey(target)){
                player.sendMessage(Main.getInstance().getConfig().getString("Messages.Invalid player").replace("&", "§"));
                return true;
            }

            EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(target);
            player.sendMessage(Main.getInstance().getConfig().getString("Messages.See another player's money").replace("&", "§").replace("{player}", target).replace("{formattedMoney}", ecoPlayer.getFormattedMoney()));
            return true;

        }

        if (args[0].equalsIgnoreCase("pay")){

            if (args.length != 3){
                player.sendMessage("§cDigite '/money pay <jogador> <quantia>'");
                return true;
            }

            String received = args[1];
            String amount = args[2];

            EconomyManager.pay(player.getName(), received, amount);
            return true;

        }

        if (player.hasPermission("feltzeconomy.admin")){

            if (args[0].equalsIgnoreCase("add")){

                if (args.length != 3){
                    player.sendMessage("§cDigite '/money add <jogador> <quantia>'");
                    return true;
                }

                String target = args[1];
                String amount = args[2];

                EconomyManager.add(player, target, amount);

                return true;

            }

            if (args[0].equalsIgnoreCase("remove")){

                if (args.length != 3){
                    player.sendMessage("§cDigite '/money remove <jogador> <quantia>'");
                    return true;
                }

                String target = args[1];
                String amount = args[2];

                EconomyManager.remove(player, target, amount);

                return true;

            }

            if (args[0].equalsIgnoreCase("set")){

                if (args.length != 3){
                    player.sendMessage("§cDigite '/money set <jogador> <quantia>'");
                    return true;
                }

                String target = args[1];
                String amount = args[2];

                EconomyManager.set(player, target, amount);

                return true;

            }

        }

        return false;
    }
}
