package mod.azure.doom;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.azurelib.AzureLib;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.entity.tileentity.TotemEntity;
import mod.azure.doom.network.PacketHandler;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.MobAttributes;
import mod.azure.doom.util.recipes.GunTableRecipe;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomLoot;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.DoomStructures;
import mod.azure.doom.util.registry.MobSpawn;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class DoomMod implements ModInitializer {

	public static DoomItems ITEMS;
	public static DoomSounds SOUNDS;
	public static DoomEntities MOBS;
	public static final String MODID = "doom";
	public static BlockEntityType<TotemEntity> TOTEM;
	public static BlockEntityType<IconBlockEntity> ICON;
	public static ProjectilesEntityRegister PROJECTILES;
	public static RecipeType<GunTableRecipe> GUN_TABLE_RECIPE;
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
	public static final ResourceLocation BFG = new ResourceLocation(MODID, "bfg");
	public static BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY;
	public static final ResourceLocation PISTOL = new ResourceLocation(MODID, "pistol");
	public static final ResourceLocation PLASMA = new ResourceLocation(MODID, "plamsa");
	public static final ResourceLocation BFG9000 = new ResourceLocation(MODID, "bfg9000");
	public static final ResourceLocation SHOTGUN = new ResourceLocation(MODID, "shotgun");
	public static final ResourceLocation UNMAYKR = new ResourceLocation(MODID, "unmaykr");
	public static final ResourceLocation UNMAKER = new ResourceLocation(MODID, "unmaykr");
	public static final ResourceLocation BALLISTA = new ResourceLocation(MODID, "ballista");
	public static final ResourceLocation CHAINGUN = new ResourceLocation(MODID, "chaingun");
	public static final ResourceLocation CHAINSAW = new ResourceLocation(MODID, "chainsaw");
	public static final ResourceLocation CRUCIBLE = new ResourceLocation(MODID, "crucible");
	public static final ResourceLocation GUNS = new ResourceLocation(MODID, "crafting_guns");
	public static final ResourceLocation RELOAD_GUN = new ResourceLocation(MODID, "gun_reload");
	public static final ResourceLocation HEAVYCANNON = new ResourceLocation(MODID, "heavycannon");
	public static final ResourceLocation MARAUDERAXE = new ResourceLocation(MODID, "marauderaxe");
	public static final ResourceLocation SUPERSHOTGUN = new ResourceLocation(MODID, "supershotgun");
	public static final ResourceLocation GUN_TABLE_GUI = new ResourceLocation(MODID, "gun_table_gui");
	public static final ResourceLocation ROCKETLAUNCHER = new ResourceLocation(MODID, "rocketlauncher");
	public static final ResourceLocation SENTINELHAMMER = new ResourceLocation(MODID, "sentinelhammer");
	public static final ResourceLocation CHAINSAW_ETERNAL = new ResourceLocation(MODID, "chainsaweternal");
	public static final ResourceLocation DARKLORDCRUCIBLE = new ResourceLocation(MODID, "darklordcrucible");
	public static final ResourceLocation DSG = new ResourceLocation(MODID, "doomed_shotgun");
	public static final ResourceLocation DGAUSS = new ResourceLocation(MODID, "doomed_gauss");
	public static final ResourceLocation DPLASMARIFLE = new ResourceLocation(MODID, "doomed_plasma_rifle");
	public static final CreativeModeTab DoomEggItemGroup = FabricItemGroup.builder(new ResourceLocation(MODID, "eggs"))
			.icon(() -> new ItemStack(DoomItems.IMP_SPAWN_EGG))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(DoomItems.GORE_NEST_SPAWN_EGG);
				entries.accept(DoomItems.CUEBALL_SPAWN_EGG);
				entries.accept(DoomItems.TENTACLE_SPAWN_EGG);
				entries.accept(DoomItems.TURRET_SPAWN_EGG);
				entries.accept(DoomItems.CHAINGUNNER_SPAWN_EGG);
				entries.accept(DoomItems.GARGOYLE_SPAWN_EGG);
				entries.accept(DoomItems.IMP_SPAWN_EGG);
				entries.accept(DoomItems.STONEIMP_SPAWN_EGG);
				entries.accept(DoomItems.LOST_SOUL_SPAWN_EGG);
				entries.accept(DoomItems.LOST_SOUL_ETERNAL_SPAWN_EGG);
				entries.accept(DoomItems.MAYKR_DRONE_SPAWN_EGG);
				entries.accept(DoomItems.MECH_ZOMBIE_SPAWN_EGG);
				entries.accept(DoomItems.POSSESSED_SCIENTIST_SPAWN_EGG);
				entries.accept(DoomItems.POSSESSED_WORKER_SPAWN_EGG);
				entries.accept(DoomItems.POSSESSED_SOLDIER_SPAWN_EGG);
				entries.accept(DoomItems.SHOTGUNGUY_SPAWN_EGG);
				entries.accept(DoomItems.UNWILLING_SPAWN_EGG);
				entries.accept(DoomItems.ZOMBIEMAN_SPAWN_EGG);
				entries.accept(DoomItems.ARACHNOTRON_SPAWN_EGG);
				entries.accept(DoomItems.ARACHNOTRONETERNAL_SPAWN_EGG);
				entries.accept(DoomItems.BLOOD_MAYKR_SPAWN_EGG);
				entries.accept(DoomItems.CACODEMON_SPAWN_EGG);
				entries.accept(DoomItems.HELLKNIGHT_SPAWN_EGG);
				entries.accept(DoomItems.HELLKNIGHT2016_SPAWN_EGG);
				entries.accept(DoomItems.MANCUBUS_SPAWN_EGG);
				entries.accept(DoomItems.PAIN_SPAWN_EGG);
				entries.accept(DoomItems.PINKY_SPAWN_EGG);
				entries.accept(DoomItems.SPECTRE_SPAWN_EGG);
				entries.accept(DoomItems.PROWLER_SPAWN_EGG);
				entries.accept(DoomItems.REVENANT_SPAWN_EGG);
				entries.accept(DoomItems.REVENANT2016_SPAWN_EGG);
				entries.accept(DoomItems.WHIPLASH_SPAWN_EGG);
				entries.accept(DoomItems.ARCHVILE_SPAWN_EGG);
				entries.accept(DoomItems.BARON_SPAWN_EGG);
				entries.accept(DoomItems.BARON2016_SPAWN_EGG);
				entries.accept(DoomItems.FIREBORNE_BARON_SPAWN_EGG);
				entries.accept(DoomItems.ARMORED_BARON_SPAWN_EGG);
				entries.accept(DoomItems.CYBERDEMON_SPAWN_EGG);
				entries.accept(DoomItems.DOOMHUNTER_SPAWN_EGG);
				entries.accept(DoomItems.MARAUDER_SPAWN_EGG);
				entries.accept(DoomItems.SUMMONER_SPAWN_EGG);
				entries.accept(DoomItems.SPIDERMASTERMIND_SPAWN_EGG);
				entries.accept(DoomItems.SPIDERMASTERMIND2016_SPAWN_EGG);
				entries.accept(DoomItems.ICON_SPAWN_EGG);
				entries.accept(DoomItems.MOTHERDEMON_SPAWN_EGG);
				entries.accept(DoomItems.GLADIATOR_SPAWN_EGG);
				entries.accept(DoomItems.ARCH_MAKYR_SPAWN_EGG);
			}).build();
	public static final CreativeModeTab DoomArmorItemGroup = FabricItemGroup
			.builder(new ResourceLocation(MODID, "armor")).icon(() -> new ItemStack(DoomItems.DOOM_HELMET))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(DoomItems.DOOM_HELMET);
				entries.accept(DoomItems.DOOM_CHESTPLATE);
				entries.accept(DoomItems.DOOM_LEGGINGS);
				entries.accept(DoomItems.DOOM_BOOTS);
				entries.accept(DoomItems.PRAETOR_DOOM_HELMET);
				entries.accept(DoomItems.PRAETOR_DOOM_CHESTPLATE);
				entries.accept(DoomItems.PRAETOR_DOOM_LEGGINGS);
				entries.accept(DoomItems.PRAETOR_DOOM_BOOTS);
				entries.accept(DoomItems.ASTRO_DOOM_HELMET);
				entries.accept(DoomItems.ASTRO_DOOM_CHESTPLATE);
				entries.accept(DoomItems.ASTRO_DOOM_LEGGINGS);
				entries.accept(DoomItems.ASTRO_DOOM_BOOTS);
				entries.accept(DoomItems.CRIMSON_DOOM_HELMET);
				entries.accept(DoomItems.CRIMSON_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CRIMSON_DOOM_LEGGINGS);
				entries.accept(DoomItems.CRIMSON_DOOM_BOOTS);
				entries.accept(DoomItems.MIDNIGHT_DOOM_HELMET);
				entries.accept(DoomItems.MIDNIGHT_DOOM_CHESTPLATE);
				entries.accept(DoomItems.MIDNIGHT_DOOM_LEGGINGS);
				entries.accept(DoomItems.MIDNIGHT_DOOM_BOOTS);
				entries.accept(DoomItems.DEMONIC_DOOM_HELMET);
				entries.accept(DoomItems.DEMONIC_DOOM_CHESTPLATE);
				entries.accept(DoomItems.DEMONIC_DOOM_LEGGINGS);
				entries.accept(DoomItems.DEMONIC_DOOM_BOOTS);
				entries.accept(DoomItems.DEMONCIDE_DOOM_HELMET);
				entries.accept(DoomItems.DEMONCIDE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.DEMONCIDE_DOOM_LEGGINGS);
				entries.accept(DoomItems.DEMONCIDE_DOOM_BOOTS);
				entries.accept(DoomItems.SENTINEL_DOOM_HELMET);
				entries.accept(DoomItems.SENTINEL_DOOM_CHESTPLATE);
				entries.accept(DoomItems.SENTINEL_DOOM_LEGGINGS);
				entries.accept(DoomItems.SENTINEL_DOOM_BOOTS);
				entries.accept(DoomItems.EMBER_DOOM_HELMET);
				entries.accept(DoomItems.EMBER_DOOM_CHESTPLATE);
				entries.accept(DoomItems.EMBER_DOOM_LEGGINGS);
				entries.accept(DoomItems.EMBER_DOOM_BOOTS);
				entries.accept(DoomItems.ZOMBIE_DOOM_HELMET);
				entries.accept(DoomItems.ZOMBIE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.ZOMBIE_DOOM_LEGGINGS);
				entries.accept(DoomItems.ZOMBIE_DOOM_BOOTS);
				entries.accept(DoomItems.PHOBOS_DOOM_HELMET);
				entries.accept(DoomItems.PHOBOS_DOOM_CHESTPLATE);
				entries.accept(DoomItems.PHOBOS_DOOM_LEGGINGS);
				entries.accept(DoomItems.PHOBOS_DOOM_BOOTS);
				entries.accept(DoomItems.NIGHTMARE_DOOM_HELMET);
				entries.accept(DoomItems.NIGHTMARE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.NIGHTMARE_DOOM_LEGGINGS);
				entries.accept(DoomItems.NIGHTMARE_DOOM_BOOTS);
				entries.accept(DoomItems.PURPLEPONY_DOOM_HELMET);
				entries.accept(DoomItems.PURPLEPONY_DOOM_CHESTPLATE);
				entries.accept(DoomItems.PURPLEPONY_DOOM_LEGGINGS);
				entries.accept(DoomItems.PURPLEPONY_DOOM_BOOTS);
				entries.accept(DoomItems.DOOMICORN_DOOM_HELMET);
				entries.accept(DoomItems.DOOMICORN_DOOM_CHESTPLATE);
				entries.accept(DoomItems.DOOMICORN_DOOM_LEGGINGS);
				entries.accept(DoomItems.DOOMICORN_DOOM_BOOTS);
				entries.accept(DoomItems.GOLD_DOOM_HELMET);
				entries.accept(DoomItems.GOLD_DOOM_CHESTPLATE);
				entries.accept(DoomItems.GOLD_DOOM_LEGGINGS);
				entries.accept(DoomItems.GOLD_DOOM_BOOTS);
				entries.accept(DoomItems.TWENTY_FIVE_DOOM_HELMET);
				entries.accept(DoomItems.TWENTY_FIVE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.TWENTY_FIVE_DOOM_LEGGINGS);
				entries.accept(DoomItems.TWENTY_FIVE_DOOM_BOOTS);
				entries.accept(DoomItems.BRONZE_DOOM_HELMET);
				entries.accept(DoomItems.BRONZE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.BRONZE_DOOM_LEGGINGS);
				entries.accept(DoomItems.BRONZE_DOOM_BOOTS);
				entries.accept(DoomItems.CULTIST_DOOM_HELMET);
				entries.accept(DoomItems.CULTIST_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CULTIST_DOOM_LEGGINGS);
				entries.accept(DoomItems.CULTIST_DOOM_BOOTS);
				entries.accept(DoomItems.MAYKR_DOOM_HELMET);
				entries.accept(DoomItems.MAYKR_DOOM_CHESTPLATE);
				entries.accept(DoomItems.MAYKR_DOOM_LEGGINGS);
				entries.accept(DoomItems.MAYKR_DOOM_BOOTS);
				entries.accept(DoomItems.PAINTER_DOOM_HELMET);
				entries.accept(DoomItems.PAINTER_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CLASSIC_DOOM_HELMET);
				entries.accept(DoomItems.CLASSIC_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CLASSIC_DOOM_LEGGINGS);
				entries.accept(DoomItems.CLASSIC_RED_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CLASSIC_RED_DOOM_LEGGINGS);
				entries.accept(DoomItems.CLASSIC_INDIGO_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CLASSIC_INDIGO_DOOM_LEGGINGS);
				entries.accept(DoomItems.CLASSIC_BRONZE_DOOM_CHESTPLATE);
				entries.accept(DoomItems.CLASSIC_BRONZE_DOOM_LEGGINGS);
				entries.accept(DoomItems.CLASSIC_DOOM_BOOTS);
				entries.accept(DoomItems.MULLET_DOOM_HELMET1);
				entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE1);
				entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE2);
				entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE3);
				entries.accept(DoomItems.MULLET_DOOM_LEGGINGS1);
				entries.accept(DoomItems.MULLET_DOOM_BOOTS1);
				entries.accept(DoomItems.HOTROD_HELMET);
				entries.accept(DoomItems.HOTROD_CHESTPLATE);
				entries.accept(DoomItems.HOTROD_LEGGINGS);
				entries.accept(DoomItems.HOTROD_BOOTS);
				entries.accept(DoomItems.SANTA_HELMET);
				entries.accept(DoomItems.DARKLORD_HELMET);
				entries.accept(DoomItems.DARKLORD_CHESTPLATE);
				entries.accept(DoomItems.DARKLORD_LEGGINGS);
				entries.accept(DoomItems.DARKLORD_BOOTS);
			}).build();
	public static final CreativeModeTab DoomBlockItemGroup = FabricItemGroup
			.builder(new ResourceLocation(MODID, "blocks")).icon(() -> new ItemStack(DoomBlocks.BARREL_BLOCK))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(DoomBlocks.BARREL_BLOCK);
				entries.accept(DoomBlocks.JUMP_PAD);
				entries.accept(DoomBlocks.DOOM_SAND);
				entries.accept(DoomBlocks.ARGENT_BLOCK);
				entries.accept(DoomBlocks.ARGENT_LAMP_BLOCK);
				entries.accept(DoomBlocks.TOTEM);
				entries.accept(DoomBlocks.GUN_TABLE);
				entries.accept(DoomBlocks.E1M1_1);
				entries.accept(DoomBlocks.E1M1_2);
				entries.accept(DoomBlocks.E1M1_3);
				entries.accept(DoomBlocks.E1M1_4);
				entries.accept(DoomBlocks.E1M1_5);
				entries.accept(DoomBlocks.E1M1_6);
				entries.accept(DoomBlocks.E1M1_7);
				entries.accept(DoomBlocks.E1M1_8);
				entries.accept(DoomBlocks.E1M1_9);
				entries.accept(DoomBlocks.E1M1_10);
				entries.accept(DoomBlocks.E1M1_11);
				entries.accept(DoomBlocks.E1M1_12);
				entries.accept(DoomBlocks.E1M1_13);
				entries.accept(DoomBlocks.E1M1_14);
				entries.accept(DoomBlocks.E1M1_15);
				entries.accept(DoomBlocks.E1M1_16);
				entries.accept(DoomBlocks.E1M1_17);
				entries.accept(DoomBlocks.E1M1_18);
				entries.accept(DoomBlocks.E1M1_19);
				entries.accept(DoomBlocks.E1M1_20);
				entries.accept(DoomBlocks.E1M1_21);
				entries.accept(DoomBlocks.E1M1_22);
				entries.accept(DoomBlocks.E1M1_23);
				entries.accept(DoomBlocks.E1M1_24);
				entries.accept(DoomBlocks.E1M1_25);
				entries.accept(DoomBlocks.E1M1_26);
				entries.accept(DoomBlocks.ICON_WALL1);
				entries.accept(DoomBlocks.ICON_WALL2);
				entries.accept(DoomBlocks.ICON_WALL3);
				entries.accept(DoomBlocks.ICON_WALL4);
				entries.accept(DoomBlocks.ICON_WALL5);
				entries.accept(DoomBlocks.ICON_WALL6);
				entries.accept(DoomBlocks.ICON_WALL7);
				entries.accept(DoomBlocks.ICON_WALL8);
				entries.accept(DoomBlocks.ICON_WALL9);
				entries.accept(DoomBlocks.ICON_WALL10);
				entries.accept(DoomBlocks.ICON_WALL11);
				entries.accept(DoomBlocks.ICON_WALL12);
				entries.accept(DoomBlocks.ICON_WALL13);
				entries.accept(DoomBlocks.ICON_WALL14);
				entries.accept(DoomBlocks.ICON_WALL15);
				entries.accept(DoomBlocks.ICON_WALL16);
			}).build();
	public static final CreativeModeTab DoomWeaponItemGroup = FabricItemGroup
			.builder(new ResourceLocation(MODID, "weapons")).icon(() -> new ItemStack(DoomItems.BFG_ETERNAL))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(DoomItems.ARGENT_AXE);
				entries.accept(DoomItems.ARGENT_HOE);
				entries.accept(DoomItems.ARGENT_SHOVEL);
				entries.accept(DoomItems.ARGENT_PICKAXE);
				entries.accept(DoomItems.ARGENT_SWORD);
				entries.accept(DoomItems.ARGENT_PAXEL);
				entries.accept(DoomItems.CHAINSAW);
				entries.accept(DoomItems.CHAINSAW64);
				entries.accept(DoomItems.CHAINSAW_ETERNAL);
				entries.accept(DoomItems.PISTOL);
				entries.accept(DoomItems.HEAVYCANNON);
				entries.accept(DoomItems.CHAINGUN);
				entries.accept(DoomItems.SG);
				entries.accept(DoomItems.DSG);
				entries.accept(DoomItems.SSG);
				entries.accept(DoomItems.DPLASMARIFLE);
				entries.accept(DoomItems.PLASMAGUN);
				entries.accept(DoomItems.ROCKETLAUNCHER);
				entries.accept(DoomItems.DGAUSS);
				entries.accept(DoomItems.BALLISTA);
				entries.accept(DoomItems.UNMAKER);
				entries.accept(DoomItems.UNMAYKR);
				entries.accept(DoomItems.BFG);
				entries.accept(DoomItems.BFG_ETERNAL);
				entries.accept(DoomItems.SWORD_CLOSED);
				entries.accept(DoomItems.CRUCIBLESWORD);
				entries.accept(DoomItems.AXE_CLOSED);
				entries.accept(DoomItems.AXE_OPEN);
				entries.accept(DoomItems.DARKLORDCRUCIBLE);
				entries.accept(DoomItems.SENTINELHAMMER);
				entries.accept(DoomItems.GRENADE);
				entries.accept(DoomItems.GAS_BARREL);
				entries.accept(DoomItems.BULLETS);
				entries.accept(DoomItems.SHOTGUN_SHELLS);
				entries.accept(DoomItems.CHAINGUN_BULLETS);
				entries.accept(DoomItems.ROCKET);
				entries.accept(DoomItems.ARGENT_BOLT);
				entries.accept(DoomItems.ENERGY_CELLS);
				entries.accept(DoomItems.BFG_CELL);
			}).build();
	public static final CreativeModeTab DoomPowerUPItemGroup = FabricItemGroup
			.builder(new ResourceLocation(MODID, "powerup")).icon(() -> new ItemStack(DoomItems.SOULCUBE))
			.displayItems((enabledFeatures, entries, operatorEnabled) -> {
				entries.accept(DoomItems.ARGENT_ENERGY);
				entries.accept(DoomItems.ARGENT_PLATE);
				entries.accept(DoomItems.SOULCUBE);
				entries.accept(DoomItems.INMORTAL);
				entries.accept(DoomItems.INVISIBLE);
				entries.accept(DoomItems.MEGA);
				entries.accept(DoomItems.POWER);
				entries.accept(DoomItems.DAISY);
				entries.accept(DoomItems.E1M1_MUSIC_DISC);
				entries.accept(DoomItems.GEOF_MUSIC_DISC);
			}).build();
	public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(
			BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		DataTrackers.MEATHOOK_TRACKER.getId();
		DoomBlocks.init();
		MidnightConfig.init(MODID, DoomConfig.class);
		ITEMS = new DoomItems();
		SOUNDS = new DoomSounds();
		MOBS = new DoomEntities();
		PROJECTILES = new ProjectilesEntityRegister();
		FuelRegistry.INSTANCE.add(DoomItems.ARGENT_ENERGY, 32767);
		ICON = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":icon",
				FabricBlockEntityTypeBuilder.create(IconBlockEntity::new, DoomBlocks.ICON_WALL1).build(null));
		TOTEM = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":totem",
				FabricBlockEntityTypeBuilder.create(TotemEntity::new, DoomBlocks.TOTEM).build(null));
		GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":guntable",
				FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, DoomBlocks.GUN_TABLE).build(null));
		TICKING_LIGHT_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":lightblock",
				FabricBlockEntityTypeBuilder.create(TickingLightEntity::new, DoomBlocks.TICKING_LIGHT_BLOCK)
						.build(null));
		MobSpawn.addSpawnEntries();
		if (DoomConfig.enable_all_villager_trades) {
			ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> DoomVillagerTrades.addTrades());
		}
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (DoomLoot.BASTION_BRIDGE.equals(id) || DoomLoot.BASTION_HOGLIN_STABLE.equals(id)
					|| DoomLoot.BASTION_OTHER.equals(id) || DoomLoot.BASTION_TREASURE.equals(id)
					|| DoomLoot.NETHER_BRIDGE.equals(id) || DoomLoot.RUINED_PORTAL.equals(id)
					|| DoomLoot.SPAWN_BONUS_CHEST.equals(id)) {
				LootPool poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.with(LootItem.lootTableItem(DoomItems.INMORTAL).build())
						.with(LootItem.lootTableItem(DoomItems.INVISIBLE).build())
						.with(LootItem.lootTableItem(DoomItems.MEGA).build())
						.with(LootItem.lootTableItem(DoomItems.POWER).build())
						.with(LootItem.lootTableItem(DoomItems.SOULCUBE).build())
						.with(LootItem.lootTableItem(DoomItems.DAISY).build()).build();
				supplier.pool(poolBuilder);
			}
		});
		MobAttributes.init();
		AzureLib.initialize();
		PacketHandler.registerMessages();
		DoomStructures.registerStructureFeatures();
		SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new);
		Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MODID, "guntable_screen_type"),
				SCREEN_HANDLER_TYPE);
	}

	public static class DataTrackers {
		public static final EntityDataAccessor<Boolean> MEATHOOK_TRACKER = SynchedEntityData.defineId(Player.class,
				EntityDataSerializers.BOOLEAN);
	}

}
