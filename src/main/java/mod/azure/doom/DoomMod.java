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
import mod.azure.doom.util.registry.DoomProjectiles;
import mod.azure.doom.util.registry.DoomRecipes;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.DoomStructures;
import mod.azure.doom.util.registry.ModEntitySpawn;
import net.minecraft.core.Registry;
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
	public static final TagKey<Block> PAXEL_BLOCKS = TagKey.create(Registry.BLOCK_REGISTRY, DoomMod.modResource("paxel_blocks"));
	public static final Tier ARGENT_TIER = TierSortingRegistry.registerTier(new ForgeTier(17, 5000, 18, 3.0F, 30, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())), new ResourceLocation(MODID, "argent"), List.of(Tiers.NETHERITE), List.of());
	public static final Tier DOOM_HIGHTEIR = TierSortingRegistry.registerTier(new ForgeTier(17, 0, 30, -1.9F, 0, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())), new ResourceLocation(MODID, "doom_highertier"), List.of(Tiers.NETHERITE), List.of());

	public DoomMod() {
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DoomConfig.SERVER_SPEC, "doom-newconfig.toml");
		DoomConfig.loadConfig(DoomConfig.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("doom-newconfig.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
		if (DoomConfig.SERVER.enable_soulcube_effects.get())
			MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		if (DoomConfig.SERVER.enable_all_villager_trades.get())
			MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
		DoomSounds.MOD_SOUNDS.register(modEventBus);
		DoomEntities.ENTITY_TYPES.register(modEventBus);
		DoomItems.ITEMS.register(modEventBus);
		DoomBlocks.BLOCKS.register(modEventBus);
		DoomEntities.TILE_TYPES.register(modEventBus);
		DoomProjectiles.ENTITY_TYPES.register(modEventBus);
		DoomScreens.CONTAIN.register(modEventBus);
		DoomRecipes.SERIAL.register(modEventBus);
		DoomParticles.PARTICLES.register(modEventBus);
		DoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		ModEntitySpawn.SERIALIZER.register(modEventBus);
		AzureLib.initialize();
	}

	private void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new LootHandler());
		DoomPacketHandler.register();
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.MODIFY_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
		InterModComms.sendTo("curios", SlotTypeMessage.MODIFY_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
	}

	public static final ResourceLocation modResource(String name) {
		return new ResourceLocation(MODID, name);
	}

	public static final CreativeModeTab DoomWeaponItemGroup = (new CreativeModeTab("weapons") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.BFG_ETERNAL.get());
		}
	});

	public static final CreativeModeTab DoomArmorItemGroup = (new CreativeModeTab("armor") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.DOOM_HELMET.get());
		}
	});

	public static final CreativeModeTab DoomBlockItemGroup = (new CreativeModeTab("blocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomBlocks.BARREL_BLOCK.get());
		}
	});

	public static final CreativeModeTab DoomEggItemGroup = (new CreativeModeTab("eggs") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.IMP_SPAWN_EGG.get());
		}
	});

	public static final CreativeModeTab DoomPowerUPItemGroup = (new CreativeModeTab("powerup") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DoomItems.INMORTAL.get());
		}
	});
}