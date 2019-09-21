package com.yerti.blackmarket.menu;

import com.yerti.blackmarket.core.menus.Page;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

public class BlackMarketMenu implements Listener {

    private static Map<ItemStack, ItemStack> trades = new HashMap<>();


    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BLACK MARKET");

        int index = 0;
        for (Map.Entry<ItemStack, ItemStack> entry : trades.entrySet()) {
            inventory.setItem(index, entry.getKey());
            inventory.setItem(index + 18, entry.getValue());
            index++;
        }


        return inventory;
    }

    public BlackMarketMenu() {

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() == null) return;
        if (event.getInventory().getName() == null) return;
        if (event.getInventory().getName() == "") return;

        if (event.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BLACK MARKET")) {
            trades.clear();

            for (int i = 0; i < 8; i++) {
                if (event.getInventory().getContents()[i] != null && event.getInventory().getContents()[i + 18] != null) {
                    if (!event.getInventory().getContents()[i].equals(Material.AIR) && !event.getInventory().getContents()[i + 18].equals(Material.AIR)) {
                        trades.put(event.getInventory().getContents()[i], event.getInventory().getContents()[i + 18]);
                    }
                }
            }

            event.getPlayer().sendMessage(ChatColor.GREEN + "Successfully saved the trades for the BlackMarket!");

        }




    }

    public Map<ItemStack, ItemStack> getTrades() { return trades; }

    public void setTrades(Map<ItemStack, ItemStack> trades) { this.trades = trades; }
}
