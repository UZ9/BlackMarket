package com.yerti.blackmarket;

import com.yerti.blackmarket.command.BlackMarketCommand;
import com.yerti.blackmarket.core.commands.CustomCommand;
import com.yerti.blackmarket.menu.BlackMarketMenu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackMarket extends JavaPlugin {

    @Override
    public void onEnable() {

        if (!new File("/BlackMarket/config.yml").exists()) {
            saveDefaultConfig();
        }

        new BlackMarketCommand(this).initCommand();

        Bukkit.getPluginManager().registerEvents(new BlackMarketMenu(), this);

        Map<ItemStack, ItemStack> trades = new HashMap<>();

        if (getConfig().getConfigurationSection("trade-items") == null) return;

        for (String key : getConfig().getConfigurationSection("trade-items").getKeys(false)) {
            if (getConfig().get("trade-items." + key) instanceof List) {
                List<ItemStack> tempList = (List<ItemStack>) getConfig().get("trade-items." + key);
                trades.put(tempList.get(0), tempList.get(1));

            }

        }

        new BlackMarketMenu().setTrades(trades);



    }

    @Override
    public void onDisable() {
        BlackMarketMenu menu = new BlackMarketMenu();

        Map<ItemStack, ItemStack> trades = menu.getTrades();

        if (trades == null) return;

        int index = 0;
        for (Map.Entry<ItemStack, ItemStack> trade : trades.entrySet()) {
            List<ItemStack> tempList = new ArrayList<>();
            tempList.add(trade.getKey());
            tempList.add(trade.getValue());

            getConfig().set("trade-items." + index, tempList);
            saveConfig();
            index++;
        }
    }

}
