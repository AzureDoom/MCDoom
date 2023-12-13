package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.enchantments.MicroMissilesEnchantment;
import mod.azure.doom.enchantments.MicrowaveBeamEnchantment;
import mod.azure.doom.enchantments.StickyBombEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class FabricDoomEnchantments {
    public static final Enchantment MICRO_MOD = new MicroMissilesEnchantment(EquipmentSlot.MAINHAND);
    public static final Enchantment MICROWAVE_MOD = new MicrowaveBeamEnchantment(EquipmentSlot.MAINHAND);
    public static final Enchantment STICKY_MOD = new StickyBombEnchantment(EquipmentSlot.MAINHAND);

    public static void initialize() {
        Registry.register(BuiltInRegistries.ENCHANTMENT, MCDoom.modResource("microattachment"), MICRO_MOD);
        Registry.register(BuiltInRegistries.ENCHANTMENT, MCDoom.modResource("microwaveattachment"), MICROWAVE_MOD);
        Registry.register(BuiltInRegistries.ENCHANTMENT, MCDoom.modResource("stickyattachment"), STICKY_MOD);
    }
}
