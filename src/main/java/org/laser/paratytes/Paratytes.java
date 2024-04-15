package org.laser.paratytes;

import jdk.internal.icu.impl.BMPSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class Paratytes extends JavaPlugin implements Listener {

    private CreateWorld createWorld;

    private HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();

    private FileConfiguration playerDataConfig;
    private File playerDataConfigFile;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        playerDataConfigFile = new File(getDataFolder(), "playerData.yml");
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataConfigFile);
    }

    // saves inventory + player gets lobby inventory
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        saveInventory(player);
        player.getInventory().clear();

        if (!playerDataConfig.contains(player.getUniqueId().toString())) {
            playerDataConfig.set(player.getUniqueId().toString(), 0);
        }

        int worldAmount = playerDataConfig.getInt(player.getUniqueId().toString());

        if (playerDataConfig.contains(player.getUniqueId().toString())) {

            ItemStack unusedMap = new ItemStack(Material.PAPER);
            ItemMeta unusedMapMeta = unusedMap.getItemMeta();
            unusedMapMeta.setCustomModelData(1);
            unusedMapMeta.setDisplayName("§aUnused Map");
            unusedMap.setItemMeta(unusedMapMeta);
            ItemStack usedMap = new ItemStack(Material.PAPER);
            ItemMeta usedMapMeta = usedMap.getItemMeta();
            usedMapMeta.setCustomModelData(2);
            usedMapMeta.setDisplayName("§eUsed Map");
            usedMap.setItemMeta(usedMapMeta);


            if (worldAmount == 0) {
                player.getInventory().setItem(11, unusedMap);
                player.getInventory().setItem(13, unusedMap);
                player.getInventory().setItem(15, unusedMap);
            } else if (worldAmount == 1) {
                player.getInventory().setItem(11, usedMap);
                player.getInventory().setItem(13, unusedMap);
                player.getInventory().setItem(15, unusedMap);
            } else if (worldAmount == 2) {
                player.getInventory().setItem(11, usedMap);
                player.getInventory().setItem(13, usedMap);
                player.getInventory().setItem(15, unusedMap);
            } else if (worldAmount == 3) {
                player.getInventory().setItem(11, usedMap);
                player.getInventory().setItem(13, usedMap);
                player.getInventory().setItem(15, usedMap);
            }
        }
    }

    // Handle player left-click with item in hand
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
    }

    // save / restore inventory
    private void saveInventory(Player player) {
        savedInventories.put(player.getUniqueId(), player.getInventory().getContents());
    }

    private void restoreInventory(Player player) {
        if (savedInventories.containsKey(player.getUniqueId())) {
            player.getInventory().setContents(savedInventories.get(player.getUniqueId()));
            savedInventories.remove(player.getUniqueId()); // Inventar aus der Speicherliste entfernen
        }
    }



    private void openBaseInventory(Player player) {

        Inventory baseInventory = Bukkit.createInventory(player, org.bukkit.event.inventory.InventoryType.HOPPER, "Base Operations Menu");

        player.openInventory(baseInventory);
    }

    private void handleBaseOperations(Player player, ItemStack clickedItem) {
        if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
            String itemName = clickedItem.getItemMeta().getDisplayName();
            if (itemName.equals("Establish a base")) {
                // Funktion zum Gründen einer Base
            } else if (itemName.equals("Join base")) {
                // Funktion zum Beitritt zu einer Base
            } else if (itemName.equals("Return to base")) {
                // Funktion zur Rückkehr zur eigenen Base
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}