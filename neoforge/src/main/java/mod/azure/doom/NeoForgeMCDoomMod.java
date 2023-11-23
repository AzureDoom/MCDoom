package mod.azure.doom;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.helper.DoomVillagerTrades;
import mod.azure.doom.helper.ModEntitySpawn;
import mod.azure.doom.helper.SoulCubeHandler;
import mod.azure.doom.platform.Services;
import mod.azure.doom.registry.*;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
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

@Mod.EventBusSubscriber
@Mod(MCDoom.MOD_ID)
public final class NeoForgeMCDoomMod {

    public static NeoForgeMCDoomMod instance;
    public static final TagKey<Block> ARGENT_TAG = BlockTags.create(MCDoom.modResource("needs_argent_tool"));
    public static final Tier ARGENT_TIER = TierSortingRegistry.registerTier(new ForgeTier(17, 5000, 18, 3.0F, 30, ARGENT_TAG, () -> Ingredient.of(NeoDoomItems.ARGENT_BLOCK.get())), MCDoom.modResource("argent"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier DOOM_HIGHTEIR = TierSortingRegistry.registerTier(new ForgeTier(17, 0, 30, -1.9F, 0, ARGENT_TAG, () -> Ingredient.of(NeoDoomItems.ARGENT_BLOCK.get())), MCDoom.modResource("doom_highertier"), List.of(Tiers.NETHERITE), List.of());


    public NeoForgeMCDoomMod() {
        instance = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MCDoom.config = AzureLibMod.registerConfig(DoomConfig.class, ConfigFormats.json()).getConfigInstance();
        if (MCDoom.config.enable_soulcube_effects)
            MinecraftForge.EVENT_BUS.register(new SoulCubeHandler());
        modEventBus.addListener(this::setup);
        if (MCDoom.config.enable_all_villager_trades)
            MinecraftForge.EVENT_BUS.addListener(DoomVillagerTrades::onVillagerTradesEvent);
        NeoDoomItems.ITEMS.register(modEventBus);
        NeoDoomBlocks.BLOCKS.register(modEventBus);
        NeoDoomTabs.TABS.register(modEventBus);
        NeoDoomSounds.MOD_SOUNDS.register(modEventBus);
        NeoDoomEntities.ENTITY_TYPES.register(modEventBus);
        NeoDoomEntities.TILE_TYPES.register(modEventBus);
        NeoDoomScreens.CONTAIN.register(modEventBus);
        NeoDoomRecipes.SERIAL.register(modEventBus);
        NeoDoomParticles.PARTICLES.register(modEventBus);
        NeoDoomStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        ModEntitySpawn.SERIALIZER.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Services.NETWORK.registerClientReceiverPackets();
        CustomPortalBuilder.beginPortal().frameBlock(NeoDoomBlocks.E1M1_1.get()).lightWithItem(NeoDoomItems.ARGENT_ENERGY.get()).destDimID(MCDoom.modResource("doom_hell")).tintColor(139, 0, 0).registerPortal();
    }
}
