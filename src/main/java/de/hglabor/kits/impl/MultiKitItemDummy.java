package de.hglabor.kits.impl;


import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MultiKitItemDummy extends AbstractKit {
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.IRON_INGOT).makeUnbreakable().build();
    private static final ItemStack KIT_ITEM_2 = new KitItemBuilder(Material.GOLD_BLOCK).makeUnbreakable().build();
    private float goldCooldown = 5f;
    private float ironCooldown = 5f;
    private float cooldown = 5f;

    public MultiKitItemDummy() {
        super("MultiKitDummy");
        onKitItemLeftClick((event, kitPlayer) -> {
            kitPlayer.sendMessage("LeftClicked " + event.getItem().getType());
            applyCooldown(kitPlayer, cooldown, "LeftClick");
        }, KIT_ITEM, "LeftClick");
        onKitItemRightClick((event, kitPlayer) -> {
            kitPlayer.sendMessage("RightClicked " + event.getItem().getType());
        }, KIT_ITEM);
        onKitItemLeftClick((event, kitPlayer) -> {
            kitPlayer.sendMessage("Yoo " + event.getItem().getType());
        }, KIT_ITEM_2);
    }

    public void test(EntityDamageByEntityEvent event, IKitPlayer kitPlayer) {
        event.getEntity().setGlowing(!event.getEntity().isGlowing());
        event.getEntity().setGravity(false);
        event.getEntity().addPassenger(kitPlayer.getPlayer().orElseThrow());
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM, KIT_ITEM_2);
    }
}
