package de.hglabor.kits.impl;


import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.BlockChangedTypeEvent;
import de.hglabor.kitapi.kit.event.BlockSetToAirEvent;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class KayaKit extends AbstractKit {
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.GRASS_BLOCK)
            .withName(Component.text("Kaya Block").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN))
            .withAmount(16)
            .build();
    public static final String KAYA_KEY = "kayaBlock";

    public KayaKit() {
        super("Kaya");
        onEvent(BlockBreakEvent.class, blockBreakEvent -> {
            blockBreakEvent.getBlock().removeMetadata(KAYA_KEY, KitApi.getPlugin());
        });
        onEvent(BlockChangedTypeEvent.class, event -> {
            event.getBlock().removeMetadata(KAYA_KEY, KitApi.getPlugin());
        });
        onKitItemPlace((blockPlaceEvent, kitPlayer) -> {
            blockPlaceEvent.getBlockPlaced().setMetadata(KAYA_KEY, new FixedMetadataValue(KitApi.getPlugin(), ""));
        });
        onPlayerEvent(PlayerMoveEvent.class, (event, kitPlayer) -> {
            Player player = event.getPlayer();
            if (!player.isOnGround()) return;
            if (event.getTo().distanceSquared(event.getFrom()) == 0) return;
            if (kitPlayer.hasKit(this)) return;
            Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);
            if (block.hasMetadata(KAYA_KEY)) {
                block.setType(Material.AIR);
                callEvent(new BlockSetToAirEvent(block));
            }
        });
        registerRecipe();
    }

    private void registerRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(KitApi.getPlugin(), KAYA_KEY), KIT_ITEM);
        recipe.addIngredient(Material.WHEAT_SEEDS);
        recipe.addIngredient(Material.DIRT);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM);
    }
}
