package org.laser.paratytes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

import java.util.HashMap;
import java.util.UUID;

public final class Paratytes extends JavaPlugin implements Listener {

    private CreateWorld createWorld;

    private HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        createWorld = new CreateWorld(this);
    }

    // saves inventory + player gets lobby inventory
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        saveInventory(player);
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemStack(Material.SCUTE, 1));
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
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType() == Material.SCUTE && event.getAction().name().contains("LEFT_CLICK")) {
            openBaseInventory(player);
        }
    }

    private void openBaseInventory(Player player) {
        Inventory baseInventory = Bukkit.createInventory(player, org.bukkit.event.inventory.InventoryType.HOPPER, "Base Operations Menu");

        ItemStack establishBaseItem = new ItemStack(Material.GHAST_TEAR);
        ItemMeta establishBaseMeta = establishBaseItem.getItemMeta();
        establishBaseMeta.setDisplayName("Establish a base");
        establishBaseItem.setItemMeta(establishBaseMeta);
        baseInventory.setItem(0, establishBaseItem);

        ItemStack joinBaseItem = new ItemStack(Material.ENDER_EYE);
        ItemMeta joinBaseMeta = joinBaseItem.getItemMeta();
        joinBaseMeta.setDisplayName("Join base");
        joinBaseItem.setItemMeta(joinBaseMeta);
        baseInventory.setItem(2, joinBaseItem);

        ItemStack returnToBaseItem = new ItemStack(Material.COMPASS);
        ItemMeta returnToBaseMeta = returnToBaseItem.getItemMeta();
        returnToBaseMeta.setDisplayName("Return to base");
        returnToBaseItem.setItemMeta(returnToBaseMeta);
        baseInventory.setItem(4, returnToBaseItem);

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