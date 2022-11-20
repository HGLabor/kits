package de.hglabor.kits;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kits.impl.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class KitsPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        KitApi.register(new DiggerKit());
        KitApi.register(new LumberjackKit());
        KitApi.register(new KayaKit());
        KitApi.register(new NinjaKit());
        KitApi.register(new SnailKit());
        KitApi.register(new SwitcherKit());
        KitApi.register(new MultiKitItemDummy());
        KitApi.register(new FrostyKit());
    }
}
