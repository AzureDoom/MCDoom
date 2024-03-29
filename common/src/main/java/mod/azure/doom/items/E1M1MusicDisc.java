package mod.azure.doom.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import org.jetbrains.annotations.NotNull;

public class E1M1MusicDisc extends RecordItem {

    public E1M1MusicDisc(SoundEvent soundSupplier) {
        super(1, soundSupplier, new Properties().stacksTo(1).rarity(Rarity.RARE), 60);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

}