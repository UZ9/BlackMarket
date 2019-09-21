package com.yerti.blackmarket.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {

    List<Page> menus;
    List<String> inventories;

    public MenuListener(List<Page> menus) {
        this.menus = menus;
        this.inventories = new ArrayList<>();
        menus.forEach(menu -> {
            inventories.add(menu.build().getName());
            System.out.println("Added " + menu.build().getName());
        });
    }

    @EventHandler
    //TODO: Change to contains inventory ratehr than name
    public void onPlayerClick(InventoryClickEvent event) {
        Bukkit.broadcastMessage("Amount: " + inventories.size());
        if (event.getInventory() == null) return;
        if (!inventories.contains(event.getInventory().getName())) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        if (event.getCurrentItem().getItemMeta() == null) return;

        Page menu = menus.get(inventories.indexOf(event.getInventory().getName()));

        Bukkit.broadcastMessage("3");
        menu.getMenuItems().forEach(menuItem -> {
            Bukkit.broadcastMessage("4");
            if (event.getCurrentItem().getItemMeta().equals(menuItem.toStack().getItemMeta())) {
                Bukkit.broadcastMessage("5");
                if (event.getCurrentItem().getType().equals(Material.AIR)) return;
                menuItem.onClick(event);
            }
        });



    }


}
