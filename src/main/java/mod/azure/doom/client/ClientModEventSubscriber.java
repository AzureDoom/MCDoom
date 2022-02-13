package mod.azure.doom.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.weapons.GunTableScreen;
import mod.azure.doom.client.render.ArachnotronEternalRender;
import mod.azure.doom.client.render.ArachnotronRender;
import mod.azure.doom.client.render.ArchMaykrRender;
import mod.azure.doom.client.render.ArchvileEternalRender;
import mod.azure.doom.client.render.ArchvileRender;
import mod.azure.doom.client.render.ArmoredBaronRender;
import mod.azure.doom.client.render.Baron2016Render;
import mod.azure.doom.client.render.BaronRender;
import mod.azure.doom.client.render.BarrelRender;
import mod.azure.doom.client.render.BloodMaykrRender;
import mod.azure.doom.client.render.CacodemonRender;
import mod.azure.doom.client.render.ChaingunnerRender;
import mod.azure.doom.client.render.CueBallRender;
import mod.azure.doom.client.render.Cyberdemon2016Render;
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
import mod.azure.doom.client.render.Imp2016Render;
import mod.azure.doom.client.render.ImpRender;
import mod.azure.doom.client.render.ImpStoneRender;
import mod.azure.doom.client.render.LostSoulRender;
import mod.azure.doom.client.render.MancubusRender;
import mod.azure.doom.client.render.MarauderRender;
import mod.azure.doom.client.render.MaykrDroneRender;
import mod.azure.doom.client.render.MechaZombieRender;
import mod.azure.doom.client.render.MotherDemonRender;
import mod.azure.doom.client.render.NightmareImpRender;
import mod.azure.doom.client.render.PainRender;
import mod.azure.doom.client.render.Pinky2016Render;
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
import mod.azure.doom.client.render.TyrantRender;
import mod.azure.doom.client.render.UnwillingRender;
import mod.azure.doom.client.render.WhiplashRender;
import mod.azure.doom.client.render.ZombiemanRender;
import mod.azure.doom.client.render.armors.AstroRender;
import mod.azure.doom.client.render.armors.BronzeRender;
import mod.azure.doom.client.render.armors.ClassicBronzeRender;
import mod.azure.doom.client.render.armors.ClassicIndigoRender;
import mod.azure.doom.client.render.armors.ClassicRedRender;
import mod.azure.doom.client.render.armors.ClassicRender;
import mod.azure.doom.client.render.armors.CrimsonRender;
import mod.azure.doom.client.render.armors.CultistRender;
import mod.azure.doom.client.render.armors.DarkLordArmorRender;
import mod.azure.doom.client.render.armors.DemoncideRender;
import mod.azure.doom.client.render.armors.DemonicRender;
import mod.azure.doom.client.render.armors.DoomRender;
import mod.azure.doom.client.render.armors.DoomicornRender;
import mod.azure.doom.client.render.armors.EmberRender;
import mod.azure.doom.client.render.armors.GoldRender;
import mod.azure.doom.client.render.armors.HotrodRender;
import mod.azure.doom.client.render.armors.MaykrRender;
import mod.azure.doom.client.render.armors.MidnightRender;
import mod.azure.doom.client.render.armors.Mullet1Render;
import mod.azure.doom.client.render.armors.Mullet2Render;
import mod.azure.doom.client.render.armors.Mullet3Render;
import mod.azure.doom.client.render.armors.NightmareRender;
import mod.azure.doom.client.render.armors.PainterRender;
import mod.azure.doom.client.render.armors.PhobosRender;
import mod.azure.doom.client.render.armors.PraetorRender;
import mod.azure.doom.client.render.armors.PurplePonyRender;
import mod.azure.doom.client.render.armors.SantaRender;
import mod.azure.doom.client.render.armors.SentinelRender;
import mod.azure.doom.client.render.armors.TwentyFiveRender;
import mod.azure.doom.client.render.armors.ZombieRender;
import mod.azure.doom.client.render.projectiles.ArgentBoltRender;
import mod.azure.doom.client.render.projectiles.BFGCellRender;
import mod.azure.doom.client.render.projectiles.BarenBlastRender;
import mod.azure.doom.client.render.projectiles.BulletsRender;
import mod.azure.doom.client.render.projectiles.ChaingunBulletRender;
import mod.azure.doom.client.render.projectiles.EnergyRender;
import mod.azure.doom.client.render.projectiles.GrenadeRender;
import mod.azure.doom.client.render.projectiles.RocketRender;
import mod.azure.doom.client.render.projectiles.ShotgunShellRender;
import mod.azure.doom.client.render.projectiles.UnmaykrBulletRender;
import mod.azure.doom.client.render.projectiles.entity.ArchvileFiringRender;
import mod.azure.doom.client.render.projectiles.entity.BloodBoltRender;
import mod.azure.doom.client.render.projectiles.entity.ChainBladeRender;
import mod.azure.doom.client.render.projectiles.entity.ChaingunMobRender;
import mod.azure.doom.client.render.projectiles.entity.DroneBoltRender;
import mod.azure.doom.client.render.projectiles.entity.EnergyCellMobRender;
import mod.azure.doom.client.render.projectiles.entity.GladiatorMaceRender;
import mod.azure.doom.client.render.projectiles.entity.RocketMobRender;
import mod.azure.doom.client.render.tile.GunCraftingRender;
import mod.azure.doom.client.render.tile.TotemRender;
import mod.azure.doom.item.armor.AstroDoomArmor;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import mod.azure.doom.item.armor.CultistDoomArmor;
import mod.azure.doom.item.armor.DarkLordArmor;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import mod.azure.doom.item.armor.DoomArmor;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import mod.azure.doom.item.armor.EmberDoomArmor;
import mod.azure.doom.item.armor.GoldDoomArmor;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import mod.azure.doom.item.armor.MulletDoomArmor;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import mod.azure.doom.item.armor.PainterDoomArmor;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import mod.azure.doom.item.armor.SantaDoomArmor;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomScreens;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@EventBusSubscriber(modid = DoomMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntityTypes.GLADIATOR_MACE.get(), GladiatorMaceRender::new);
		event.registerEntityRenderer(ModEntityTypes.SHOTGUN_SHELL.get(), ShotgunShellRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARGENT_BOLT.get(), ArgentBoltRender::new);
		event.registerEntityRenderer(ModEntityTypes.GRENADE.get(), GrenadeRender::new);
		event.registerEntityRenderer(ModEntityTypes.DRONEBOLT_MOB.get(), DroneBoltRender::new);
		event.registerEntityRenderer(ModEntityTypes.UNMAYKR.get(), UnmaykrBulletRender::new);
		event.registerEntityRenderer(ModEntityTypes.BULLETS.get(), BulletsRender::new);
		event.registerEntityRenderer(ModEntityTypes.BFG_CELL.get(), BFGCellRender::new);
		event.registerEntityRenderer(ModEntityTypes.ROCKET.get(), RocketRender::new);
		event.registerEntityRenderer(ModEntityTypes.CHAINGUN_BULLET.get(), ChaingunBulletRender::new);
		event.registerEntityRenderer(ModEntityTypes.BLOODBOLT_MOB.get(), BloodBoltRender::new);
		event.registerEntityRenderer(ModEntityTypes.BARENBLAST.get(), BarenBlastRender::new);
		event.registerEntityRenderer(ModEntityTypes.LOST_SOUL.get(), LostSoulRender::new);
		event.registerEntityRenderer(ModEntityTypes.IMP.get(), ImpRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARACHNOTRON.get(), ArachnotronRender::new);
		event.registerEntityRenderer(ModEntityTypes.NIGHTMARE_IMP.get(), NightmareImpRender::new);
		event.registerEntityRenderer(ModEntityTypes.PINKY.get(), PinkyRender::new);
		event.registerEntityRenderer(ModEntityTypes.CACODEMON.get(), CacodemonRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARCHVILE.get(), ArchvileRender::new);
		event.registerEntityRenderer(ModEntityTypes.BARON.get(), BaronRender::new);
		event.registerEntityRenderer(ModEntityTypes.MANCUBUS.get(), MancubusRender::new);
		event.registerEntityRenderer(ModEntityTypes.SPIDERMASTERMIND.get(), SpiderMastermindRender::new);
		event.registerEntityRenderer(ModEntityTypes.ZOMBIEMAN.get(), ZombiemanRender::new);
		event.registerEntityRenderer(ModEntityTypes.REVENANT.get(), RevenantRender::new);
		event.registerEntityRenderer(ModEntityTypes.IMP2016.get(), Imp2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.CHAINGUNNER.get(), ChaingunnerRender::new);
		event.registerEntityRenderer(ModEntityTypes.SHOTGUNGUY.get(), ShotgunguyRender::new);
		event.registerEntityRenderer(ModEntityTypes.MARAUDER.get(), MarauderRender::new);
		event.registerEntityRenderer(ModEntityTypes.PAIN.get(), PainRender::new);
		event.registerEntityRenderer(ModEntityTypes.HELLKNIGHT.get(), HellknightRender::new);
		event.registerEntityRenderer(ModEntityTypes.CYBERDEMON.get(), CyberdemonRender::new);
		event.registerEntityRenderer(ModEntityTypes.UNWILLING.get(), UnwillingRender::new);
		event.registerEntityRenderer(ModEntityTypes.CYBERDEMON2016.get(), Cyberdemon2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.ICONOFSIN.get(), IconofsinRender::new);
		event.registerEntityRenderer(ModEntityTypes.POSSESSEDSCIENTIST.get(), PossessedScientistRender::new);
		event.registerEntityRenderer(ModEntityTypes.POSSESSEDSOLDIER.get(), PossessedSoldierRender::new);
		event.registerEntityRenderer(ModEntityTypes.ENERGY_CELL_MOB.get(), EnergyCellMobRender::new);
		event.registerEntityRenderer(ModEntityTypes.ENERGY_CELL.get(), EnergyRender::new);
		event.registerEntityRenderer(ModEntityTypes.ROCKET_MOB.get(), RocketMobRender::new);
		event.registerEntityRenderer(ModEntityTypes.CHAINGUN_MOB.get(), ChaingunMobRender::new);
		event.registerEntityRenderer(ModEntityTypes.GORE_NEST.get(), GoreNestRender::new);
		event.registerEntityRenderer(ModEntityTypes.MECHAZOMBIE.get(), MechaZombieRender::new);
		event.registerEntityRenderer(ModEntityTypes.GARGOYLE.get(), GargoyleRender::new);
		event.registerEntityRenderer(ModEntityTypes.HELLKNIGHT2016.get(), Hellknight2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.FIRING.get(), ArchvileFiringRender::new);
		event.registerEntityRenderer(ModEntityTypes.CHAINBLADE.get(), ChainBladeRender::new);
		event.registerEntityRenderer(ModEntityTypes.SPECTRE.get(), SpectreRender::new);
		event.registerEntityRenderer(ModEntityTypes.CUEBALL.get(), CueBallRender::new);
		event.registerEntityRenderer(ModEntityTypes.PROWLER.get(), ProwlerRender::new);
		event.registerEntityRenderer(ModEntityTypes.DREADKNIGHT.get(), DreadKnightRender::new);
		event.registerEntityRenderer(ModEntityTypes.IMP_STONE.get(), ImpStoneRender::new);
		event.registerEntityRenderer(ModEntityTypes.TYRANT.get(), TyrantRender::new);
		event.registerEntityRenderer(ModEntityTypes.POSSESSEDWORKER.get(), PossessedWorkerRender::new);
		event.registerEntityRenderer(ModEntityTypes.DOOMHUNTER.get(), DoomHunterRender::new);
		event.registerEntityRenderer(ModEntityTypes.PINKY2016.get(), Pinky2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.WHIPLASH.get(), WhiplashRender::new);
		event.registerEntityRenderer(ModEntityTypes.BARON2016.get(), Baron2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.FIREBARON.get(), FireBaronRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARMORBARON.get(), ArmoredBaronRender::new);
		event.registerEntityRenderer(ModEntityTypes.MAYKRDRONE.get(), MaykrDroneRender::new);
		event.registerEntityRenderer(ModEntityTypes.BLOODMAYKR.get(), BloodMaykrRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARCHMAKER.get(), ArchMaykrRender::new);
		event.registerEntityRenderer(ModEntityTypes.SPIDERMASTERMIND2016.get(), SpiderMastermind2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.ARACHNOTRONETERNAL.get(), ArachnotronEternalRender::new);
		event.registerEntityRenderer(ModEntityTypes.ARCHVILEETERNAL.get(), ArchvileEternalRender::new);
		event.registerEntityRenderer(ModEntityTypes.TENTACLE.get(), TentacleRender::new);
		event.registerEntityRenderer(ModEntityTypes.MOTHERDEMON.get(), MotherDemonRender::new);
		event.registerEntityRenderer(ModEntityTypes.TURRET.get(), TurretRender::new);
		event.registerEntityRenderer(ModEntityTypes.SUMMONER.get(), SummonerRender::new);
		event.registerEntityRenderer(ModEntityTypes.REVENANT2016.get(), Revenant2016Render::new);
		event.registerEntityRenderer(ModEntityTypes.GLADIATOR.get(), GladiatorRender::new);

		event.registerEntityRenderer(ModEntityTypes.BARREL.get(), BarrelRender::new);
		event.registerBlockEntityRenderer(ModEntityTypes.TOTEM.get(), TotemRender::new);
		event.registerBlockEntityRenderer(ModEntityTypes.GUN_TABLE_ENTITY.get(), GunCraftingRender::new);
	}

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		Keybindings.RELOAD = new KeyMapping("key." + DoomMod.MODID + ".reload", GLFW.GLFW_KEY_R,
				"key.categories." + DoomMod.MODID);
		ClientRegistry.registerKeyBinding(Keybindings.RELOAD);
		ItemBlockRenderTypes.setRenderLayer(DoomBlocks.JUMP_PAD.get(), RenderType.translucent());
		MenuScreens.register(DoomScreens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
		// Crucible
		ItemProperties.register(DoomItems.CRUCIBLESWORD.get(), new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		ItemProperties.register(DoomItems.AXE_OPEN.get(), new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// NonCenter
		ItemProperties.register(DoomItems.SG.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.ROCKETLAUNCHER.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.PLASMAGUN.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.HEAVYCANNON.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.UNMAYKR.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.CHAINGUN.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.BFG_ETERNAL.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.BALLISTA.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.SSG.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.PISTOL.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DPLASMARIFLE.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DGAUSS.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DSG.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.CHAINSAW.get(), new ResourceLocation("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		ItemProperties.register(DoomItems.CHAINSAW64.get(), new ResourceLocation("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}

	public static boolean nonCentered(ItemStack stack) {
		return DoomConfig.SERVER.enable_noncenter.get();
	}

	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {

		GeoArmorRenderer.registerArmorRenderer(DoomicornDoomArmor.class, new DoomicornRender());
		GeoArmorRenderer.registerArmorRenderer(NightmareDoomArmor.class, new NightmareRender());
		GeoArmorRenderer.registerArmorRenderer(PurplePonyDoomArmor.class, new PurplePonyRender());
		GeoArmorRenderer.registerArmorRenderer(DoomArmor.class, new DoomRender());
		GeoArmorRenderer.registerArmorRenderer(AstroDoomArmor.class, new AstroRender());
		GeoArmorRenderer.registerArmorRenderer(BronzeDoomArmor.class, new BronzeRender());
		GeoArmorRenderer.registerArmorRenderer(CrimsonDoomArmor.class, new CrimsonRender());
		GeoArmorRenderer.registerArmorRenderer(DemoncideDoomArmor.class, new DemoncideRender());
		GeoArmorRenderer.registerArmorRenderer(DemonicDoomArmor.class, new DemonicRender());
		GeoArmorRenderer.registerArmorRenderer(EmberDoomArmor.class, new EmberRender());
		GeoArmorRenderer.registerArmorRenderer(GoldDoomArmor.class, new GoldRender());
		GeoArmorRenderer.registerArmorRenderer(HotrodDoomArmor.class, new HotrodRender());
		GeoArmorRenderer.registerArmorRenderer(MidnightDoomArmor.class, new MidnightRender());
		GeoArmorRenderer.registerArmorRenderer(PhobosDoomArmor.class, new PhobosRender());
		GeoArmorRenderer.registerArmorRenderer(PraetorDoomArmor.class, new PraetorRender());
		GeoArmorRenderer.registerArmorRenderer(TwentyFiveDoomArmor.class, new TwentyFiveRender());
		GeoArmorRenderer.registerArmorRenderer(ClassicBronzeDoomArmor.class, new ClassicBronzeRender());
		GeoArmorRenderer.registerArmorRenderer(ClassicDoomArmor.class, new ClassicRender());
		GeoArmorRenderer.registerArmorRenderer(ClassicIndigoDoomArmor.class, new ClassicIndigoRender());
		GeoArmorRenderer.registerArmorRenderer(ClassicRedDoomArmor.class, new ClassicRedRender());
		GeoArmorRenderer.registerArmorRenderer(MulletDoomArmor.class, new Mullet1Render());
		GeoArmorRenderer.registerArmorRenderer(Mullet2DoomArmor.class, new Mullet2Render());
		GeoArmorRenderer.registerArmorRenderer(Mullet3DoomArmor.class, new Mullet3Render());
		GeoArmorRenderer.registerArmorRenderer(PainterDoomArmor.class, new PainterRender());
		GeoArmorRenderer.registerArmorRenderer(CultistDoomArmor.class, new CultistRender());
		GeoArmorRenderer.registerArmorRenderer(MaykrDoomArmor.class, new MaykrRender());
		GeoArmorRenderer.registerArmorRenderer(SentinelDoomArmor.class, new SentinelRender());
		GeoArmorRenderer.registerArmorRenderer(ZombieDoomArmor.class, new ZombieRender());
		GeoArmorRenderer.registerArmorRenderer(SantaDoomArmor.class, new SantaRender());
		GeoArmorRenderer.registerArmorRenderer(DarkLordArmor.class, new DarkLordArmorRender());

	}
}