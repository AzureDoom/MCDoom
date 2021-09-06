package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.enchantments.LeapingDoomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoomEnchantments {

	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, DoomMod.MODID);

	public static final RegistryObject<Enchantment> LEAPING_DOOM = ENCHANTMENTS.register("leaping_doom",
			() -> new LeapingDoomEnchantment(Rarity.RARE, EquipmentSlotType.FEET));

}
