package org.laser.paratytes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Paratytes extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();

            if (event.getClickedInventory() != null) {

                if (event.getRawSlot() < event.getView().getTopInventory().getSize()) event.setCancelled(true);

                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.SCUTE) {

                    player.sendMessage("Lets begin the tail!");

                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
