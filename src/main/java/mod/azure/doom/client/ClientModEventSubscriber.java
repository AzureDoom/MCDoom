package mod.azure.doom.client;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.client.render.ArachonotronEternalRender;
import mod.azure.doom.client.render.ArachonotronRender;
import mod.azure.doom.client.render.ArchMaykrRender;
import mod.azure.doom.client.render.ArchvileRender;
import mod.azure.doom.client.render.ArmoredBaronRender;
import mod.azure.doom.client.render.Baron2016Render;
import mod.azure.doom.client.render.BaronRender;
import mod.azure.doom.client.render.BarrelRender;
import mod.azure.doom.client.render.BloodMaykrRender;
import mod.azure.doom.client.render.CacodemonRender;
import mod.azure.doom.client.render.CarcassRender;
import mod.azure.doom.client.render.ChaingunnerRender;
import mod.azure.doom.client.render.CueBallRender;
import mod.azure.doom.client.render.CyberdemonRender;
import mod.azure.doom.client.render.DoomHunterRender;
import mod.azure.doom.client.render.DreadKnightRender;
import mod.azure.doom.client.render.FireBaronRender;
import mod.azure.doom.client.render.GargoyleRender;
import mod.azure.doom.client.render.GladiatorRender;
import mod.azure.doom.client.render.GoreNestRender;
import mod.azure.doom.client.render.Hellknight2016Render;
import mod.azure.doom.client.render.HellknightRender;
import mod.azure.doom.client.render.IconofsinRender;
import mod.azure.doom.client.render.ImpRender;
import mod.azure.doom.client.render.ImpStoneRender;
import mod.azure.doom.client.render.LostSoulEternalRender;
import mod.azure.doom.client.render.LostSoulRender;
import mod.azure.doom.client.render.MancubusRender;
import mod.azure.doom.client.render.MarauderRender;
import mod.azure.doom.client.render.MaykrDroneRender;
import mod.azure.doom.client.render.MechaZombieRender;
import mod.azure.doom.client.render.MotherDemonRender;
import mod.azure.doom.client.render.PainRender;
import mod.azure.doom.client.render.PinkyRender;
import mod.azure.doom.client.render.PossessedScientistRender;
import mod.azure.doom.client.render.PossessedSoldierRender;
import mod.azure.doom.client.render.PossessedWorkerRender;
import mod.azure.doom.client.render.ProwlerRender;
import mod.azure.doom.client.render.Revenant2016Render;
import mod.azure.doom.client.render.RevenantRender;
import mod.azure.doom.client.render.ShotgunguyRender;
import mod.azure.doom.client.render.SpectreRender;
import mod.azure.doom.client.render.SpiderMastermind2016Render;
import mod.azure.doom.client.render.SpiderMastermindRender;
import mod.azure.doom.client.render.SummonerRender;
import mod.azure.doom.client.render.TentacleRender;
import mod.azure.doom.client.render.TurretRender;
import mod.azure.doom.client.render.UnwillingRender;
import mod.azure.doom.client.render.WhiplashRender;
import mod.azure.doom.client.render.ZombiemanRender;
import mod.azure.doom.client.render.projectiles.ArgentBoltRender;
import mod.azure.doom.client.render.projectiles.BFGCellRender;
import mod.azure.doom.client.render.projectiles.BarenBlastRender;
import mod.azure.doom.client.render.projectiles.BulletsRender;
import mod.azure.doom.client.render.projectiles.ChaingunBulletRender;
import mod.azure.doom.client.render.projectiles.EnergyCellRender;
import mod.azure.doom.client.render.projectiles.GrenadeRender;
import mod.azure.doom.client.render.projectiles.MeatHookEntityRenderer;
import mod.azure.doom.client.render.projectiles.RocketRender;
import mod.azure.doom.client.render.projectiles.ShotgunShellRender;
import mod.azure.doom.client.render.projectiles.UnmaykrBulletRender;
import mod.azure.doom.client.render.projectiles.entity.ArchvileFiringRender;
import mod.azure.doom.client.render.projectiles.entity.BloodBoltRender;
import mod.azure.doom.client.render.projectiles.entity.ChaingunMobRender;
import mod.azure.doom.client.render.projectiles.entity.DroneBoltRender;
import mod.azure.doom.client.render.projectiles.entity.EnergyCellMobRender;
import mod.azure.doom.client.render.projectiles.entity.FireProjectileRender;
import mod.azure.doom.client.render.projectiles.entity.GladiatorMaceRender;
import mod.azure.doom.client.render.projectiles.entity.RocketMobRender;
import mod.azure.doom.client.render.tile.GunCraftingRender;
import mod.azure.doom.client.render.tile.TotemRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomProjectiles;
import mod.azure.doom.util.registry.DoomScreens;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = DoomMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DoomProjectiles.GLADIATOR_MACE.get(), GladiatorMaceRender::new);
		event.registerEntityRenderer(DoomProjectiles.MEATHOOOK_ENTITY.get(), MeatHookEntityRenderer::new);
		event.registerEntityRenderer(DoomProjectiles.SHOTGUN_SHELL.get(), ShotgunShellRender::new);
		event.registerEntityRenderer(DoomProjectiles.ARGENT_BOLT.get(), ArgentBoltRender::new);
		event.registerEntityRenderer(DoomProjectiles.GRENADE.get(), GrenadeRender::new);
		event.registerEntityRenderer(DoomProjectiles.DRONEBOLT_MOB.get(), DroneBoltRender::new);
		event.registerEntityRenderer(DoomProjectiles.FIRE_MOB.get(), FireProjectileRender::new);
		event.registerEntityRenderer(DoomProjectiles.UNMAYKR.get(), UnmaykrBulletRender::new);
		event.registerEntityRenderer(DoomProjectiles.BULLETS.get(), BulletsRender::new);
		event.registerEntityRenderer(DoomProjectiles.BFG_CELL.get(), BFGCellRender::new);
		event.registerEntityRenderer(DoomProjectiles.ROCKET.get(), RocketRender::new);
		event.registerEntityRenderer(DoomProjectiles.CHAINGUN_BULLET.get(), ChaingunBulletRender::new);
		event.registerEntityRenderer(DoomProjectiles.BLOODBOLT_MOB.get(), BloodBoltRender::new);
		event.registerEntityRenderer(DoomProjectiles.BARENBLAST.get(), BarenBlastRender::new);
		event.registerEntityRenderer(DoomEntities.LOST_SOUL.get(), LostSoulRender::new);
		event.registerEntityRenderer(DoomEntities.LOST_SOUL_ETERNAL.get(), LostSoulEternalRender::new);
		event.registerEntityRenderer(DoomEntities.IMP.get(), ImpRender::new);
		event.registerEntityRenderer(DoomEntities.ARACHNOTRON.get(), ArachonotronRender::new);
		event.registerEntityRenderer(DoomEntities.PINKY.get(), PinkyRender::new);
		event.registerEntityRenderer(DoomEntities.CACODEMON.get(), CacodemonRender::new);
		event.registerEntityRenderer(DoomEntities.ARCHVILE.get(), ArchvileRender::new);
		event.registerEntityRenderer(DoomEntities.BARON.get(), BaronRender::new);
		event.registerEntityRenderer(DoomEntities.MANCUBUS.get(), MancubusRender::new);
		event.registerEntityRenderer(DoomEntities.SPIDERMASTERMIND.get(), SpiderMastermindRender::new);
		event.registerEntityRenderer(DoomEntities.ZOMBIEMAN.get(), ZombiemanRender::new);
		event.registerEntityRenderer(DoomEntities.REVENANT.get(), RevenantRender::new);
		event.registerEntityRenderer(DoomEntities.CHAINGUNNER.get(), ChaingunnerRender::new);
		event.registerEntityRenderer(DoomEntities.SHOTGUNGUY.get(), ShotgunguyRender::new);
		event.registerEntityRenderer(DoomEntities.MARAUDER.get(), MarauderRender::new);
		event.registerEntityRenderer(DoomEntities.PAIN.get(), PainRender::new);
		event.registerEntityRenderer(DoomEntities.HELLKNIGHT.get(), HellknightRender::new);
		event.registerEntityRenderer(DoomEntities.CYBERDEMON.get(), CyberdemonRender::new);
		event.registerEntityRenderer(DoomEntities.UNWILLING.get(), UnwillingRender::new);
		event.registerEntityRenderer(DoomEntities.ICONOFSIN.get(), IconofsinRender::new);
		event.registerEntityRenderer(DoomEntities.POSSESSEDSCIENTIST.get(), PossessedScientistRender::new);
		event.registerEntityRenderer(DoomEntities.POSSESSEDSOLDIER.get(), PossessedSoldierRender::new);
		event.registerEntityRenderer(DoomProjectiles.ENERGY_CELL_MOB.get(), EnergyCellMobRender::new);
		event.registerEntityRenderer(DoomProjectiles.ENERGY_CELL.get(), EnergyCellRender::new);
		event.registerEntityRenderer(DoomProjectiles.ROCKET_MOB.get(), RocketMobRender::new);
		event.registerEntityRenderer(DoomProjectiles.CHAINGUN_MOB.get(), ChaingunMobRender::new);
		event.registerEntityRenderer(DoomEntities.GORE_NEST.get(), GoreNestRender::new);
		event.registerEntityRenderer(DoomEntities.MECHAZOMBIE.get(), MechaZombieRender::new);
		event.registerEntityRenderer(DoomEntities.GARGOYLE.get(), GargoyleRender::new);
		event.registerEntityRenderer(DoomEntities.HELLKNIGHT2016.get(), Hellknight2016Render::new);
		event.registerEntityRenderer(DoomProjectiles.FIRING.get(), ArchvileFiringRender::new);
		event.registerEntityRenderer(DoomEntities.SPECTRE.get(), SpectreRender::new);
		event.registerEntityRenderer(DoomEntities.CUEBALL.get(), CueBallRender::new);
		event.registerEntityRenderer(DoomEntities.PROWLER.get(), ProwlerRender::new);
		event.registerEntityRenderer(DoomEntities.DREADKNIGHT.get(), DreadKnightRender::new);
		event.registerEntityRenderer(DoomEntities.IMP_STONE.get(), ImpStoneRender::new);
		event.registerEntityRenderer(DoomEntities.POSSESSEDWORKER.get(), PossessedWorkerRender::new);
		event.registerEntityRenderer(DoomEntities.DOOMHUNTER.get(), DoomHunterRender::new);
		event.registerEntityRenderer(DoomEntities.WHIPLASH.get(), WhiplashRender::new);
		event.registerEntityRenderer(DoomEntities.BARON2016.get(), Baron2016Render::new);
		event.registerEntityRenderer(DoomEntities.FIREBARON.get(), FireBaronRender::new);
		event.registerEntityRenderer(DoomEntities.ARMORBARON.get(), ArmoredBaronRender::new);
		event.registerEntityRenderer(DoomEntities.MAYKRDRONE.get(), MaykrDroneRender::new);
		event.registerEntityRenderer(DoomEntities.BLOODMAYKR.get(), BloodMaykrRender::new);
		event.registerEntityRenderer(DoomEntities.ARCHMAKER.get(), ArchMaykrRender::new);
		event.registerEntityRenderer(DoomEntities.SPIDERMASTERMIND2016.get(), SpiderMastermind2016Render::new);
		event.registerEntityRenderer(DoomEntities.ARACHNOTRONETERNAL.get(), ArachonotronEternalRender::new);
		event.registerEntityRenderer(DoomEntities.TENTACLE.get(), TentacleRender::new);
		event.registerEntityRenderer(DoomEntities.MOTHERDEMON.get(), MotherDemonRender::new);
		event.registerEntityRenderer(DoomEntities.TURRET.get(), TurretRender::new);
		event.registerEntityRenderer(DoomEntities.SUMMONER.get(), SummonerRender::new);
		event.registerEntityRenderer(DoomEntities.REVENANT2016.get(), Revenant2016Render::new);
		event.registerEntityRenderer(DoomEntities.GLADIATOR.get(), GladiatorRender::new);
		event.registerEntityRenderer(DoomEntities.CARCASS.get(), CarcassRender::new);

		event.registerEntityRenderer(DoomProjectiles.BARREL.get(), BarrelRender::new);
		event.registerBlockEntityRenderer(DoomEntities.TOTEM.get(), context -> new TotemRender());
		event.registerBlockEntityRenderer(DoomEntities.GUN_TABLE_ENTITY.get(), context -> new GunCraftingRender());
	}

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(DoomBlocks.JUMP_PAD.get(), RenderType.translucent());
		MenuScreens.register(DoomScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
		// Crucible
		ItemProperties.register(DoomItems.CRUCIBLESWORD.get(), new ResourceLocation("broken"), (itemStack, clientWorld, livingEntity, seed) -> (isUsable(itemStack) ? 0.0F : 1.0F));
		// Marauder Axe
		ItemProperties.register(DoomItems.AXE_OPEN.get(), new ResourceLocation("broken"), (itemStack, clientWorld, livingEntity, seed) -> (isUsable(itemStack) ? 0.0F : 1.0F));
		// NonCenter
		ItemProperties.register(DoomItems.SG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.ROCKETLAUNCHER.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.PLASMAGUN.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.HEAVYCANNON.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.UNMAKER.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.UNMAYKR.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.CHAINGUN.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.BFG_ETERNAL.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.BALLISTA.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.SSG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.PISTOL.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.DPLASMARIFLE.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.DGAUSS.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.DSG.get(), new ResourceLocation("nocenter"), (itemStack, clientWorld, livingEntity, seed) -> (nonCentered(itemStack) ? 1.0F : 0.0F));
		ItemProperties.register(DoomItems.CHAINSAW.get(), new ResourceLocation("stalled"), (itemStack, clientWorld, livingEntity, seed) -> (isUsable(itemStack) ? 0.0F : 1.0F));
		ItemProperties.register(DoomItems.CHAINSAW64.get(), new ResourceLocation("stalled"), (itemStack, clientWorld, livingEntity, seed) -> (isUsable(itemStack) ? 0.0F : 1.0F));
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}

	public static boolean nonCentered(ItemStack stack) {
		return DoomMod.config.enable_noncenter;
	}
}