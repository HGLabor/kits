package de.hglabor.kits.impl;

import de.hglabor.kitapi.kit.AbstractKit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class NinjaKit extends AbstractKit {
    private final long lastHitExpiration = 15L;
    private final double radius = 30D;
    private final Material material = Material.ANDESITE_SLAB;
    private float cooldown = 4f;

    public NinjaKit() {
        super("Ninja");
        onKitPlayerEvent(PlayerToggleSneakEvent.class, (event, kitPlayer) -> {
            if (event.isSneaking() && kitPlayer.getLatestTarget().left() != null) {
                if (kitPlayer.getLatestTarget().right() + lastHitExpiration * 1000L > System.currentTimeMillis()) {
                    Player player = event.getPlayer();
                    Entity target = kitPlayer.getLatestTarget().left();
                    if (distanceBetweenEntities(player, target) < radius * radius) {
                        player.teleport(calculateNinjaBehind(target));
                        player.getLocation().getBlock().setType(material);
                        applyCooldown(kitPlayer, cooldown);
                        kitPlayer.setLatestTarget(null);
                    }
                }
            }
        }, PlayerToggleSneakEvent::isSneaking);
    }

    private Location calculateNinjaBehind(Entity entity) {
        float nang = entity.getLocation().getYaw() + 90;
        if (nang < 0) nang += 360;
        double nX = Math.cos(Math.toRadians(nang));
        double nZ = Math.sin(Math.toRadians(nang));
        return entity.getLocation().clone().subtract(nX, 0, nZ);
    }

    private double distanceBetweenEntities(Player player, Entity entity) {
        Location ninjaLocation = player.getLocation().clone();
        Location entityLocation = entity.getLocation().clone();
        ninjaLocation.setY(0);
        entityLocation.setY(0);
        return ninjaLocation.distanceSquared(entityLocation);
    }
}
