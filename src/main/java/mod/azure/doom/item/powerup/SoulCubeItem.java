package mod.azure.doom.item.powerup;

import dev.emi.trinkets.api.TrinketItem;
import mod.azure.doom.DoomMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SoulCubeItem extends TrinketItem {

	public SoulCubeItem() {
		super(new Item.Settings().group(DoomMod.DoomPowerUPItemGroup).maxCount(1).maxDamage(5));
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}