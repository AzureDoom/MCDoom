package mod.azure.doom;

import mod.azure.doom.client.gui.GunTableScreenHandler;
import mod.azure.doom.config.CustomMidnightConfig;
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
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;

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
	public static final Identifier BFG = new Identifier(MODID, "bfg");
	public static BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY;
	public static final Identifier PISTOL = new Identifier(MODID, "pistol");
	public static final Identifier PLASMA = new Identifier(MODID, "plamsa");
	public static final Identifier BFG9000 = new Identifier(MODID, "bfg9000");
	public static final Identifier SHOTGUN = new Identifier(MODID, "shotgun");
	public static final Identifier UNMAYKR = new Identifier(MODID, "unmaykr");
	public static final Identifier BALLISTA = new Identifier(MODID, "ballista");
	public static final Identifier CHAINGUN = new Identifier(MODID, "chaingun");
	public static final Identifier CHAINSAW = new Identifier(MODID, "chainsaw");
	public static final Identifier CRUCIBLE = new Identifier(MODID, "crucible");
	public static final Identifier GUNS = new Identifier(MODID, "crafting_guns");
	public static final Identifier RELOAD_GUN = new Identifier(MODID, "gun_reload");
	public static final Identifier HEAVYCANNON = new Identifier(MODID, "heavycannon");
	public static final Identifier MARAUDERAXE = new Identifier(MODID, "marauderaxe");
	public static final Identifier SUPERSHOTGUN = new Identifier(MODID, "supershotgun");
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");
	public static final Identifier SENTINELHAMMER = new Identifier(MODID, "sentinelhammer");
	public static final Identifier CHAINSAW_ETERNAL = new Identifier(MODID, "chainsaweternal");
	public static final Identifier DARKLORDCRUCIBLE = new Identifier(MODID, "darklordcrucible");
	public static final Identifier DSG = new Identifier(MODID, "doomed_shotgun");
	public static final Identifier DGAUSS = new Identifier(MODID, "doomed_gauss");
	public static final Identifier DPLASMARIFLE = new Identifier(MODID, "doomed_plasma_rifle");
	public static final ItemGroup DoomEggItemGroup = FabricItemGroup.builder(new Identifier(MODID, "eggs"))
			.icon(() -> new ItemStack(DoomItems.IMP_SPAWN_EGG)).entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(DoomItems.GORE_NEST_SPAWN_EGG);
				entries.add(DoomItems.CUEBALL_SPAWN_EGG);
				entries.add(DoomItems.TENTACLE_SPAWN_EGG);
				entries.add(DoomItems.TURRET_SPAWN_EGG);
				entries.add(DoomItems.CHAINGUNNER_SPAWN_EGG);
				entries.add(DoomItems.GARGOYLE_SPAWN_EGG);
				entries.add(DoomItems.IMP_SPAWN_EGG);
				entries.add(DoomItems.STONEIMP_SPAWN_EGG);
				entries.add(DoomItems.LOST_SOUL_SPAWN_EGG);
				entries.add(DoomItems.LOST_SOUL_ETERNAL_SPAWN_EGG);
				entries.add(DoomItems.MAYKR_DRONE_SPAWN_EGG);
				entries.add(DoomItems.MECH_ZOMBIE_SPAWN_EGG);
				entries.add(DoomItems.POSSESSED_SCIENTIST_SPAWN_EGG);
				entries.add(DoomItems.POSSESSED_WORKER_SPAWN_EGG);
				entries.add(DoomItems.POSSESSED_SOLDIER_SPAWN_EGG);
				entries.add(DoomItems.SHOTGUNGUY_SPAWN_EGG);
				entries.add(DoomItems.UNWILLING_SPAWN_EGG);
				entries.add(DoomItems.ZOMBIEMAN_SPAWN_EGG);
				entries.add(DoomItems.ARACHNOTRON_SPAWN_EGG);
				entries.add(DoomItems.ARACHNOTRONETERNAL_SPAWN_EGG);
				entries.add(DoomItems.BLOOD_MAYKR_SPAWN_EGG);
				entries.add(DoomItems.CACODEMON_SPAWN_EGG);
				entries.add(DoomItems.HELLKNIGHT_SPAWN_EGG);
				entries.add(DoomItems.HELLKNIGHT2016_SPAWN_EGG);
				entries.add(DoomItems.MANCUBUS_SPAWN_EGG);
				entries.add(DoomItems.PAIN_SPAWN_EGG);
				entries.add(DoomItems.PINKY_SPAWN_EGG);
				entries.add(DoomItems.SPECTRE_SPAWN_EGG);
				entries.add(DoomItems.PROWLER_SPAWN_EGG);
				entries.add(DoomItems.REVENANT_SPAWN_EGG);
				entries.add(DoomItems.REVENANT2016_SPAWN_EGG);
				entries.add(DoomItems.WHIPLASH_SPAWN_EGG);
				entries.add(DoomItems.ARCHVILE_SPAWN_EGG);
				entries.add(DoomItems.BARON_SPAWN_EGG);
				entries.add(DoomItems.BARON2016_SPAWN_EGG);
				entries.add(DoomItems.FIREBORNE_BARON_SPAWN_EGG);
				entries.add(DoomItems.ARMORED_BARON_SPAWN_EGG);
				entries.add(DoomItems.CYBERDEMON_SPAWN_EGG);
				entries.add(DoomItems.DOOMHUNTER_SPAWN_EGG);
				entries.add(DoomItems.MARAUDER_SPAWN_EGG);
				entries.add(DoomItems.SUMMONER_SPAWN_EGG);
				entries.add(DoomItems.SPIDERMASTERMIND_SPAWN_EGG);
				entries.add(DoomItems.SPIDERMASTERMIND2016_SPAWN_EGG);
				entries.add(DoomItems.ICON_SPAWN_EGG);
				entries.add(DoomItems.MOTHERDEMON_SPAWN_EGG);
				entries.add(DoomItems.GLADIATOR_SPAWN_EGG);
				entries.add(DoomItems.ARCH_MAKYR_SPAWN_EGG);
			}).build();
	public static final ItemGroup DoomArmorItemGroup = FabricItemGroup.builder(new Identifier(MODID, "armor"))
			.icon(() -> new ItemStack(DoomItems.DOOM_HELMET)).entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(DoomItems.DOOM_HELMET);
				entries.add(DoomItems.DOOM_CHESTPLATE);
				entries.add(DoomItems.DOOM_LEGGINGS);
				entries.add(DoomItems.DOOM_BOOTS);
				entries.add(DoomItems.PRAETOR_DOOM_HELMET);
				entries.add(DoomItems.PRAETOR_DOOM_CHESTPLATE);
				entries.add(DoomItems.PRAETOR_DOOM_LEGGINGS);
				entries.add(DoomItems.PRAETOR_DOOM_BOOTS );
				entries.add(DoomItems.ASTRO_DOOM_CHESTPLATE);
				entries.add(DoomItems.ASTRO_DOOM_LEGGINGS);
				entries.add(DoomItems.ASTRO_DOOM_BOOTS);
				entries.add(DoomItems.CRIMSON_DOOM_HELMET);
				entries.add(DoomItems.CRIMSON_DOOM_CHESTPLATE);
				entries.add(DoomItems.CRIMSON_DOOM_LEGGINGS);
				entries.add(DoomItems.CRIMSON_DOOM_BOOTS);
				entries.add(DoomItems.MIDNIGHT_DOOM_HELMET);
				entries.add(DoomItems.MIDNIGHT_DOOM_CHESTPLATE);
				entries.add(DoomItems.MIDNIGHT_DOOM_LEGGINGS);
				entries.add(DoomItems.MIDNIGHT_DOOM_BOOTS);
				entries.add(DoomItems.DEMONIC_DOOM_HELMET);
				entries.add(DoomItems.DEMONIC_DOOM_CHESTPLATE);
				entries.add(DoomItems.DEMONIC_DOOM_LEGGINGS);
				entries.add(DoomItems.DEMONIC_DOOM_BOOTS);
				entries.add(DoomItems.DEMONCIDE_DOOM_HELMET);
				entries.add(DoomItems.DEMONCIDE_DOOM_CHESTPLATE);
				entries.add(DoomItems.DEMONCIDE_DOOM_LEGGINGS);
				entries.add(DoomItems.DEMONCIDE_DOOM_BOOTS);
				entries.add(DoomItems.SENTINEL_DOOM_HELMET);
				entries.add(DoomItems.SENTINEL_DOOM_CHESTPLATE);
				entries.add(DoomItems.SENTINEL_DOOM_LEGGINGS);
				entries.add(DoomItems.SENTINEL_DOOM_BOOTS);
				entries.add(DoomItems.EMBER_DOOM_HELMET);
				entries.add(DoomItems.EMBER_DOOM_CHESTPLATE);
				entries.add(DoomItems.EMBER_DOOM_LEGGINGS);
				entries.add(DoomItems.EMBER_DOOM_BOOTS);
				entries.add(DoomItems.ZOMBIE_DOOM_HELMET);
				entries.add(DoomItems.ZOMBIE_DOOM_CHESTPLATE);
				entries.add(DoomItems.ZOMBIE_DOOM_LEGGINGS);
				entries.add(DoomItems.ZOMBIE_DOOM_BOOTS);
				entries.add(DoomItems.PHOBOS_DOOM_HELMET);
				entries.add(DoomItems.PHOBOS_DOOM_CHESTPLATE);
				entries.add(DoomItems.PHOBOS_DOOM_LEGGINGS);
				entries.add(DoomItems.PHOBOS_DOOM_BOOTS);
				entries.add(DoomItems.NIGHTMARE_DOOM_HELMET);
				entries.add(DoomItems.NIGHTMARE_DOOM_CHESTPLATE);
				entries.add(DoomItems.NIGHTMARE_DOOM_LEGGINGS);
				entries.add(DoomItems.NIGHTMARE_DOOM_BOOTS);
				entries.add(DoomItems.PURPLEPONY_DOOM_HELMET);
				entries.add(DoomItems.PURPLEPONY_DOOM_CHESTPLATE);
				entries.add(DoomItems.PURPLEPONY_DOOM_LEGGINGS);
				entries.add(DoomItems.PURPLEPONY_DOOM_BOOTS);
				entries.add(DoomItems.DOOMICORN_DOOM_HELMET);
				entries.add(DoomItems.DOOMICORN_DOOM_CHESTPLATE);
				entries.add(DoomItems.DOOMICORN_DOOM_LEGGINGS);
				entries.add(DoomItems.DOOMICORN_DOOM_BOOTS);
				entries.add(DoomItems.GOLD_DOOM_HELMET);
				entries.add(DoomItems.GOLD_DOOM_CHESTPLATE);
				entries.add(DoomItems.GOLD_DOOM_LEGGINGS);
				entries.add(DoomItems.GOLD_DOOM_BOOTS);
				entries.add(DoomItems.TWENTY_FIVE_DOOM_HELMET);
				entries.add(DoomItems.TWENTY_FIVE_DOOM_CHESTPLATE);
				entries.add(DoomItems.TWENTY_FIVE_DOOM_LEGGINGS);
				entries.add(DoomItems.TWENTY_FIVE_DOOM_BOOTS);
				entries.add(DoomItems.BRONZE_DOOM_HELMET);
				entries.add(DoomItems.BRONZE_DOOM_CHESTPLATE);
				entries.add(DoomItems.BRONZE_DOOM_LEGGINGS);
				entries.add(DoomItems.BRONZE_DOOM_BOOTS);
				entries.add(DoomItems.CULTIST_DOOM_HELMET);
				entries.add(DoomItems.CULTIST_DOOM_CHESTPLATE);
				entries.add(DoomItems.CULTIST_DOOM_LEGGINGS);
				entries.add(DoomItems.CULTIST_DOOM_BOOTS);
				entries.add(DoomItems.MAYKR_DOOM_HELMET);
				entries.add(DoomItems.MAYKR_DOOM_CHESTPLATE);
				entries.add(DoomItems.MAYKR_DOOM_LEGGINGS);
				entries.add(DoomItems.MAYKR_DOOM_BOOTS);
				entries.add(DoomItems.PAINTER_DOOM_HELMET);
				entries.add(DoomItems.PAINTER_DOOM_CHESTPLATE);
				entries.add(DoomItems.CLASSIC_DOOM_HELMET);
				entries.add(DoomItems.CLASSIC_DOOM_CHESTPLATE);
				entries.add(DoomItems.CLASSIC_DOOM_LEGGINGS);
				entries.add(DoomItems.CLASSIC_RED_DOOM_CHESTPLATE);
				entries.add(DoomItems.CLASSIC_RED_DOOM_LEGGINGS);
				entries.add(DoomItems.CLASSIC_INDIGO_DOOM_CHESTPLATE);
				entries.add(DoomItems.CLASSIC_INDIGO_DOOM_LEGGINGS);
				entries.add(DoomItems.CLASSIC_BRONZE_DOOM_CHESTPLATE);
				entries.add(DoomItems.CLASSIC_BRONZE_DOOM_LEGGINGS);
				entries.add(DoomItems.CLASSIC_DOOM_BOOTS);
				entries.add(DoomItems.MULLET_DOOM_HELMET1);
				entries.add(DoomItems.MULLET_DOOM_CHESTPLATE1);
				entries.add(DoomItems.MULLET_DOOM_CHESTPLATE2);
				entries.add(DoomItems.MULLET_DOOM_CHESTPLATE3);
				entries.add(DoomItems.MULLET_DOOM_LEGGINGS1);
				entries.add(DoomItems.MULLET_DOOM_BOOTS1);
				entries.add(DoomItems.HOTROD_HELMET);
				entries.add(DoomItems.HOTROD_CHESTPLATE);
				entries.add(DoomItems.HOTROD_LEGGINGS);
				entries.add(DoomItems.HOTROD_BOOTS);
				entries.add(DoomItems.SANTA_HELMET);
				entries.add(DoomItems.DARKLORD_HELMET);
				entries.add(DoomItems.DARKLORD_CHESTPLATE);
				entries.add(DoomItems.DARKLORD_LEGGINGS);
				entries.add(DoomItems.DARKLORD_BOOTS);
			}).build();
	public static final ItemGroup DoomBlockItemGroup = FabricItemGroup.builder(new Identifier(MODID, "blocks"))
			.icon(() -> new ItemStack(DoomBlocks.BARREL_BLOCK)).entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(DoomBlocks.BARREL_BLOCK);
				entries.add(DoomBlocks.JUMP_PAD);
				entries.add(DoomBlocks.DOOM_SAND);
				entries.add(DoomBlocks.ARGENT_BLOCK);
				entries.add(DoomBlocks.ARGENT_LAMP_BLOCK);
				entries.add(DoomBlocks.TOTEM);
				entries.add(DoomBlocks.GUN_TABLE);
				entries.add(DoomBlocks.E1M1_1);
				entries.add(DoomBlocks.E1M1_2);
				entries.add(DoomBlocks.E1M1_3);
				entries.add(DoomBlocks.E1M1_4);
				entries.add(DoomBlocks.E1M1_5);
				entries.add(DoomBlocks.E1M1_6);
				entries.add(DoomBlocks.E1M1_7);
				entries.add(DoomBlocks.E1M1_8);
				entries.add(DoomBlocks.E1M1_9);
				entries.add(DoomBlocks.E1M1_10);
				entries.add(DoomBlocks.E1M1_11);
				entries.add(DoomBlocks.E1M1_12);
				entries.add(DoomBlocks.E1M1_13);
				entries.add(DoomBlocks.E1M1_14);
				entries.add(DoomBlocks.E1M1_15);
				entries.add(DoomBlocks.E1M1_16);
				entries.add(DoomBlocks.E1M1_17);
				entries.add(DoomBlocks.E1M1_18);
				entries.add(DoomBlocks.E1M1_19);
				entries.add(DoomBlocks.E1M1_20);
				entries.add(DoomBlocks.E1M1_21);
				entries.add(DoomBlocks.E1M1_22);
				entries.add(DoomBlocks.E1M1_23);
				entries.add(DoomBlocks.E1M1_24);
				entries.add(DoomBlocks.E1M1_25);
				entries.add(DoomBlocks.E1M1_26);
				entries.add(DoomBlocks.ICON_WALL1);
				entries.add(DoomBlocks.ICON_WALL2);
				entries.add(DoomBlocks.ICON_WALL3);
				entries.add(DoomBlocks.ICON_WALL4);
				entries.add(DoomBlocks.ICON_WALL5);
				entries.add(DoomBlocks.ICON_WALL6);
				entries.add(DoomBlocks.ICON_WALL7);
				entries.add(DoomBlocks.ICON_WALL8);
				entries.add(DoomBlocks.ICON_WALL9);
				entries.add(DoomBlocks.ICON_WALL10);
				entries.add(DoomBlocks.ICON_WALL11);
				entries.add(DoomBlocks.ICON_WALL12);
				entries.add(DoomBlocks.ICON_WALL13);
				entries.add(DoomBlocks.ICON_WALL14);
				entries.add(DoomBlocks.ICON_WALL15);
				entries.add(DoomBlocks.ICON_WALL16);
			}).build();
	public static final ItemGroup DoomWeaponItemGroup = FabricItemGroup.builder(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(DoomItems.BFG_ETERNAL)).entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(DoomItems.ARGENT_AXE);
				entries.add(DoomItems.ARGENT_HOE);
				entries.add(DoomItems.ARGENT_SHOVEL);
				entries.add(DoomItems.ARGENT_PICKAXE);
				entries.add(DoomItems.ARGENT_SWORD);
				entries.add(DoomItems.ARGENT_PAXEL);
				entries.add(DoomItems.CHAINSAW);
				entries.add(DoomItems.CHAINSAW64);
				entries.add(DoomItems.CHAINSAW_ETERNAL);
				entries.add(DoomItems.PISTOL);
				entries.add(DoomItems.HEAVYCANNON);
				entries.add(DoomItems.CHAINGUN);
				entries.add(DoomItems.SG);
				entries.add(DoomItems.DSG);
				entries.add(DoomItems.SSG);
				entries.add(DoomItems.DPLASMARIFLE);
				entries.add(DoomItems.PLASMAGUN);
				entries.add(DoomItems.ROCKETLAUNCHER);
				entries.add(DoomItems.DGAUSS);
				entries.add(DoomItems.BALLISTA);
				entries.add(DoomItems.UNMAKER);
				entries.add(DoomItems.UNMAYKR);
				entries.add(DoomItems.BFG);
				entries.add(DoomItems.BFG_ETERNAL);
				entries.add(DoomItems.SWORD_CLOSED);
				entries.add(DoomItems.CRUCIBLESWORD);
				entries.add(DoomItems.AXE_CLOSED);
				entries.add(DoomItems.AXE_OPEN);
				entries.add(DoomItems.DARKLORDCRUCIBLE);
				entries.add(DoomItems.SENTINELHAMMER);
				entries.add(DoomItems.GRENADE);
				entries.add(DoomItems.GAS_BARREL);
				entries.add(DoomItems.BULLETS);
				entries.add(DoomItems.SHOTGUN_SHELLS);
				entries.add(DoomItems.CHAINGUN_BULLETS);
				entries.add(DoomItems.ROCKET);
				entries.add(DoomItems.ARGENT_BOLT);
				entries.add(DoomItems.ENERGY_CELLS);
				entries.add(DoomItems.BFG_CELL);
			}).build();
	public static final ItemGroup DoomPowerUPItemGroup = FabricItemGroup.builder(new Identifier(MODID, "powerup"))
			.icon(() -> new ItemStack(DoomItems.SOULCUBE)).entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(DoomItems.ARGENT_ENERGY);
				entries.add(DoomItems.ARGENT_PLATE);
				entries.add(DoomItems.SOULCUBE);
				entries.add(DoomItems.INMORTAL);
				entries.add(DoomItems.INVISIBLE);
				entries.add(DoomItems.MEGA);
				entries.add(DoomItems.POWER);
				entries.add(DoomItems.DAISY);
				entries.add(DoomItems.E1M1_MUSIC_DISC);
				entries.add(DoomItems.GEOF_MUSIC_DISC);
			}).build();
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(
			Registries.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		DataTrackers.MEATHOOK_TRACKER.getId();
		DoomBlocks.init();
		CustomMidnightConfig.init(MODID, DoomConfig.class);
		ITEMS = new DoomItems();
		SOUNDS = new DoomSounds();
		MOBS = new DoomEntities();
		PROJECTILES = new ProjectilesEntityRegister();
		FuelRegistry.INSTANCE.add(DoomItems.ARGENT_ENERGY, 32767);
		ICON = Registry.register(Registries.BLOCK_ENTITY_TYPE, MODID + ":icon",
				FabricBlockEntityTypeBuilder.create(IconBlockEntity::new, DoomBlocks.ICON_WALL1).build(null));
		TOTEM = Registry.register(Registries.BLOCK_ENTITY_TYPE, MODID + ":totem",
				FabricBlockEntityTypeBuilder.create(TotemEntity::new, DoomBlocks.TOTEM).build(null));
		GUN_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, MODID + ":guntable",
				FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, DoomBlocks.GUN_TABLE).build(null));
		TICKING_LIGHT_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, MODID + ":lightblock",
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
				LootPool poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
						.with(ItemEntry.builder(DoomItems.INMORTAL).build())
						.with(ItemEntry.builder(DoomItems.INVISIBLE).build())
						.with(ItemEntry.builder(DoomItems.MEGA).build())
						.with(ItemEntry.builder(DoomItems.POWER).build())
						.with(ItemEntry.builder(DoomItems.SOULCUBE).build())
						.with(ItemEntry.builder(DoomItems.DAISY).build()).build();
				supplier.pool(poolBuilder);
			}
		});
		MobAttributes.init();
		GeckoLib.initialize();
		PacketHandler.registerMessages();
		DoomStructures.registerStructureFeatures();
		SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(GunTableScreenHandler::new);
		Registry.register(Registries.SCREEN_HANDLER, new Identifier(MODID, "guntable_screen_type"),
				SCREEN_HANDLER_TYPE);
	}

	public static class DataTrackers {
		public static final TrackedData<Boolean> MEATHOOK_TRACKER = DataTracker.registerData(PlayerEntity.class,
				TrackedDataHandlerRegistry.BOOLEAN);
	}

}
