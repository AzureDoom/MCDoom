package mod.azure.doom.util.registry;

import mod.azure.azurelib.AzureLib;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.entity.tileentity.TotemEntity;
import mod.azure.doom.network.PacketHandler;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.MobAttributes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModRegistry {

	public static BlockEntityType<TotemEntity> TOTEM;
	public static BlockEntityType<IconBlockEntity> ICON;
	public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;

	public static void initialize() {

		DoomBlocks.initialize();
		DoomEntities.initialize();
		DoomProjectiles.initialize();
		DoomItems.initItems();
		DoomItems.initArmor();
		DoomItems.initEggs();
		DoomSounds.initialize();
		MobSpawn.addSpawnEntries();
		MobAttributes.initialize();
		AzureLib.initialize();
		PacketHandler.registerMessages();
		DoomStructures.registerStructureFeatures();
		if (DoomMod.config.enable_all_villager_trades)
			ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> DoomVillagerTrades.addTrades());
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (DoomLoot.BASTION_BRIDGE.equals(id) || DoomLoot.BASTION_HOGLIN_STABLE.equals(id) || DoomLoot.BASTION_OTHER.equals(id) || DoomLoot.BASTION_TREASURE.equals(id) || DoomLoot.NETHER_BRIDGE.equals(id) || DoomLoot.RUINED_PORTAL.equals(id) || DoomLoot.SPAWN_BONUS_CHEST.equals(id)) {
				final LootPool poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).with(LootItem.lootTableItem(DoomItems.INMORTAL).build()).with(LootItem.lootTableItem(DoomItems.INVISIBLE).build()).with(LootItem.lootTableItem(DoomItems.MEGA).build()).with(LootItem.lootTableItem(DoomItems.POWER).build()).with(LootItem.lootTableItem(DoomItems.SOULCUBE).build()).with(LootItem.lootTableItem(DoomItems.DAISY).build()).build();
				supplier.pool(poolBuilder);
			}
		});
		ICON = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, DoomMod.modResource("icon"), FabricBlockEntityTypeBuilder.create(IconBlockEntity::new, DoomBlocks.ICON_WALL1).build(null));
		TOTEM = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, DoomMod.modResource("totem"), FabricBlockEntityTypeBuilder.create(TotemEntity::new, DoomBlocks.TOTEM).build(null));
		GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, DoomMod.modResource("guntable"), FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, DoomBlocks.GUN_TABLE).build(null));
		SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
		Registry.register(BuiltInRegistries.MENU, DoomMod.modResource("guntable_screen_type"), SCREEN_HANDLER_TYPE);
		CustomPortalBuilder.beginPortal().frameBlock(DoomBlocks.E1M1_1).lightWithItem(DoomItems.ARGENT_ENERGY).destDimID(DoomMod.modResource("doom_hell")).tintColor(139, 0, 0).registerPortal();
	}
}