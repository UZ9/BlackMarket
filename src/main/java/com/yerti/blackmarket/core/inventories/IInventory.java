package com.yerti.blackmarket.core.inventories;

import com.sun.media.jfxmedia.events.PlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface IInventory extends InventoryHolder {

    public void onGUI(Player player, int slot, ItemStack clickedItem, InventoryClickEvent event);

}
