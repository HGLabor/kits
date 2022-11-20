package de.hglabor.kits.impl;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class FiremanKit extends AbstractKit {
    public FiremanKit() {
        super("Fireman");
        onKitPlayerEvent(EntityDamageEvent.class, (event, kitPlayer) -> {

        });
    }

    @Override
    public void onEnable(IKitPlayer kitPlayer) {
        kitPlayer.getPlayer().ifPresent(player -> player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET)));
    }
}
