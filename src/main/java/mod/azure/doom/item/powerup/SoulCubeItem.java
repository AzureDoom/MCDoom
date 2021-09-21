package mod.azure.doom.item.powerup;

import mod.azure.doom.DoomMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SoulCubeItem extends Item {

	public SoulCubeItem() {
		super(new Item.Settings().group(DoomMod.DoomPowerUPItemGroup).maxCount(1).maxDamage(5));
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}