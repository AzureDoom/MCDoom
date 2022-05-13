package mod.azure.doom;

import java.util.List;

import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.LootHandler;
import mod.azure.doom.util.SoulCubeHandler;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.DoomRecipes;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.DoomStructures;
import mod.azure.doom.util.registry.ModEntitySpawn;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.network.GeckoLibNetwork;
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

	public DoomMod() {
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoomConfig.SERVER_SPEC, "doom-newconfig.toml");
		DoomConfig.loadConfig(DoomConfig.SERVER_SPEC,
				FMLPaths.CONFIGDIR.get().resolve("doom-newconfig.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
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
		DoomParticles.PARTICLES.register(modEventBus);
		MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
		DoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
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