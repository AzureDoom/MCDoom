package mod.azure.doom.item;

import java.util.function.Supplier;

import mod.azure.doom.DoomMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

public class E1M1MusicDisc extends RecordItem {

	public E1M1MusicDisc(Supplier<SoundEvent> soundSupplier) {
		super(1, soundSupplier,
				(new Item.Properties()).stacksTo(1).tab(DoomMod.DoomPowerUPItemGroup).rarity(Rarity.RARE), 60);
	}

}