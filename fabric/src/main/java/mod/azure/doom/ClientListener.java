package mod.azure.doom;

import com.mojang.blaze3d.platform.InputConstants;
import mod.azure.doom.client.DoomKeyBinds;
import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.client.render.mobs.ambient.CueBallRender;
import mod.azure.doom.client.render.mobs.ambient.GoreNestRender;
import mod.azure.doom.client.render.mobs.ambient.TentacleRender;
import mod.azure.doom.client.render.mobs.ambient.TurretRender;
import mod.azure.doom.client.render.mobs.boss.*;
import mod.azure.doom.client.render.mobs.fodder.*;
import mod.azure.doom.client.render.mobs.heavy.*;
import mod.azure.doom.client.render.mobs.superheavy.*;
import mod.azure.doom.client.render.projectiles.*;
import mod.azure.doom.client.render.projectiles.entity.*;
import mod.azure.doom.client.render.tile.BarrelRender;
import mod.azure.doom.client.render.tile.GunCraftingRender;
import mod.azure.doom.client.render.tile.TotemRender;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.registry.FabricDoomBlocks;
import mod.azure.doom.registry.FabricDoomEntities;
import mod.azure.doom.registry.FabricDoomItems;
import mod.azure.doom.registry.FabricDoomParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public final class ClientListener implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DoomKeyBinds.HOOK = new KeyMapping("key.doom.meathook", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H,
                "category.doom.binds");
        KeyBindingHelper.registerKeyBinding(DoomKeyBinds.HOOK);
        DoomKeyBinds.FIRETYPE = new KeyMapping("key.doom.firetype", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G,
                "category.doom.binds");
        KeyBindingHelper.registerKeyBinding(DoomKeyBinds.FIRETYPE);
        this.initItemPlacement();
        this.initMobRenders();
        MenuScreens.register(FabricMCDoomMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
        ParticleFactoryRegistry.getInstance().register(FabricDoomParticles.PLASMA, PlasmaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FabricDoomParticles.PISTOL, PlasmaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FabricDoomParticles.UNMAYKR, PlasmaParticle.Factory::new);
    }

    public void initMobRenders() {
        EntityRendererRegistry.register(FabricDoomEntities.ARCHVILE, ArchvileRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BARREL, BarrelRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.IMP, ImpRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.PINKY, PinkyRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.SPECTRE, SpectreRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.LOST_SOUL, LostSoulRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.LOST_SOUL_ETERNAL, LostSoulEternalRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.CACODEMON, CacodemonRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BARON, BaronRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.MANCUBUS, MancubusRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.SPIDERMASTERMIND, SpiderMastermindRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ARACHNOTRON, ArachonotronRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ZOMBIEMAN, ZombiemanRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.REVENANT, RevenantRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.GORE_NEST, GoreNestRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.CHAINGUNNER, ChaingunnerRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.SHOTGUNGUY, ShotgunguyRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.MARAUDER, MarauderRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.PAIN, PainRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.HELLKNIGHT, HellknightRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.HELLKNIGHT2016, Hellknight2016Render::new);
        EntityRendererRegistry.register(FabricDoomEntities.CYBERDEMON, CyberdemonRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.UNWILLING, UnwillingRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ICONOFSIN, IconofsinRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.POSSESSEDSCIENTIST, PossessedScientistRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.POSSESSEDSOLDIER, PossessedSoldierRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.GARGOYLE, GargoyleRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.MECHAZOMBIE, MechaZombieRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.CUEBALL, CueBallRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.PROWLER, ProwlerRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.DREADKNIGHT, DreadKnightRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.IMP_STONE, ImpStoneRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.POSSESSEDWORKER, PossessedWorkerRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.DOOMHUNTER, DoomHunterRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.MAYKRDRONE, MaykrDroneRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.WHIPLASH, WhiplashRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BARON2016, Baron2016Render::new);
        EntityRendererRegistry.register(FabricDoomEntities.FIREBARON, FireBaronRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ARMORBARON, ArmoredBaronRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BLOODMAYKR, BloodMaykrRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ARCHMAKER, ArchMaykrRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ARACHNOTRONETERNAL, ArachonotronEternalRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.SPIDERMASTERMIND2016, SpiderMastermind2016Render::new);
        EntityRendererRegistry.register(FabricDoomEntities.TENTACLE, TentacleRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.TURRET, TurretRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.MOTHERDEMON, MotherDemonRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.SUMMONER, SummonerRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.REVENANT2016, Revenant2016Render::new);
        EntityRendererRegistry.register(FabricDoomEntities.GLADIATOR, GladiatorRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.CARCASS, CarcassRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.GRENADE, GrenadeRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BFG_CELL, BFGCellRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ROCKET, RocketRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BARENBLAST, BarenBlastRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BULLETS, BulletsRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ROCKET_MOB, RocketMobRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.ENERGY_CELL_MOB, EnergyCellMobRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.CHAINGUN_MOB, ChaingunMobRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.FIRING, ArchvileFiringRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.GLADIATOR_MACE, GladiatorMaceRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.DRONEBOLT_MOB, DroneBoltRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.BLOODBOLT_MOB, BloodBoltRender::new);
        EntityRendererRegistry.register(FabricDoomEntities.FIRE_MOB, FireProjectileRender::new);
        BlockEntityRenderers.register(FabricDoomEntities.TOTEM,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new TotemRender());
        BlockEntityRenderers.register(FabricDoomEntities.GUN_TABLE_ENTITY,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new GunCraftingRender());
        BlockRenderLayerMap.INSTANCE.putBlock(FabricDoomBlocks.JUMP_PAD, RenderType.translucent());
        EntityRendererRegistry.register(FabricDoomEntities.MEATHOOOK_ENTITY, MeatHookEntityRenderer::new);
    }

    public void initItemPlacement() {
        // Crucible
        ItemProperties.register(FabricDoomItems.CRUCIBLESWORD, new ResourceLocation("broken"),
                (itemStack, clientWorld, livingEntity, seed) ->
                        CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F);
        // Marauder Axe
        ItemProperties.register(FabricDoomItems.AXE_OPEN, new ResourceLocation("broken"),
                (itemStack, clientWorld, livingEntity, seed) -> itemStack.getDamageValue() < itemStack.getMaxDamage() - 1 ? 0.0F : 1.0F);
        // NonCenter
        ItemProperties.register(FabricDoomItems.SG, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.ROCKETLAUNCHER, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.PLASMAGUN, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.HEAVYCANNON, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.UNMAYKR, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.UNMAKER, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.CHAINGUN, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.BFG_ETERNAL, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.BALLISTA, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.SSG, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.PISTOL, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.DPLASMARIFLE, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.DGAUSS, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.DSG, new ResourceLocation("nocenter"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.nonCentered() ? 1.0F : 0.0F);
        ItemProperties.register(FabricDoomItems.CHAINSAW, new ResourceLocation("stalled"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F);
        ItemProperties.register(FabricDoomItems.CHAINSAW64, new ResourceLocation("stalled"),
                (itemStack, clientWorld, livingEntity, seed) -> CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F);
    }
}
