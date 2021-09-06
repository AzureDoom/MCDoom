package mod.azure.doom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mod.azure.doom.DoomMod;
import mod.azure.doom.mixin.BuilderAccessor;
import mod.azure.doom.mixin.LootContextAccessor;
import mod.azure.doom.mixin.LootManagerAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class StructureModdedLootImporter {

	private static final Map<LootTable, Identifier> REVERSED_TABLES = new HashMap<>();
	private static final Map<Identifier, Identifier> TABLE_IMPORTS = createMap();

	private static Map<Identifier, Identifier> createMap() {
		Map<Identifier, Identifier> tableMap = new HashMap<>();
		tableMap.put(new Identifier("minecraft:chests/bastion_bridge"),
				new Identifier(DoomMod.MODID, "chests/bastion_bridge"));
		tableMap.put(new Identifier("minecraft:chests/bastion_hoglin_stable"),
				new Identifier(DoomMod.MODID, "chests/bastion_hoglin_stable"));
		tableMap.put(new Identifier("minecraft:chests/bastion_other"),
				new Identifier(DoomMod.MODID, "chests/bastion_other"));
		tableMap.put(new Identifier("minecraft:chests/bastion_treasure"),
				new Identifier(DoomMod.MODID, "chests/bastion_treasure"));
		tableMap.put(new Identifier("minecraft:chests/nether_bridge"),
				new Identifier(DoomMod.MODID, "chests/nether_bridge"));
		tableMap.put(new Identifier("minecraft:chests/ruined_portal"),
				new Identifier(DoomMod.MODID, "chests/ruined_portal"));
		tableMap.put(new Identifier("minecraft:chests/spawn_bonus_chest"),
				new Identifier(DoomMod.MODID, "chests/spawn_bonus_chest"));
		return tableMap;
	}

	public static List<ItemStack> checkAndGetModifiedLoot(LootContext context, LootTable currentLootTable,
			List<ItemStack> originalLoot) {
		Identifier lootTableID = REVERSED_TABLES.computeIfAbsent(currentLootTable,
				(lootTable) -> ((LootManagerAccessor) context.getWorld().getServer().getLootManager()).rs_getTables()
						.entrySet().stream().filter(entry -> lootTable.equals(entry.getValue())).map(Map.Entry::getKey)
						.findFirst().orElse(null));

		if (lootTableID != null) {
			return StructureModdedLootImporter.modifyLootTables(context, lootTableID, originalLoot);
		}

		return new ArrayList<>();
	}

	public static List<ItemStack> modifyLootTables(LootContext context, Identifier lootTableID,
			List<ItemStack> originalLoot) {
		Identifier tableToImportLoot = TABLE_IMPORTS.get(lootTableID);
		if (tableToImportLoot == null)
			return originalLoot;

		LootContext newContext = copyLootContextWithNewQueryID(context);
		List<ItemStack> newlyGeneratedLoot = newContext.getSupplier(tableToImportLoot).generateLoot(newContext);

		newlyGeneratedLoot.removeIf(itemStack -> {
			RegistryKey<Item> itemKey = Registry.ITEM.getKey(itemStack.getItem()).orElse(null);
			return itemKey != null && itemKey.getValue().getNamespace().equals("minecraft");
		});

		originalLoot.addAll(newlyGeneratedLoot);
		return originalLoot;
	}

	private static LootContext copyLootContextWithNewQueryID(LootContext oldLootContext) {
		LootContext.Builder newContextBuilder = new LootContext.Builder(oldLootContext.getWorld())
				.random(oldLootContext.getRandom()).luck(oldLootContext.getLuck());

		((BuilderAccessor) newContextBuilder).rs_setDrops(((LootContextAccessor) oldLootContext).rs_getDrops());
		((BuilderAccessor) newContextBuilder)
				.rs_setParameters(((LootContextAccessor) oldLootContext).rs_getParameters());
		return newContextBuilder.build(LootContextTypes.CHEST);
	}
}
