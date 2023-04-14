package mod.azure.doom.item;

import java.util.function.Supplier;

import mod.azure.doom.DoomMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class DoomSpawnEgg extends ForgeSpawnEggItem {

	public DoomSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor) {
		super(type, primaryColor, secondaryColor, new Item.Properties().stacksTo(64).tab(DoomMod.DoomEggItemGroup));
	}

}