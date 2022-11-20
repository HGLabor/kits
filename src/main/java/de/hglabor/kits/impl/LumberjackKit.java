package de.hglabor.kits.impl;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LumberjackKit extends AbstractKit {
    private int maxLogs = 300;

    public LumberjackKit() {
        super("Lumberjack");
        onKitItemRightClick((event, kitPlayer) -> event.setCancelled(true));
        onKitItemBreaksBlock((event, kitPlayer) -> {
            String blockTypeName = event.getBlock().getType().name().toLowerCase();
            if ((blockTypeName.contains("wood") || blockTypeName.contains("log") || blockTypeName.contains("stem") || blockTypeName.contains("hyphae")) && !blockTypeName.contains("mushroom")) {
                kitPlayer.putKitAttribute("logCounter", new AtomicInteger());
                breakSurroundingWood(event.getBlock(), kitPlayer);
            }
        });
    }

    public void breakSurroundingWood(Block block, IKitPlayer kitPlayer) {
        String blockTypeName = block.getType().name().toLowerCase();
        if (blockTypeName.contains("wood") || blockTypeName.contains("log") || blockTypeName.contains("stem") || blockTypeName.contains("hyphae")) {
            block.breakNaturally();
            AtomicInteger count = kitPlayer.getKitAttribute("logCounter");
            if (count.getAndIncrement() > maxLogs) return;
            BlockFace[] faces = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : faces) {
                breakSurroundingWood(block.getRelative(face), kitPlayer);
            }
        }
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(new KitItemBuilder(Material.WOODEN_AXE).withName(Component.text("Lumberjack").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GREEN)).makeUnbreakable().build());
    }
}
