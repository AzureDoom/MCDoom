package mod.azure.doom;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;

import mod.azure.doom.structures.DoomConfiguredStructures;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.structures.templates.ArchMaykrStructure;
import mod.azure.doom.structures.templates.MaykrStructure;
import mod.azure.doom.structures.templates.MotherDemonStructure;
import mod.azure.doom.structures.templates.NetherPortalStructure;
import mod.azure.doom.structures.templates.PortalStructure;
import mod.azure.doom.structures.templates.TitanSkullStructure;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.LootHandler;
import mod.azure.doom.util.SoulCubeHandler;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomRecipes;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.ModEntitySpawn;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(DoomMod.MODID)
public class DoomMod {

	public static DoomMod instance;
	public static final String MODID = "doom";
	public static final Logger LOGGER = LogManager.getLogger();

	public DoomMod() {
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoomConfig.SERVER_SPEC, "doom-newconfig.toml");
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		DoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.NORMAL, ArchMaykrStructure::setupStructureSpawns);
		forgeBus.addListener(EventPriority.NORMAL, MaykrStructure::setupStructureSpawns);
		forgeBus.addListener(EventPriority.NORMAL, MotherDemonStructure::setupStructureSpawns);
		forgeBus.addListener(EventPriority.NORMAL, NetherPortalStructure::setupStructureSpawns);
		forgeBus.addListener(EventPriority.NORMAL, PortalStructure::setupStructureSpawns);
		forgeBus.addListener(EventPriority.NORMAL, TitanSkullStructure::setupStructureSpawns);
		if (DoomConfig.SERVER.enable_all_villager_trades.get()) {
			MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
		}
		ModSoundEvents.MOD_SOUNDS.register(modEventBus);
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);
		DoomItems.ITEMS.register(modEventBus);
		DoomBlocks.BLOCKS.register(modEventBus);
		ModEntityTypes.TILE_TYPES.register(modEventBus);
		DoomScreens.CONTAIN.register(modEventBus);
		DoomRecipes.SERIAL.register(modEventBus);
		MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
		GeckoLib.initialize();
		GeckoLibNetwork.initialize();
	}

	@SubscribeEvent
	public void onBiomeLoad(BiomeLoadingEvent event) {
		ModEntitySpawn.onBiomesLoad(event);
	}

	private void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new LootHandler());
		DoomPacketHandler.register();
		event.enqueueWork(() -> {
			DoomStructures.setupStructures();
			DoomConfiguredStructures.registerConfiguredStructures();
		});
	}

	private static Method GETCODEC_METHOD;

	@SuppressWarnings({ "unchecked" })
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld()instanceof ServerLevel serverLevel) {
			ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
			StructureSettings worldStructureConfig = chunkGenerator.getSettings();
			ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap
					.builder();
			worldStructureConfig.configuredStructures.entrySet().forEach(tempStructureToMultiMap::put);
			ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> tempConfiguredStructureBiomeMultiMap = ImmutableMultimap
					.builder();
			for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess()
					.ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
				Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
				if (biomeCategory != Biome.BiomeCategory.OCEAN && biomeCategory != Biome.BiomeCategory.THEEND
						&& biomeCategory != Biome.BiomeCategory.NETHER && biomeCategory != Biome.BiomeCategory.NONE) {
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_PORTAL,
							biomeEntry.getKey());
				}
				if (biomeCategory == Biome.BiomeCategory.THEEND) {
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_MAYKR,
							biomeEntry.getKey());
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_ARCHMAYKR,
							biomeEntry.getKey());
				}
				if (biomeCategory == Biome.BiomeCategory.NETHER) {
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_TITAN_SKULL,
							biomeEntry.getKey());
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_MOTHERDEMON,
							biomeEntry.getKey());
					tempConfiguredStructureBiomeMultiMap.put(DoomConfiguredStructures.CONFIGURED_NETHERPORTAL,
							biomeEntry.getKey());
				}
			}
			tempStructureToMultiMap.put(DoomStructures.MAYKR.get(), tempConfiguredStructureBiomeMultiMap.build());
			tempStructureToMultiMap.put(DoomStructures.ARCHMAYKR.get(), tempConfiguredStructureBiomeMultiMap.build());
			tempStructureToMultiMap.put(DoomStructures.TITAN_SKULL.get(), tempConfiguredStructureBiomeMultiMap.build());
			tempStructureToMultiMap.put(DoomStructures.PORTAL.get(), tempConfiguredStructureBiomeMultiMap.build());
			tempStructureToMultiMap.put(DoomStructures.NETHERPORTAL.get(),
					tempConfiguredStructureBiomeMultiMap.build());
			tempStructureToMultiMap.put(DoomStructures.MOTHERDEMON.get(), tempConfiguredStructureBiomeMultiMap.build());

			worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();
			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR
						.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
					return;
			} catch (Exception e) {
				DoomMod.LOGGER.error("Was unable to check if " + serverLevel.dimension().location()
						+ " is using Terraforged's ChunkGenerator.");
			}
			if (serverLevel.getChunkSource().getGenerator() instanceof FlatLevelSource
					&& serverLevel.dimension().equals(Level.OVERWORLD)) {
				return;
			}
			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(
					worldStructureConfig.structureConfig());
			tempMap.putIfAbsent(DoomStructures.MAYKR.get(), StructureSettings.DEFAULTS.get(DoomStructures.MAYKR.get()));
			tempMap.putIfAbsent(DoomStructures.ARCHMAYKR.get(),
					StructureSettings.DEFAULTS.get(DoomStructures.ARCHMAYKR.get()));
			tempMap.putIfAbsent(DoomStructures.MOTHERDEMON.get(),
					StructureSettings.DEFAULTS.get(DoomStructures.MOTHERDEMON.get()));
			tempMap.putIfAbsent(DoomStructures.TITAN_SKULL.get(),
					StructureSettings.DEFAULTS.get(DoomStructures.TITAN_SKULL.get()));
			tempMap.putIfAbsent(DoomStructures.PORTAL.get(),
					StructureSettings.DEFAULTS.get(DoomStructures.PORTAL.get()));
			tempMap.putIfAbsent(DoomStructures.NETHERPORTAL.get(),
					StructureSettings.DEFAULTS.get(DoomStructures.NETHERPORTAL.get()));
			worldStructureConfig.structureConfig = tempMap;
		}
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
				() -> SlotTypePreset.CHARM.getMessageBuilder().build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
				() -> SlotTypePreset.BELT.getMessageBuilder().build());
	}

	public static final CreativeModeTab DoomWeaponItemGroup = (new CreativeModeTab("doomweapons") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.BFG_ETERNAL.get());
		}
	});

	public static final CreativeModeTab DoomArmorItemGroup = (new CreativeModeTab("doomarmor") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.DOOM_HELMET.get());
		}
	});

	public static final CreativeModeTab DoomBlockItemGroup = (new CreativeModeTab("doomblocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomBlocks.BARREL_BLOCK.get());
		}
	});

	public static final CreativeModeTab DoomEggItemGroup = (new CreativeModeTab("doomeggs") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.IMP_SPAWN_EGG.get());
		}
	});

	public static final CreativeModeTab DoomPowerUPItemGroup = (new CreativeModeTab("doompowerup") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.INMORTAL.get());
		}
	});
}