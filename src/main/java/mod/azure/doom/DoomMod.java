package mod.azure.doom;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.serialization.Codec;

import mod.azure.doom.client.ModItemModelsProperties;
import mod.azure.doom.structures.DoomConfiguredStructures;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.LootHandler;
import mod.azure.doom.util.SoulCubeHandler;
import mod.azure.doom.util.config.BiomeConfig;
import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomRecipes;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.ModEntitySpawn;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.item.ItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.server.ServerWorld;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModConfigEvent);
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC, "doom-config.toml");
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, Config.BIOME_SPEC, "doom-mob-biomes.toml");
		Config.SERVER.bakeConfig();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::enqueueIMC);
		DoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
		// MinecraftForge.EVENT_BUS.addListener(this::gEvent);
		MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
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
	public void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		if (config.getSpec() == Config.BIOME_SPEC) {
			BiomeConfig.bake(config);
		}
	}

	@SubscribeEvent
	public void onBiomeLoad(BiomeLoadingEvent event) {
		ModEntitySpawn.onBiomesLoad(event);
	}

	private void clientSetup(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new ModItemModelsProperties());
	}

	private void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new LootHandler());
		DoomPacketHandler.register();
		event.enqueueWork(() -> {
			DoomStructures.setupStructures();
			DoomConfiguredStructures.registerConfiguredStructures();
		});
	}
	
	public void biomeModification(final BiomeLoadingEvent event) {
		if (event.getCategory().equals(BiomeCategory.THEEND)) {
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_MAYKR);
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_ARCHMAYKR);
		}
		if (event.getCategory().equals(BiomeCategory.NETHER)) {
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_TITAN_SKULL);
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_MOTHERDEMON);
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_NETHERPORTAL);
		}
		if (!(event.getCategory().equals(BiomeCategory.NETHER) || event.getCategory().equals(BiomeCategory.THEEND))) {
			event.getGeneration().getStructures().add(() -> DoomConfiguredStructures.CONFIGURED_PORTAL);
		}
	}

	private static Method GETCODEC_METHOD;

	@SuppressWarnings("unchecked")
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();
			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR
						.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD
								.invoke(serverWorld.getChunkSource().generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
					return;
			} catch (Exception e) {
				DoomMod.LOGGER.error("Was unable to check if " + serverWorld.dimension().location()
						+ " is using Terraforged's ChunkGenerator.");
			}
			if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator
					&& serverWorld.dimension().equals(World.OVERWORLD)) {
				return;
			}
			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
					serverWorld.getChunkSource().generator.getSettings().structureConfig());
			tempMap.putIfAbsent(DoomStructures.MAYKR.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.MAYKR.get()));
			tempMap.putIfAbsent(DoomStructures.ARCHMAYKR.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.ARCHMAYKR.get()));
			tempMap.putIfAbsent(DoomStructures.MOTHERDEMON.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.MOTHERDEMON.get()));
			tempMap.putIfAbsent(DoomStructures.TITAN_SKULL.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.TITAN_SKULL.get()));
			tempMap.putIfAbsent(DoomStructures.PORTAL.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.PORTAL.get()));
			tempMap.putIfAbsent(DoomStructures.NETHERPORTAL.get(),
					DimensionStructuresSettings.DEFAULTS.get(DoomStructures.NETHERPORTAL.get()));
			serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
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