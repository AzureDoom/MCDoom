package mod.azure.doom.item;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

public class E1M1MusicDisc extends RecordItem {

	public E1M1MusicDisc(Supplier<SoundEvent> soundSupplier) {
		super(1, soundSupplier,
				(new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 60);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

}