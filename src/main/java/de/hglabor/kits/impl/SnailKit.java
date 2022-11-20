package de.hglabor.kits.impl;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.util.ChanceUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnailKit extends AbstractKit {
    private final int likelihood, effectDuration, effectMultiplier;
    private final PotionEffectType effectType;

    public SnailKit() {
        super("Snail");
        likelihood = 25;
        effectDuration = 4;
        effectMultiplier = 0;
        effectType = PotionEffectType.SLOW;
        onKitPlayerAttacksEntity((event, kitPlayer) -> {
            if (event.getEntity() instanceof LivingEntity livingEntity && ChanceUtils.roll(likelihood)) {
                livingEntity.addPotionEffect(new PotionEffect(effectType, effectDuration * 20, effectMultiplier, true, true));
            }
        });
    }
}
