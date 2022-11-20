package de.hglabor.kits.impl;

import de.hglabor.kitapi.kit.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrostyKit extends AbstractKit {
    private int potionAmplifier = 2;
    private PotionEffectType potionEffectType = PotionEffectType.SPEED;

    public FrostyKit() {
        super("Frosty");
        onKitPlayerEvent(PlayerMoveEvent.class, (event, kitPlayer) -> {
            Player player = event.getPlayer();
            Material type = player.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
            if (type == Material.SNOW_BLOCK || type == Material.SNOW) {
                player.addPotionEffect(new PotionEffect(potionEffectType, 20, potionAmplifier));
                player.setFreezeTicks(60);
            }
        });
    }
}
