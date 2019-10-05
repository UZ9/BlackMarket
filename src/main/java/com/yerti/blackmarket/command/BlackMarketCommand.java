package com.yerti.blackmarket.command;

import com.nisovin.shopkeepers.api.ShopkeepersAPI;
import com.nisovin.shopkeepers.api.shopkeeper.DefaultShopTypes;
import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.api.shopkeeper.ShopType;
import com.nisovin.shopkeepers.api.shopkeeper.Shopkeeper;
import com.nisovin.shopkeepers.api.shopobjects.DefaultShopObjectTypes;
import com.nisovin.shopkeepers.api.shopobjects.ShopObjectType;
import com.nisovin.shopkeepers.shopkeeper.admin.AdminShopkeeper;
import com.yerti.blackmarket.core.commands.CustomCommand;
import com.yerti.blackmarket.menu.BlackMarketMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Random;

public class BlackMarketCommand extends CustomCommand {

    Shopkeeper blackMarketMerchant;
    Location blackMarketLocation;

    int time = 0;

    //location:
    //  x: 0
    //  y: 0
    //  z: 0

    public BlackMarketCommand(Plugin plugin) {
        super("blackmarket", "blackmarket.admin");

        blackMarketLocation = new Location(Bukkit.getWorld(plugin.getConfig().getString("location.world")), plugin.getConfig().getDouble("location.x"), plugin.getConfig().getDouble("location.y"), plugin.getConfig().getDouble("location.z"));

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            if (blackMarketMerchant == null) {
                boolean spawnMarket = new Random().nextInt(1440) == 0;

                if (spawnMarket) {
                    spawnMerchant(blackMarketLocation);
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("broadcast-message")));
                }
            } else {
                if (time == 1) {
                    blackMarketMerchant.delete();
                    time = 0;
                    blackMarketMerchant = null;
                } else {
                    time++;
                }
            }


        }, 20L * 60L, 20L * 60L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0)  {
                ((Player) sender).openInventory(new BlackMarketMenu().getInventory());
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("forcespawn")) {
                    spawnMerchant(((Player) sender).getLocation());


                }
            }

        }


        return true;
    }

    private void spawnMerchant(Location location) {
        ShopType<?> shopType = DefaultShopTypes.ADMIN();
        ShopObjectType<?> defaultShopType = DefaultShopObjectTypes.getInstance().getAll().get(0);


        ShopCreationData data = ShopCreationData.create(null, shopType, defaultShopType, location, null);

        Shopkeeper shopkeeper = ShopkeepersAPI.handleShopkeeperCreation(data);

        blackMarketMerchant = shopkeeper;

        for (Map.Entry<ItemStack, ItemStack> trade : new BlackMarketMenu().getTrades().entrySet()) {
            ((AdminShopkeeper) shopkeeper).addOffer(trade.getValue(), trade.getKey(), null);
        }

        shopkeeper.setName("Black Market");
    }
}
