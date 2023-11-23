package mod.azure.doom;

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
import mod.azure.doom.registry.*;
import mod.azure.doom.registry.NeoDoomEntities;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MCDoom.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientListener {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NeoDoomEntities.GLADIATOR_MACE.get(), GladiatorMaceRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MEATHOOOK_ENTITY.get(), MeatHookEntityRenderer::new);
        event.registerEntityRenderer(NeoDoomEntities.SHOTGUN_SHELL.get(), ShotgunShellRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ARGENT_BOLT.get(), ArgentBoltRender::new);
        event.registerEntityRenderer(NeoDoomEntities.GRENADE.get(), GrenadeRender::new);
        event.registerEntityRenderer(NeoDoomEntities.DRONEBOLT_MOB.get(), DroneBoltRender::new);
        event.registerEntityRenderer(NeoDoomEntities.FIRE_MOB.get(), FireProjectileRender::new);
        event.registerEntityRenderer(NeoDoomEntities.UNMAYKR.get(), UnmaykrBulletRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BULLETS.get(), BulletsRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BFG_CELL.get(), BFGCellRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ROCKET.get(), RocketRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CHAINGUN_BULLET.get(), ChaingunBulletRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BLOODBOLT_MOB.get(), BloodBoltRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BARENBLAST.get(), BarenBlastRender::new);
        event.registerEntityRenderer(NeoDoomEntities.LOST_SOUL.get(), LostSoulRender::new);
        event.registerEntityRenderer(NeoDoomEntities.LOST_SOUL_ETERNAL.get(), LostSoulEternalRender::new);
        event.registerEntityRenderer(NeoDoomEntities.IMP.get(), ImpRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ARACHNOTRON.get(), ArachonotronRender::new);
        event.registerEntityRenderer(NeoDoomEntities.PINKY.get(), PinkyRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CACODEMON.get(), CacodemonRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ARCHVILE.get(), ArchvileRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BARON.get(), BaronRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MANCUBUS.get(), MancubusRender::new);
        event.registerEntityRenderer(NeoDoomEntities.SPIDERMASTERMIND.get(), SpiderMastermindRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ZOMBIEMAN.get(), ZombiemanRender::new);
        event.registerEntityRenderer(NeoDoomEntities.REVENANT.get(), RevenantRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CHAINGUNNER.get(), ChaingunnerRender::new);
        event.registerEntityRenderer(NeoDoomEntities.SHOTGUNGUY.get(), ShotgunguyRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MARAUDER.get(), MarauderRender::new);
        event.registerEntityRenderer(NeoDoomEntities.PAIN.get(), PainRender::new);
        event.registerEntityRenderer(NeoDoomEntities.HELLKNIGHT.get(), HellknightRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CYBERDEMON.get(), CyberdemonRender::new);
        event.registerEntityRenderer(NeoDoomEntities.UNWILLING.get(), UnwillingRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ICONOFSIN.get(), IconofsinRender::new);
        event.registerEntityRenderer(NeoDoomEntities.POSSESSEDSCIENTIST.get(), PossessedScientistRender::new);
        event.registerEntityRenderer(NeoDoomEntities.POSSESSEDSOLDIER.get(), PossessedSoldierRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ENERGY_CELL_MOB.get(), EnergyCellMobRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ENERGY_CELL.get(), EnergyCellRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ROCKET_MOB.get(), RocketMobRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CHAINGUN_MOB.get(), ChaingunMobRender::new);
        event.registerEntityRenderer(NeoDoomEntities.GORE_NEST.get(), GoreNestRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MECHAZOMBIE.get(), MechaZombieRender::new);
        event.registerEntityRenderer(NeoDoomEntities.GARGOYLE.get(), GargoyleRender::new);
        event.registerEntityRenderer(NeoDoomEntities.HELLKNIGHT2016.get(), Hellknight2016Render::new);
        event.registerEntityRenderer(NeoDoomEntities.FIRING.get(), ArchvileFiringRender::new);
        event.registerEntityRenderer(NeoDoomEntities.SPECTRE.get(), SpectreRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CUEBALL.get(), CueBallRender::new);
        event.registerEntityRenderer(NeoDoomEntities.PROWLER.get(), ProwlerRender::new);
        event.registerEntityRenderer(NeoDoomEntities.DREADKNIGHT.get(), DreadKnightRender::new);
        event.registerEntityRenderer(NeoDoomEntities.IMP_STONE.get(), ImpStoneRender::new);
        event.registerEntityRenderer(NeoDoomEntities.POSSESSEDWORKER.get(), PossessedWorkerRender::new);
        event.registerEntityRenderer(NeoDoomEntities.DOOMHUNTER.get(), DoomHunterRender::new);
        event.registerEntityRenderer(NeoDoomEntities.WHIPLASH.get(), WhiplashRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BARON2016.get(), Baron2016Render::new);
        event.registerEntityRenderer(NeoDoomEntities.FIREBARON.get(), FireBaronRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ARMORBARON.get(), ArmoredBaronRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MAYKRDRONE.get(), MaykrDroneRender::new);
        event.registerEntityRenderer(NeoDoomEntities.BLOODMAYKR.get(), BloodMaykrRender::new);
        event.registerEntityRenderer(NeoDoomEntities.ARCHMAKER.get(), ArchMaykrRender::new);
        event.registerEntityRenderer(NeoDoomEntities.SPIDERMASTERMIND2016.get(), SpiderMastermind2016Render::new);
        event.registerEntityRenderer(NeoDoomEntities.ARACHNOTRONETERNAL.get(), ArachonotronEternalRender::new);
        event.registerEntityRenderer(NeoDoomEntities.TENTACLE.get(), TentacleRender::new);
        event.registerEntityRenderer(NeoDoomEntities.MOTHERDEMON.get(), MotherDemonRender::new);
        event.registerEntityRenderer(NeoDoomEntities.TURRET.get(), TurretRender::new);
        event.registerEntityRenderer(NeoDoomEntities.SUMMONER.get(), SummonerRender::new);
        event.registerEntityRenderer(NeoDoomEntities.REVENANT2016.get(), Revenant2016Render::new);
        event.registerEntityRenderer(NeoDoomEntities.GLADIATOR.get(), GladiatorRender::new);
        event.registerEntityRenderer(NeoDoomEntities.CARCASS.get(), CarcassRender::new);

        event.registerEntityRenderer(NeoDoomEntities.BARREL.get(), BarrelRender::new);
        event.registerBlockEntityRenderer(NeoDoomEntities.TOTEM.get(), context -> new TotemRender());
        event.registerBlockEntityRenderer(NeoDoomEntities.GUN_TABLE_ENTITY.get(), context -> new GunCraftingRender());
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(NeoDoomBlocks.JUMP_PAD.get(), RenderType.translucent());
        MenuScreens.register(NeoDoomScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
        // Crucible
        ItemProperties.register(NeoDoomItems.CRUCIBLESWORD.get(), new ResourceLocation("broken"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F));
        // Marauder Axe
        ItemProperties.register(NeoDoomItems.AXE_OPEN.get(), new ResourceLocation("broken"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F));
        // NonCenter
        ItemProperties.register(NeoDoomItems.SG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.ROCKETLAUNCHER.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.PLASMAGUN.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.HEAVYCANNON.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.UNMAKER.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.UNMAYKR.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.CHAINGUN.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.BFG_ETERNAL.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.BALLISTA.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.SSG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.PISTOL.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.DPLASMARIFLE.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.DGAUSS.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.DSG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.nonCentered(itemStack) ? 1.0F : 0.0F));
        ItemProperties.register(NeoDoomItems.CHAINSAW.get(), new ResourceLocation("stalled"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F));
        ItemProperties.register(NeoDoomItems.CHAINSAW64.get(), new ResourceLocation("stalled"), (itemStack, clientWorld, livingEntity, seed) -> (CommonUtils.isUsable(itemStack) ? 0.0F : 1.0F));
    }
}
