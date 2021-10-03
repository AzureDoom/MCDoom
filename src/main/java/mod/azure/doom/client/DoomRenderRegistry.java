package mod.azure.doom.client;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.render.ArachonotronEternalRender;
import mod.azure.doom.client.render.ArachonotronRender;
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
import mod.azure.doom.client.render.projectiles.ArgentBoltRender;
import mod.azure.doom.client.render.projectiles.BFGCellRender;
import mod.azure.doom.client.render.projectiles.BarenBlastRender;
import mod.azure.doom.client.render.projectiles.BulletsRender;
import mod.azure.doom.client.render.projectiles.ChaingunBulletRender;
import mod.azure.doom.client.render.projectiles.EnergyCellRender;
import mod.azure.doom.client.render.projectiles.RocketRender;
import mod.azure.doom.client.render.projectiles.ShotgunShellRender;
import mod.azure.doom.client.render.projectiles.UnmaykrBulletRender;
import mod.azure.doom.client.render.projectiles.entity.ArchvileFiringRender;
import mod.azure.doom.client.render.projectiles.entity.BloodBoltRender;
import mod.azure.doom.client.render.projectiles.entity.ChainBladeRender;
import mod.azure.doom.client.render.projectiles.entity.ChaingunMobRender;
import mod.azure.doom.client.render.projectiles.entity.DroneBoltRender;
import mod.azure.doom.client.render.projectiles.entity.EnergyCellMobRender;
import mod.azure.doom.client.render.projectiles.entity.RocketMobRender;
import mod.azure.doom.client.render.tile.GunCraftingRender;
import mod.azure.doom.client.render.tile.TotemRender;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class DoomRenderRegistry {

	@SuppressWarnings("unchecked")
	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHVILE, (ctx) -> new ArchvileRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARREL, (ctx) -> new BarrelRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP, (ctx) -> new ImpRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PINKY, (ctx) -> new PinkyRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPECTRE, (ctx) -> new SpectreRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.LOST_SOUL, (ctx) -> new LostSoulRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CACODEMON, (ctx) -> new CacodemonRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARON, (ctx) -> new BaronRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MANCUBUS, (ctx) -> new MancubusRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPIDERMASTERMIND,
				(ctx) -> new SpiderMastermindRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARACHNOTRON, (ctx) -> new ArachonotronRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ZOMBIEMAN, (ctx) -> new ZombiemanRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.REVENANT, (ctx) -> new RevenantRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP2016, (ctx) -> new Imp2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GORE_NEST, (ctx) -> new GoreNestRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.NIGHTMARE_IMP, (ctx) -> new NightmareImpRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CHAINGUNNER, (ctx) -> new ChaingunnerRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SHOTGUNGUY, (ctx) -> new ShotgunguyRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MARAUDER, (ctx) -> new MarauderRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PAIN, (ctx) -> new PainRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.HELLKNIGHT, (ctx) -> new HellknightRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.HELLKNIGHT2016, (ctx) -> new Hellknight2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CYBERDEMON, (ctx) -> new CyberdemonRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.UNWILLING, (ctx) -> new UnwillingRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CYBERDEMON2016, (ctx) -> new Cyberdemon2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ICONOFSIN, (ctx) -> new IconofsinRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDSCIENTIST,
				(ctx) -> new PossessedScientistRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDSOLDIER,
				(ctx) -> new PossessedSoldierRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GARGOYLE, (ctx) -> new GargoyleRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MECHAZOMBIE, (ctx) -> new MechaZombieRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CUEBALL, (ctx) -> new CueBallRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PROWLER, (ctx) -> new ProwlerRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.DREADKNIGHT, (ctx) -> new DreadKnightRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP_STONE, (ctx) -> new ImpStoneRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TYRANT, (ctx) -> new TyrantRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDWORKER,
				(ctx) -> new PossessedWorkerRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.DOOMHUNTER, (ctx) -> new DoomHunterRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MAYKRDRONE, (ctx) -> new MaykrDroneRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PINKY2016, (ctx) -> new Pinky2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.WHIPLASH, (ctx) -> new WhiplashRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARON2016, (ctx) -> new Baron2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.FIREBARON, (ctx) -> new FireBaronRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARMORBARON, (ctx) -> new ArmoredBaronRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BLOODMAYKR, (ctx) -> new BloodMaykrRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHMAKER, (ctx) -> new ArchMaykrRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARACHNOTRONETERNAL,
				(ctx) -> new ArachonotronEternalRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPIDERMASTERMIND2016,
				(ctx) -> new SpiderMastermind2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHVILEETERNAL,
				(ctx) -> new ArchvileEternalRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TENTACLE, (ctx) -> new TentacleRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TURRET, (ctx) -> new TurretRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MOTHERDEMON, (ctx) -> new MotherDemonRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SUMMONER, (ctx) -> new SummonerRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.REVENANT2016, (ctx) -> new Revenant2016Render(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ARGENT_BOLT,
				(ctx) -> new ArgentBoltRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SHOTGUN_SHELL,
				(ctx) -> new ShotgunShellRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ENERGY_CELL,
				(ctx) -> new EnergyCellRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BFG_CELL, (ctx) -> new BFGCellRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKET, (ctx) -> new RocketRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BARENBLAST,
				(ctx) -> new BarenBlastRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (ctx) -> new BulletsRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINGUN_BULLET,
				(ctx) -> new ChaingunBulletRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.UNMAYKR,
				(ctx) -> new UnmaykrBulletRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKET_MOB,
				(ctx) -> new RocketMobRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ENERGY_CELL_MOB,
				(ctx) -> new EnergyCellMobRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINGUN_MOB,
				(ctx) -> new ChaingunMobRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIRING,
				(ctx) -> new ArchvileFiringRender(ctx));

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINBLADE,
				(ctx) -> new ChainBladeRender(ctx));
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.DRONEBOLT_MOB,
				(ctx) -> new DroneBoltRender(ctx));
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLOODBOLT_MOB,
				(ctx) -> new BloodBoltRender(ctx));

		BlockEntityRendererRegistry.INSTANCE.register(DoomMod.TOTEM,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new TotemRender());
		BlockEntityRendererRegistry.INSTANCE.register(DoomMod.GUN_TABLE_ENTITY,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new GunCraftingRender());

