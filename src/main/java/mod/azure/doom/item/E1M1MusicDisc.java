package mod.azure.doom.item;

import java.util.function.Supplier;

import mod.azure.doom.DoomMod;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;

public class E1M1MusicDisc extends MusicDiscItem {

	public E1M1MusicDisc(Supplier<SoundEvent> soundSupplier) {
		super(1, soundSupplier,
				(new Item.Properties()).stacksTo(1).tab(DoomMod.DoomPowerUPItemGroup).rarity(Rarity.RARE));
	}

}