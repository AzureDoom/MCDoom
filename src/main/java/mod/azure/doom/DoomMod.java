package mod.azure.doom;

import java.util.List;

import mod.azure.azurelib.AzureLib;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.LootHandler;
import mod.azure.doom.util.SoulCubeHandler;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.DoomRecipes;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.DoomStructures;
import mod.azure.doom.util.registry.ModEntitySpawn;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(DoomMod.MODID)
public class DoomMod {

	public static DoomMod instance;
	public static final String MODID = "doom";
	public static final TagKey<Block> ARGENT_TAG = BlockTags.create(new ResourceLocation(MODID, "needs_argent_tool"));
	public static final Tier ARGENT_TIER = TierSortingRegistry.registerTier(
			new ForgeTier(17, 5000, 18, 3.0F, 30, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())),
			new ResourceLocation(MODID, "argent"), List.of(Tiers.NETHERITE), List.of());
	public static final Tier DOOM_HIGHTEIR = TierSortingRegistry.registerTier(
			new ForgeTier(17, 0, 30, -1.9F, 0, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())),
			new ResourceLocation(MODID, "doom_highertier"), List.of(Tiers.NETHERITE), List.of());

	public DoomMod() {
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoomConfig.SERVER_SPEC, "doom-newconfig.toml");
		DoomConfig.loadConfig(DoomConfig.SERVER_SPEC,
				FMLPaths.CONFIGDIR.get().resolve("doom-newconfig.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
		if (DoomConfig.SERVER.enable_soulcube_effects.get()) {
			MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
		}
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		if (DoomConfig.SERVER.enable_all_villager_trades.get()) {
			MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
		}
		DoomSounds.MOD_SOUNDS.register(modEventBus);
		DoomEntities.ENTITY_TYPES.register(modEventBus);
		DoomItems.ITEMS.register(modEventBus);
		DoomBlocks.BLOCKS.register(modEventBus);
		DoomEntities.TILE_TYPES.register(modEventBus);
		ProjectilesEntityRegister.ENTITY_TYPES.register(modEventBus);
		DoomScreens.CONTAIN.register(modEventBus);
		DoomRecipes.SERIAL.register(modEventBus);
		DoomParticles.PARTICLES.register(modEventBus);
		DoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		ModEntitySpawn.SERIALIZER.register(modEventBus);
		AzureLib.initialize();
		modEventBus.addListener(this::addCreativeTabs);
	}

	private void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new LootHandler());
		DoomPacketHandler.register();
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.MODIFY_TYPE,
				() -> SlotTypePreset.CHARM.getMessageBuilder().build());
		InterModComms.sendTo("curios", SlotTypeMessage.MODIFY_TYPE,
				() -> SlotTypePreset.BELT.getMessageBuilder().build());
	}

	public void addCreativeTabs(final CreativeModeTabEvent.Register event) {
		event.registerCreativeModeTab(new ResourceLocation(MODID, "eggs"),
				e -> e.icon(() -> new ItemStack(DoomItems.IMP_SPAWN_EGG.get()))
						.title(Component.translatable("itemGroup." + MODID + ".eggs"))
						.displayItems((enabledFeatures, entries, operatorEnabled) -> {
							entries.accept(DoomItems.GORE_NEST_SPAWN_EGG.get());
							entries.accept(DoomItems.CUEBALL_SPAWN_EGG.get());
							entries.accept(DoomItems.TENTACLE_SPAWN_EGG.get());
							entries.accept(DoomItems.TURRET_SPAWN_EGG.get());
							entries.accept(DoomItems.CHAINGUNNER_SPAWN_EGG.get());
							entries.accept(DoomItems.GARGOYLE_SPAWN_EGG.get());
							entries.accept(DoomItems.IMP_SPAWN_EGG.get());
							entries.accept(DoomItems.STONEIMP_SPAWN_EGG.get());
							entries.accept(DoomItems.LOST_SOUL_SPAWN_EGG.get());
							entries.accept(DoomItems.LOST_SOUL_ETERNAL_SPAWN_EGG.get());
							entries.accept(DoomItems.MAYKR_DRONE_SPAWN_EGG.get());
							entries.accept(DoomItems.MECH_ZOMBIE_SPAWN_EGG.get());
							entries.accept(DoomItems.POSSESSED_SCIENTIST_SPAWN_EGG.get());
							entries.accept(DoomItems.POSSESSED_WORKER_SPAWN_EGG.get());
							entries.accept(DoomItems.POSSESSED_SOLDIER_SPAWN_EGG.get());
							entries.accept(DoomItems.SHOTGUNGUY_SPAWN_EGG.get());
							entries.accept(DoomItems.UNWILLING_SPAWN_EGG.get());
							entries.accept(DoomItems.ZOMBIEMAN_SPAWN_EGG.get());
							entries.accept(DoomItems.ARACHNOTRON_SPAWN_EGG.get());
							entries.accept(DoomItems.ARACHNOTRONETERNAL_SPAWN_EGG.get());
							entries.accept(DoomItems.BLOOD_MAYKR_SPAWN_EGG.get());
							entries.accept(DoomItems.CACODEMON_SPAWN_EGG.get());
							entries.accept(DoomItems.HELLKNIGHT_SPAWN_EGG.get());
							entries.accept(DoomItems.HELLKNIGHT2016_SPAWN_EGG.get());
							entries.accept(DoomItems.MANCUBUS_SPAWN_EGG.get());
							entries.accept(DoomItems.PAIN_SPAWN_EGG.get());
							entries.accept(DoomItems.PINKY_SPAWN_EGG.get());
							entries.accept(DoomItems.SPECTRE_SPAWN_EGG.get());
							entries.accept(DoomItems.PROWLER_SPAWN_EGG.get());
							entries.accept(DoomItems.REVENANT_SPAWN_EGG.get());
							entries.accept(DoomItems.REVENANT2016_SPAWN_EGG.get());
							entries.accept(DoomItems.WHIPLASH_SPAWN_EGG.get());
							entries.accept(DoomItems.ARCHVILE_SPAWN_EGG.get());
							entries.accept(DoomItems.BARON_SPAWN_EGG.get());
							entries.accept(DoomItems.BARON2016_SPAWN_EGG.get());
							entries.accept(DoomItems.FIREBORNE_BARON_SPAWN_EGG.get());
							entries.accept(DoomItems.ARMORED_BARON_SPAWN_EGG.get());
							entries.accept(DoomItems.CYBERDEMON_SPAWN_EGG.get());
							entries.accept(DoomItems.DOOMHUNTER_SPAWN_EGG.get());
							entries.accept(DoomItems.MARAUDER_SPAWN_EGG.get());
							entries.accept(DoomItems.SUMMONER_SPAWN_EGG.get());
							entries.accept(DoomItems.SPIDERMASTERMIND_SPAWN_EGG.get());
							entries.accept(DoomItems.SPIDERMASTERMIND2016_SPAWN_EGG.get());
							entries.accept(DoomItems.ICON_SPAWN_EGG.get());
							entries.accept(DoomItems.MOTHERDEMON_SPAWN_EGG.get());
							entries.accept(DoomItems.GLADIATOR_SPAWN_EGG.get());
							entries.accept(DoomItems.ARCH_MAKYR_SPAWN_EGG.get());
						}));
		event.registerCreativeModeTab(new ResourceLocation(MODID, "armor"),
				e -> e.icon(() -> new ItemStack(DoomItems.DOOM_HELMET.get()))
				.title(Component.translatable("itemGroup." + MODID + ".armor"))
						.displayItems((enabledFeatures, entries, operatorEnabled) -> {
							entries.accept(DoomItems.DOOM_HELMET.get());
							entries.accept(DoomItems.DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.DOOM_LEGGINGS.get());
							entries.accept(DoomItems.DOOM_BOOTS.get());
							entries.accept(DoomItems.PRAETOR_DOOM_HELMET.get());
							entries.accept(DoomItems.PRAETOR_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.PRAETOR_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.PRAETOR_DOOM_BOOTS.get());
							entries.accept(DoomItems.ASTRO_DOOM_HELMET.get());
							entries.accept(DoomItems.ASTRO_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.ASTRO_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.ASTRO_DOOM_BOOTS.get());
							entries.accept(DoomItems.CRIMSON_DOOM_HELMET.get());
							entries.accept(DoomItems.CRIMSON_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CRIMSON_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CRIMSON_DOOM_BOOTS.get());
							entries.accept(DoomItems.MIDNIGHT_DOOM_HELMET.get());
							entries.accept(DoomItems.MIDNIGHT_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.MIDNIGHT_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.MIDNIGHT_DOOM_BOOTS.get());
							entries.accept(DoomItems.DEMONIC_DOOM_HELMET.get());
							entries.accept(DoomItems.DEMONIC_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.DEMONIC_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.DEMONIC_DOOM_BOOTS.get());
							entries.accept(DoomItems.DEMONCIDE_DOOM_HELMET.get());
							entries.accept(DoomItems.DEMONCIDE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.DEMONCIDE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.DEMONCIDE_DOOM_BOOTS.get());
							entries.accept(DoomItems.SENTINEL_DOOM_HELMET.get());
							entries.accept(DoomItems.SENTINEL_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.SENTINEL_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.SENTINEL_DOOM_BOOTS.get());
							entries.accept(DoomItems.EMBER_DOOM_HELMET.get());
							entries.accept(DoomItems.EMBER_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.EMBER_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.EMBER_DOOM_BOOTS.get());
							entries.accept(DoomItems.ZOMBIE_DOOM_HELMET.get());
							entries.accept(DoomItems.ZOMBIE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.ZOMBIE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.ZOMBIE_DOOM_BOOTS.get());
							entries.accept(DoomItems.PHOBOS_DOOM_HELMET.get());
							entries.accept(DoomItems.PHOBOS_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.PHOBOS_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.PHOBOS_DOOM_BOOTS.get());
							entries.accept(DoomItems.NIGHTMARE_DOOM_HELMET.get());
							entries.accept(DoomItems.NIGHTMARE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.NIGHTMARE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.NIGHTMARE_DOOM_BOOTS.get());
							entries.accept(DoomItems.PURPLEPONY_DOOM_HELMET.get());
							entries.accept(DoomItems.PURPLEPONY_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.PURPLEPONY_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.PURPLEPONY_DOOM_BOOTS.get());
							entries.accept(DoomItems.DOOMICORN_DOOM_HELMET.get());
							entries.accept(DoomItems.DOOMICORN_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.DOOMICORN_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.DOOMICORN_DOOM_BOOTS.get());
							entries.accept(DoomItems.GOLD_DOOM_HELMET.get());
							entries.accept(DoomItems.GOLD_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.GOLD_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.GOLD_DOOM_BOOTS.get());
							entries.accept(DoomItems.TWENTY_FIVE_DOOM_HELMET.get());
							entries.accept(DoomItems.TWENTY_FIVE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.TWENTY_FIVE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.TWENTY_FIVE_DOOM_BOOTS.get());
							entries.accept(DoomItems.BRONZE_DOOM_HELMET.get());
							entries.accept(DoomItems.BRONZE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.BRONZE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.BRONZE_DOOM_BOOTS.get());
							entries.accept(DoomItems.CULTIST_DOOM_HELMET.get());
							entries.accept(DoomItems.CULTIST_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CULTIST_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CULTIST_DOOM_BOOTS.get());
							entries.accept(DoomItems.MAYKR_DOOM_HELMET.get());
							entries.accept(DoomItems.MAYKR_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.MAYKR_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.MAYKR_DOOM_BOOTS.get());
							entries.accept(DoomItems.PAINTER_DOOM_HELMET.get());
							entries.accept(DoomItems.PAINTER_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CLASSIC_DOOM_HELMET.get());
							entries.accept(DoomItems.CLASSIC_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CLASSIC_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CLASSIC_RED_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CLASSIC_RED_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CLASSIC_INDIGO_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CLASSIC_INDIGO_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CLASSIC_BRONZE_DOOM_CHESTPLATE.get());
							entries.accept(DoomItems.CLASSIC_BRONZE_DOOM_LEGGINGS.get());
							entries.accept(DoomItems.CLASSIC_DOOM_BOOTS.get());
							entries.accept(DoomItems.MULLET_DOOM_HELMET1.get());
							entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE1.get());
							entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE2.get());
							entries.accept(DoomItems.MULLET_DOOM_CHESTPLATE3.get());
							entries.accept(DoomItems.MULLET_DOOM_LEGGINGS1.get());
							entries.accept(DoomItems.MULLET_DOOM_BOOTS1.get());
							entries.accept(DoomItems.HOTROD_HELMET.get());
							entries.accept(DoomItems.HOTROD_CHESTPLATE.get());
							entries.accept(DoomItems.HOTROD_LEGGINGS.get());
							entries.accept(DoomItems.HOTROD_BOOTS.get());
							entries.accept(DoomItems.SANTA_HELMET.get());
							entries.accept(DoomItems.DARKLORD_HELMET.get());
							entries.accept(DoomItems.DARKLORD_CHESTPLATE.get());
							entries.accept(DoomItems.DARKLORD_LEGGINGS.get());
							entries.accept(DoomItems.DARKLORD_BOOTS.get());
						}));
		event.registerCreativeModeTab(new ResourceLocation(MODID, "blocks"),
				e -> e.icon(() -> new ItemStack(DoomBlocks.BARREL_BLOCK.get()))
				.title(Component.translatable("itemGroup." + MODID + ".blocks"))
						.displayItems((enabledFeatures, entries, operatorEnabled) -> {
							entries.accept(DoomBlocks.BARREL_BLOCK.get());
							entries.accept(DoomBlocks.JUMP_PAD.get());
							entries.accept(DoomBlocks.DOOM_SAND.get());
							entries.accept(DoomBlocks.ARGENT_BLOCK.get());
							entries.accept(DoomBlocks.ARGENT_LAMP_BLOCK.get());
							entries.accept(DoomBlocks.TOTEM.get());
							entries.accept(DoomBlocks.GUN_TABLE.get());
							entries.accept(DoomBlocks.E1M1_1.get());
							entries.accept(DoomBlocks.E1M1_2.get());
							entries.accept(DoomBlocks.E1M1_3.get());
							entries.accept(DoomBlocks.E1M1_4.get());
							entries.accept(DoomBlocks.E1M1_5.get());
							entries.accept(DoomBlocks.E1M1_6.get());
							entries.accept(DoomBlocks.E1M1_7.get());
							entries.accept(DoomBlocks.E1M1_8.get());
							entries.accept(DoomBlocks.E1M1_9.get());
							entries.accept(DoomBlocks.E1M1_10.get());
							entries.accept(DoomBlocks.E1M1_11.get());
							entries.accept(DoomBlocks.E1M1_12.get());
							entries.accept(DoomBlocks.E1M1_13.get());
							entries.accept(DoomBlocks.E1M1_14.get());
							entries.accept(DoomBlocks.E1M1_15.get());
							entries.accept(DoomBlocks.E1M1_16.get());
							entries.accept(DoomBlocks.E1M1_17.get());
							entries.accept(DoomBlocks.E1M1_18.get());
							entries.accept(DoomBlocks.E1M1_19.get());
							entries.accept(DoomBlocks.E1M1_20.get());
							entries.accept(DoomBlocks.E1M1_21.get());
							entries.accept(DoomBlocks.E1M1_22.get());
							entries.accept(DoomBlocks.E1M1_23.get());
							entries.accept(DoomBlocks.E1M1_24.get());
							entries.accept(DoomBlocks.E1M1_25.get());
							entries.accept(DoomBlocks.E1M1_26.get());
							entries.accept(DoomBlocks.ICON_WALL1.get());
							entries.accept(DoomBlocks.ICON_WALL2.get());
							entries.accept(DoomBlocks.ICON_WALL3.get());
							entries.accept(DoomBlocks.ICON_WALL4.get());
							entries.accept(DoomBlocks.ICON_WALL5.get());
							entries.accept(DoomBlocks.ICON_WALL6.get());
							entries.accept(DoomBlocks.ICON_WALL7.get());
							entries.accept(DoomBlocks.ICON_WALL8.get());
							entries.accept(DoomBlocks.ICON_WALL9.get());
							entries.accept(DoomBlocks.ICON_WALL10.get());
							entries.accept(DoomBlocks.ICON_WALL11.get());
							entries.accept(DoomBlocks.ICON_WALL12.get());
							entries.accept(DoomBlocks.ICON_WALL13.get());
							entries.accept(DoomBlocks.ICON_WALL14.get());
							entries.accept(DoomBlocks.ICON_WALL15.get());
							entries.accept(DoomBlocks.ICON_WALL16.get());
						}));
		event.registerCreativeModeTab(new ResourceLocation(MODID, "weapons"),
				e -> e.icon(() -> new ItemStack(DoomItems.BFG_ETERNAL.get()))
				.title(Component.translatable("itemGroup." + MODID + ".eggs"))
						.displayItems((enabledFeatures, entries, operatorEnabled) -> {
							entries.accept(DoomItems.ARGENT_AXE.get());
							entries.accept(DoomItems.ARGENT_HOE.get());
							entries.accept(DoomItems.ARGENT_SHOVEL.get());
							entries.accept(DoomItems.ARGENT_PICKAXE.get());
							entries.accept(DoomItems.ARGENT_SWORD.get());
							entries.accept(DoomItems.ARGENT_PAXEL.get());
							entries.accept(DoomItems.CHAINSAW.get());
							entries.accept(DoomItems.CHAINSAW64.get());
							entries.accept(DoomItems.CHAINSAW_ETERNAL.get());
							entries.accept(DoomItems.PISTOL.get());
							entries.accept(DoomItems.HEAVYCANNON.get());
							entries.accept(DoomItems.CHAINGUN.get());
							entries.accept(DoomItems.SG.get());
							entries.accept(DoomItems.DSG.get());
							entries.accept(DoomItems.SSG.get());
							entries.accept(DoomItems.DPLASMARIFLE.get());
							entries.accept(DoomItems.PLASMAGUN.get());
							entries.accept(DoomItems.ROCKETLAUNCHER.get());
							entries.accept(DoomItems.DGAUSS.get());
							entries.accept(DoomItems.BALLISTA.get());
							entries.accept(DoomItems.UNMAKER.get());
							entries.accept(DoomItems.UNMAYKR.get());
							entries.accept(DoomItems.BFG.get());
							entries.accept(DoomItems.BFG_ETERNAL.get());
							entries.accept(DoomItems.SWORD_CLOSED.get());
							entries.accept(DoomItems.CRUCIBLESWORD.get());
							entries.accept(DoomItems.AXE_CLOSED.get());
							entries.accept(DoomItems.AXE_OPEN.get());
							entries.accept(DoomItems.DARKLORDCRUCIBLE.get());
							entries.accept(DoomItems.SENTINELHAMMER.get());
							entries.accept(DoomItems.GRENADE.get());
							entries.accept(DoomItems.GAS_BARREL.get());
							entries.accept(DoomItems.BULLETS.get());
							entries.accept(DoomItems.SHOTGUN_SHELLS.get());
							entries.accept(DoomItems.CHAINGUN_BULLETS.get());
							entries.accept(DoomItems.ROCKET.get());
							entries.accept(DoomItems.ARGENT_BOLT.get());
							entries.accept(DoomItems.ENERGY_CELLS.get());
							entries.accept(DoomItems.BFG_CELL.get());
						}));
		event.registerCreativeModeTab(new ResourceLocation(MODID, "powerup"),
				e -> e.icon(() -> new ItemStack(DoomItems.SOULCUBE.get()))
				.title(Component.translatable("itemGroup." + MODID + ".eggs"))
						.displayItems((enabledFeatures, entries, operatorEnabled) -> {
							entries.accept(DoomItems.ARGENT_ENERGY.get());
							entries.accept(DoomItems.ARGENT_PLATE.get());
							entries.accept(DoomItems.SOULCUBE.get());
							entries.accept(DoomItems.INMORTAL.get());
							entries.accept(DoomItems.INVISIBLE.get());
							entries.accept(DoomItems.MEGA.get());
							entries.accept(DoomItems.POWER.get());
							entries.accept(DoomItems.DAISY.get());
							entries.accept(DoomItems.E1M1_MUSIC_DISC.get());
							entries.accept(DoomItems.GEOF_MUSIC_DISC.get());
						}));
	}
}