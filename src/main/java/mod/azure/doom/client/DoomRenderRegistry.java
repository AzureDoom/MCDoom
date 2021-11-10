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
import mod.azure.doom.client.render.armors.AstroRender;
import mod.azure.doom.client.render.armors.BronzeRender;
import mod.azure.doom.client.render.armors.ClassicBronzeRender;
import mod.azure.doom.client.render.armors.ClassicIndigoRender;
import mod.azure.doom.client.render.armors.ClassicRedRender;
import mod.azure.doom.client.render.armors.ClassicRender;
import mod.azure.doom.client.render.armors.CrimsonRender;
import mod.azure.doom.client.render.armors.CultistRender;
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
import mod.azure.doom.item.armor.AstroDoomArmor;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import mod.azure.doom.item.armor.CultistDoomArmor;
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
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class DoomRenderRegistry {

	public static void init() {
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHVILE, (dispatcher, context) -> {
			return new ArchvileRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARREL, (dispatcher, context) -> {
			return new BarrelRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP, (dispatcher, context) -> {
			return new ImpRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PINKY, (dispatcher, context) -> {
			return new PinkyRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPECTRE, (dispatcher, context) -> {
			return new SpectreRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.LOST_SOUL, (dispatcher, context) -> {
			return new LostSoulRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CACODEMON, (dispatcher, context) -> {
			return new CacodemonRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARON, (dispatcher, context) -> {
			return new BaronRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MANCUBUS, (dispatcher, context) -> {
			return new MancubusRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPIDERMASTERMIND, (dispatcher, context) -> {
			return new SpiderMastermindRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARACHNOTRON, (dispatcher, context) -> {
			return new ArachonotronRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ZOMBIEMAN, (dispatcher, context) -> {
			return new ZombiemanRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.REVENANT, (dispatcher, context) -> {
			return new RevenantRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP2016, (dispatcher, context) -> {
			return new Imp2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GORE_NEST, (dispatcher, context) -> {
			return new GoreNestRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.NIGHTMARE_IMP, (dispatcher, context) -> {
			return new NightmareImpRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CHAINGUNNER, (dispatcher, context) -> {
			return new ChaingunnerRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SHOTGUNGUY, (dispatcher, context) -> {
			return new ShotgunguyRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MARAUDER, (dispatcher, context) -> {
			return new MarauderRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PAIN, (dispatcher, context) -> {
			return new PainRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.HELLKNIGHT, (dispatcher, context) -> {
			return new HellknightRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.HELLKNIGHT2016, (dispatcher, context) -> {
			return new Hellknight2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CYBERDEMON, (dispatcher, context) -> {
			return new CyberdemonRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.UNWILLING, (dispatcher, context) -> {
			return new UnwillingRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CYBERDEMON2016, (dispatcher, context) -> {
			return new Cyberdemon2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ICONOFSIN, (dispatcher, context) -> {
			return new IconofsinRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDSCIENTIST, (dispatcher, context) -> {
			return new PossessedScientistRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDSOLDIER, (dispatcher, context) -> {
			return new PossessedSoldierRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GARGOYLE, (dispatcher, context) -> {
			return new GargoyleRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MECHAZOMBIE, (dispatcher, context) -> {
			return new MechaZombieRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.CUEBALL, (dispatcher, context) -> {
			return new CueBallRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PROWLER, (dispatcher, context) -> {
			return new ProwlerRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.DREADKNIGHT, (dispatcher, context) -> {
			return new DreadKnightRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.IMP_STONE, (dispatcher, context) -> {
			return new ImpStoneRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TYRANT, (dispatcher, context) -> {
			return new TyrantRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.POSSESSEDWORKER, (dispatcher, context) -> {
			return new PossessedWorkerRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.DOOMHUNTER, (dispatcher, context) -> {
			return new DoomHunterRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MAYKRDRONE, (dispatcher, context) -> {
			return new MaykrDroneRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.PINKY2016, (dispatcher, context) -> {
			return new Pinky2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.WHIPLASH, (dispatcher, context) -> {
			return new WhiplashRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BARON2016, (dispatcher, context) -> {
			return new Baron2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.FIREBARON, (dispatcher, context) -> {
			return new FireBaronRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARMORBARON, (dispatcher, context) -> {
			return new ArmoredBaronRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BLOODMAYKR, (dispatcher, context) -> {
			return new BloodMaykrRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHMAKER, (dispatcher, context) -> {
			return new ArchMaykrRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARACHNOTRONETERNAL, (dispatcher, context) -> {
			return new ArachonotronEternalRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SPIDERMASTERMIND2016, (dispatcher, context) -> {
			return new SpiderMastermind2016Render(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ARCHVILEETERNAL, (dispatcher, context) -> {
			return new ArchvileEternalRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TENTACLE, (dispatcher, context) -> {
			return new TentacleRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.MOTHERDEMON, (dispatcher, context) -> {
			return new MotherDemonRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TURRET, (dispatcher, context) -> {
			return new TurretRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.SUMMONER, (dispatcher, context) -> {
			return new SummonerRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.REVENANT2016, (dispatcher, context) -> {
			return new Revenant2016Render(dispatcher);
		});
//		EntityRendererRegistry.INSTANCE.register(ModEntityTypes.GLADIATOR, (dispatcher, context) -> {
//			return new GladiatorRender(dispatcher);
//		});

		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ARGENT_BOLT, (dispatcher, context) -> {
			return new ArgentBoltRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.SHOTGUN_SHELL, (dispatcher, context) -> {
			return new ShotgunShellRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ENERGY_CELL, (dispatcher, context) -> {
			return new EnergyCellRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BFG_CELL, (dispatcher, context) -> {
			return new BFGCellRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKET, (dispatcher, context) -> {
			return new RocketRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BARENBLAST, (dispatcher, context) -> {
			return new BarenBlastRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BULLETS, (dispatcher, context) -> {
			return new BulletsRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINGUN_BULLET, (dispatcher, context) -> {
			return new ChaingunBulletRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.UNMAYKR, (dispatcher, context) -> {
			return new UnmaykrBulletRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ROCKET_MOB, (dispatcher, context) -> {
			return new RocketMobRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.ENERGY_CELL_MOB, (dispatcher, context) -> {
			return new EnergyCellMobRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINGUN_MOB, (dispatcher, context) -> {
			return new ChaingunMobRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.FIRING, (dispatcher, context) -> {
			return new ArchvileFiringRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.CHAINBLADE, (dispatcher, context) -> {
			return new ChainBladeRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.DRONEBOLT_MOB, (dispatcher, context) -> {
			return new DroneBoltRender(dispatcher);
		});
		EntityRendererRegistry.INSTANCE.register(ProjectilesEntityRegister.BLOODBOLT_MOB, (dispatcher, context) -> {
			return new BloodBoltRender(dispatcher);
		});

		BlockEntityRendererRegistry.INSTANCE.register(DoomMod.TOTEM, TotemRender::new);
		BlockEntityRendererRegistry.INSTANCE.register(DoomMod.GUN_TABLE_ENTITY, GunCraftingRender::new);

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

		BlockRenderLayerMap.INSTANCE.putBlock(DoomBlocks.JUMP_PAD, RenderLayer.getTranslucent());
	}
}