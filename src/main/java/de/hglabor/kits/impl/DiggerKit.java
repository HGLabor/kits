package de.hglabor.kits.impl;


import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.BlockSetToAirEvent;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftBlock;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DiggerKit extends AbstractKit {
    private int radius = 6;
    private float cooldown = 12f;
    private int maxUsage = 3;

    public DiggerKit() {
        super("Digger");
        onKitItemPlace((event, kitPlayer) -> {
            Block block = event.getBlock();
            block.setType(Material.AIR);
            applyCooldown(kitPlayer, cooldown, maxUsage);
            runTaskLater(() -> {
                int dist = (int) Math.ceil((double) (radius - 1) / 2);
                for (int y = -1; y >= -radius; y--) {
                    for (int x = -dist; x <= dist; x++) {
                        for (int z = -dist; z <= dist; z++) {
                            if (block.getY() + y <= 0) {
                                continue;
                            }
                            Block toBreak = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);
                            if (!toBreak.getType().equals(Material.BEDROCK)) {
                                toBreak.getWorld().playEffect(toBreak.getLocation(), Effect.STEP_SOUND, net.minecraft.world.level.block.Block.getId(((CraftBlock) toBreak).getNMS()));
                                if (toBreak instanceof Container) {
                                    toBreak.breakNaturally();
                                } else {
                                    toBreak.setType(Material.AIR);
                                }
                                callEvent(new BlockSetToAirEvent(toBreak));
                            }
                        }
                    }
                }
            }, 15L);
        });
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(new KitItemBuilder(Material.DRAGON_EGG).withAmount(16).build());
    }
}