//		GeoArmorRenderer.registerArmorRenderer(DoomicornDoomArmor.class, new DoomicornRender());
//		GeoArmorRenderer.registerArmorRenderer(NightmareDoomArmor.class, new NightmareRender());
//		GeoArmorRenderer.registerArmorRenderer(PurplePonyDoomArmor.class, new PurplePonyRender());
//		GeoArmorRenderer.registerArmorRenderer(DoomArmor.class, new DoomRender());
//		GeoArmorRenderer.registerArmorRenderer(AstroDoomArmor.class, new AstroRender());
//		GeoArmorRenderer.registerArmorRenderer(BronzeDoomArmor.class, new BronzeRender());
//		GeoArmorRenderer.registerArmorRenderer(CrimsonDoomArmor.class, new CrimsonRender());
//		GeoArmorRenderer.registerArmorRenderer(DemoncideDoomArmor.class, new DemoncideRender());
//		GeoArmorRenderer.registerArmorRenderer(DemonicDoomArmor.class, new DemonicRender());
//		GeoArmorRenderer.registerArmorRenderer(EmberDoomArmor.class, new EmberRender());
//		GeoArmorRenderer.registerArmorRenderer(GoldDoomArmor.class, new GoldRender());
//		GeoArmorRenderer.registerArmorRenderer(HotrodDoomArmor.class, new HotrodRender());
//		GeoArmorRenderer.registerArmorRenderer(MidnightDoomArmor.class, new MidnightRender());
//		GeoArmorRenderer.registerArmorRenderer(PhobosDoomArmor.class, new PhobosRender());
//		GeoArmorRenderer.registerArmorRenderer(PraetorDoomArmor.class, new PraetorRender());
//		GeoArmorRenderer.registerArmorRenderer(TwentyFiveDoomArmor.class, new TwentyFiveRender());
//		GeoArmorRenderer.registerArmorRenderer(ClassicBronzeDoomArmor.class, new ClassicBronzeRender());
//		GeoArmorRenderer.registerArmorRenderer(ClassicDoomArmor.class, new ClassicRender());
//		GeoArmorRenderer.registerArmorRenderer(ClassicIndigoDoomArmor.class, new ClassicIndigoRender());
//		GeoArmorRenderer.registerArmorRenderer(ClassicRedDoomArmor.class, new ClassicRedRender());
//		GeoArmorRenderer.registerArmorRenderer(MulletDoomArmor.class, new Mullet1Render());
//		GeoArmorRenderer.registerArmorRenderer(Mullet2DoomArmor.class, new Mullet2Render());
//		GeoArmorRenderer.registerArmorRenderer(Mullet3DoomArmor.class, new Mullet3Render());
//		GeoArmorRenderer.registerArmorRenderer(PainterDoomArmor.class, new PainterRender());
//		GeoArmorRenderer.registerArmorRenderer(CultistDoomArmor.class, new CultistRender());
//		GeoArmorRenderer.registerArmorRenderer(MaykrDoomArmor.class, new MaykrRender());
//		GeoArmorRenderer.registerArmorRenderer(SentinelDoomArmor.class, new SentinelRender());
//		GeoArmorRenderer.registerArmorRenderer(ZombieDoomArmor.class, new ZombieRender());
//		GeoArmorRenderer.registerArmorRenderer(SantaDoomArmor.class, new SantaRender());

		BlockRenderLayerMap.INSTANCE.putBlock(DoomBlocks.JUMP_PAD, RenderLayer.getTranslucent());
	}
}