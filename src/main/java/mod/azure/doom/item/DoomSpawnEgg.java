package mod.azure.doom.item;

import mod.azure.doom.DoomMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class DoomSpawnEgg extends SpawnEggItem {

	public DoomSpawnEgg(EntityType<? extends Mob> type, int primaryColor, int secondaryColor) {
		super(type, primaryColor, secondaryColor, new Item.Properties().stacksTo(64).tab(DoomMod.DoomEggItemGroup));
	}

}