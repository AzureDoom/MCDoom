package mod.azure.doom.util;

import mod.azure.doom.DoomMod;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoomMod.MODID)
public class LootHandler {
	@SubscribeEvent
	public static void lootLoad(LootTableLoadEvent evt) {
		String prefix = "minecraft:chests/";
		String name = evt.getName().toString();

		if (name.startsWith(prefix)) {
			String file = name.substring(name.indexOf(prefix) + prefix.length());
			switch (file) {
			case "ruined_portal":
			case "bastion_bridge":
			case "bastion_hoglin_stable":
			case "bastion_other":
			case "bastion_treasure":
			case "nether_bridge":
			case "spawn_bonus_chest":
				evt.getTable().addPool(getInjectPool(file));
				break;
			default:
				break;
			}
		}
	}

	private static net.minecraft.loot.LootPool getInjectPool(String entryName) {
		return LootPool.lootPool().add(getInjectEntry(entryName, 1)).bonusRolls(0, 1).name("doom_inject").build();
	}

	@SuppressWarnings("rawtypes")
	private static LootEntry.Builder getInjectEntry(String name, int weight) {
		ResourceLocation table = new ResourceLocation(DoomMod.MODID, "chests/" + name);
		return TableLootEntry.lootTableReference(table).setWeight(weight);
	}
}