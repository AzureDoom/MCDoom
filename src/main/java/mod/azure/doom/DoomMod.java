package mod.azure.doom;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.recipes.GunTableRecipe;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class DoomMod implements ModInitializer {

	public static final String MODID = "doom";
	public static RecipeType<GunTableRecipe> GUN_TABLE_RECIPE;
	public static final ResourceLocation BFG = new ResourceLocation(MODID, "bfg");
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
	public static final CreativeModeTab DoomEggItemGroup = FabricItemGroupBuilder.build(new ResourceLocation(MODID, "eggs"), () -> new ItemStack(DoomItems.IMP_SPAWN_EGG));
	public static final CreativeModeTab DoomArmorItemGroup = FabricItemGroupBuilder.build(new ResourceLocation(MODID, "armor"), () -> new ItemStack(DoomItems.DOOM_HELMET));
	public static final CreativeModeTab DoomBlockItemGroup = FabricItemGroupBuilder.build(new ResourceLocation(MODID, "blocks"), () -> new ItemStack(DoomBlocks.BARREL_BLOCK));
	public static final CreativeModeTab DoomPowerUPItemGroup = FabricItemGroupBuilder.build(new ResourceLocation(MODID, "powerup"), () -> new ItemStack(DoomItems.SOULCUBE));
	public static final CreativeModeTab DoomWeaponItemGroup = FabricItemGroupBuilder.build(new ResourceLocation(MODID, "weapons"), () -> new ItemStack(DoomItems.BFG_ETERNAL));
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		ModRegistry.initialize();
		DataTrackers.MEATHOOK_TRACKER.getId();
		MidnightConfig.init(MODID, DoomConfig.class);
		FuelRegistry.INSTANCE.add(DoomItems.ARGENT_ENERGY, 32767);
	}

	public static class DataTrackers {
		public static final EntityDataAccessor<Boolean> MEATHOOK_TRACKER = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
	}

	public static final ResourceLocation modResource(String name) {
		return new ResourceLocation(MODID, name);
	}
}
