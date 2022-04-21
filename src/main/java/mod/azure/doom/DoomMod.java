package mod.azure.doom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
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
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomStructures;
import mod.azure.doom.util.registry.MobSpawn;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3q.GeckoLib;

@SuppressWarnings("deprecation")
public class DoomMod implements ModInitializer {

	public static DoomItems ITEMS;
	public static DoomConfig config;
	public static ModSoundEvents SOUNDS;
	public static ModEntityTypes MOBS;
	public static final String MODID = "doom";
	public static BlockEntityType<TotemEntity> TOTEM;
	public static BlockEntityType<IconBlockEntity> ICON;
	public static ProjectilesEntityRegister PROJECTILES;
	public static RecipeType<GunTableRecipe> GUN_TABLE_RECIPE;
	public static final Logger LOGGER = LogManager.getLogger();
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
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE = ScreenHandlerRegistry
			.registerSimple(GUN_TABLE_GUI, GunTableScreenHandler::new);
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry
			.register(Registry.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize(ModContainer mod) {
		DataTrackers.MEATHOOK_TRACKER.getId();
		AutoConfig.register(DoomConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(DoomConfig.class).getConfig();
		DoomBlocks.init();
		ITEMS = new DoomItems();
		SOUNDS = new ModSoundEvents();
		MOBS = new ModEntityTypes();
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
		if (config.misc.enable_all_villager_trades) {
			ServerLifecycleEvents.READY.register(minecraftServer -> DoomVillagerTrades.addTrades());
		}
		MobAttributes.init();
		GeckoLib.initialize();
		PacketHandler.registerMessages();
		DoomStructures.setupAndRegisterStructureFeatures();
	}

	public static class DataTrackers {
		public static final TrackedData<Boolean> MEATHOOK_TRACKER = DataTracker.registerData(PlayerEntity.class,
				TrackedDataHandlerRegistry.BOOLEAN);
	}

}
