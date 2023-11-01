package mod.azure.doom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.DoomVillagerTrades;
import mod.azure.doom.util.SoulCubeHandler;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.registry.*;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod(DoomMod.MODID)
public class DoomMod {

    public static DoomMod instance;
    public static DoomConfig config;
    public static final String MODID = "doom";
    public static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final List<Runnable> REGISTRY_INIT_TASKS = new ObjectArrayList<>();
    public static final TagKey<Block> ARGENT_TAG = BlockTags.create(new ResourceLocation(MODID, "needs_argent_tool"));
    public static final TagKey<Block> PAXEL_BLOCKS = TagKey.create(Registries.BLOCK, DoomMod.modResource("paxel_blocks"));
    public static final Tier ARGENT_TIER = TierSortingRegistry.registerTier(new ForgeTier(17, 5000, 18, 3.0F, 30, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())), new ResourceLocation(MODID, "argent"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier DOOM_HIGHTEIR = TierSortingRegistry.registerTier(new ForgeTier(17, 0, 30, -1.9F, 0, ARGENT_TAG, () -> Ingredient.of(DoomItems.ARGENT_BLOCK.get())), new ResourceLocation(MODID, "doom_highertier"), List.of(Tiers.NETHERITE), List.of());

    public DoomMod() {
        instance = this;
        config = AzureLibMod.registerConfig(DoomConfig.class, ConfigFormats.json()).getConfigInstance();
        MinecraftForge.EVENT_BUS.register(this);
        if (DoomMod.config.enable_soulcube_effects)
            MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
        modEventBus.addListener(this::setup);
        if (DoomMod.config.enable_all_villager_trades)
            MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
        DoomSounds.MOD_SOUNDS.register(modEventBus);
        DoomEntities.ENTITY_TYPES.register(modEventBus);
        DoomItems.ITEMS.register(modEventBus);
        DoomBlocks.BLOCKS.register(modEventBus);
        DoomTabs.TABS.register(modEventBus);
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
        DoomPacketHandler.register();
        CustomPortalBuilder.beginPortal().frameBlock(DoomBlocks.E1M1_1.get()).lightWithItem(DoomItems.ARGENT_ENERGY.get()).destDimID(DoomMod.modResource("doom_hell")).tintColor(139, 0, 0).registerPortal();
    }

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MODID, name);
    }
}