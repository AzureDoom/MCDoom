package mod.azure.doom.item;

import mod.azure.doom.DoomMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

public class E1M1MusicDisc extends MusicDiscItem {

	public E1M1MusicDisc(SoundEvent soundSupplier) {
		super(1, soundSupplier,
				(new Item.Settings()).maxCount(1).group(DoomMod.DoomPowerUPItemGroup).rarity(Rarity.RARE));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

}