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
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

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
	public static final ItemGroup DoomEggItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "eggs"))
			.icon(() -> new ItemStack(DoomItems.IMP_SPAWN_EGG)).build();
	public static final ItemGroup DoomArmorItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "armor"))
			.icon(() -> new ItemStack(DoomItems.DOOM_HELMET)).build();
	public static final ItemGroup DoomBlockItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "blocks"))
			.icon(() -> new ItemStack(DoomBlocks.BARREL_BLOCK)).build();
	public static final ItemGroup DoomWeaponItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "weapons"))
			.icon(() -> new ItemStack(DoomItems.BFG_ETERNAL)).build();
	public static final ItemGroup DoomPowerUPItemGroup = FabricItemGroupBuilder.create(new Identifier(MODID, "powerup"))
			.icon(() -> new ItemStack(DoomItems.INMORTAL)).build();
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry
			.register(Registry.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

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
		ICON = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":icon",
				FabricBlockEntityTypeBuilder.create(IconBlockEntity::new, DoomBlocks.ICON_WALL1).build(null));
		TOTEM = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":totem",
				FabricBlockEntityTypeBuilder.create(TotemEntity::new, DoomBlocks.TOTEM).build(null));
		GUN_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":guntable",
				FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, DoomBlocks.GUN_TABLE).build(null));
		TICKING_LIGHT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":lightblock",
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
		Registry.register(Registry.SCREEN_HANDLER, new Identifier(MODID, "guntable_screen_type"), SCREEN_HANDLER_TYPE);
	}

	public static class DataTrackers {
		public static final TrackedData<Boolean> MEATHOOK_TRACKER = DataTracker.registerData(PlayerEntity.class,
				TrackedDataHandlerRegistry.BOOLEAN);
	}

}
