package mod.azure.doom.item.eggs;

import mod.azure.doom.DoomMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class DoomSpawnEgg extends SpawnEggItem {

	public DoomSpawnEgg(EntityType<? extends MobEntity> type) {
		super(type, 11022961, 11035249, new Item.Settings().maxCount(1).group(DoomMod.DoomEggItemGroup));
	}

}