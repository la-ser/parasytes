package org.laser.paratytes;

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


    private File playerDataFile;
    private FileConfiguration playerDataConfig;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        createWorld = new CreateWorld(this);


        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
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

        //hier musst du spieler inventar vergeben und die welten benennen jede einezlne situation

    }

    private void givePlayerInventory() {

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

    // Handle player left-click with item in hand
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

    }

    private void openBaseMenu(Player player) {

        Inventory baseInventory = Bukkit.createInventory(player, org.bukkit.event.inventory.InventoryType.HOPPER, "Base Operations Menu");

        ItemStack establishBaseItem = new ItemStack(Material.GHAST_TEAR);
        ItemMeta establishBaseMeta = establishBaseItem.getItemMeta();
        establishBaseMeta.setDisplayName("Establish a base");
        establishBaseItem.setItemMeta(establishBaseMeta);
        baseInventory.setItem(0, establishBaseItem);

        player.openInventory(baseInventory);
    }

    private void handleBaseOperations(Player player, ItemStack clickedItem) {

        if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
            String itemName = clickedItem.getItemMeta().getDisplayName();
            if (itemName.equals("Establish a base")) {
                // Funktion zum Gründen einer Base
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}