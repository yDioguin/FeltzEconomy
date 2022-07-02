package pt.dioguin.feltzeconomy.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyChangeEvent extends Event implements Cancellable {

    private double amount;
    private String sender;
    private String receiver;
    private boolean isCancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerMoneyChangeEvent(double amount, String sender, String receiver){
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
