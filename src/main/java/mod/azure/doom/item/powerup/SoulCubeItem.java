package mod.azure.doom.item.powerup;

import mod.azure.doom.DoomMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SoulCubeItem extends Item {

	public SoulCubeItem() {
		super(new Item.Properties().stacksTo(1).durability(5).tab(DoomMod.DoomPowerUPItemGroup));
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}