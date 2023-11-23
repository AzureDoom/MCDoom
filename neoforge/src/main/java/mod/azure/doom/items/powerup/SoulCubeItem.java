package mod.azure.doom.items.powerup;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SoulCubeItem extends Item {

    public SoulCubeItem() {
        super(new Properties().stacksTo(1).durability(5));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}