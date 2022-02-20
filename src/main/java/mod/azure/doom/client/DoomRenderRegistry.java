package mod.azure.doom.client;

import mod.azure.doom.DoomMod;
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
import mod.azure.doom.client.render.projectiles.EnergyCellRender;
import mod.azure.doom.client.render.projectiles.GrenadeRender;
import mod.azure.doom.client.render.projectiles.MeatHookEntityRenderer;
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
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DoomRenderRegistry {

	@SuppressWarnings("unchecked")
	public static void init() {
		EntityRendererRegistry.register(ModEntityTypes.ARCHVILE, (ctx) -> new ArchvileRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.BARREL, (ctx) -> new BarrelRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.IMP, (ctx) -> new ImpRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.PINKY, (ctx) -> new PinkyRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.SPECTRE, (ctx) -> new SpectreRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.LOST_SOUL, (ctx) -> new LostSoulRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.LOST_SOUL_ETERNAL, (ctx) -> new LostSoulEternalRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.CACODEMON, (ctx) -> new CacodemonRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.BARON, (ctx) -> new BaronRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.MANCUBUS, (ctx) -> new MancubusRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.SPIDERMASTERMIND, (ctx) -> new SpiderMastermindRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ARACHNOTRON, (ctx) -> new ArachonotronRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ZOMBIEMAN, (ctx) -> new ZombiemanRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.REVENANT, (ctx) -> new RevenantRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.GORE_NEST, (ctx) -> new GoreNestRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.CHAINGUNNER, (ctx) -> new ChaingunnerRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.SHOTGUNGUY, (ctx) -> new ShotgunguyRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.MARAUDER, (ctx) -> new MarauderRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.PAIN, (ctx) -> new PainRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.HELLKNIGHT, (ctx) -> new HellknightRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.HELLKNIGHT2016, (ctx) -> new Hellknight2016Render(ctx));

		EntityRendererRegistry.register(ModEntityTypes.CYBERDEMON, (ctx) -> new CyberdemonRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.UNWILLING, (ctx) -> new UnwillingRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ICONOFSIN, (ctx) -> new IconofsinRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.POSSESSEDSCIENTIST, (ctx) -> new PossessedScientistRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.POSSESSEDSOLDIER, (ctx) -> new PossessedSoldierRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.GARGOYLE, (ctx) -> new GargoyleRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.MECHAZOMBIE, (ctx) -> new MechaZombieRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.CUEBALL, (ctx) -> new CueBallRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.PROWLER, (ctx) -> new ProwlerRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.DREADKNIGHT, (ctx) -> new DreadKnightRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.IMP_STONE, (ctx) -> new ImpStoneRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.POSSESSEDWORKER, (ctx) -> new PossessedWorkerRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.DOOMHUNTER, (ctx) -> new DoomHunterRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.MAYKRDRONE, (ctx) -> new MaykrDroneRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.WHIPLASH, (ctx) -> new WhiplashRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.BARON2016, (ctx) -> new Baron2016Render(ctx));

		EntityRendererRegistry.register(ModEntityTypes.FIREBARON, (ctx) -> new FireBaronRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ARMORBARON, (ctx) -> new ArmoredBaronRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.BLOODMAYKR, (ctx) -> new BloodMaykrRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ARCHMAKER, (ctx) -> new ArchMaykrRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.ARACHNOTRONETERNAL, (ctx) -> new ArachonotronEternalRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.SPIDERMASTERMIND2016,
				(ctx) -> new SpiderMastermind2016Render(ctx));

		EntityRendererRegistry.register(ModEntityTypes.TENTACLE, (ctx) -> new TentacleRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.TURRET, (ctx) -> new TurretRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.MOTHERDEMON, (ctx) -> new MotherDemonRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.SUMMONER, (ctx) -> new SummonerRender(ctx));

		EntityRendererRegistry.register(ModEntityTypes.REVENANT2016, (ctx) -> new Revenant2016Render(ctx));

		EntityRendererRegistry.register(ModEntityTypes.GLADIATOR, (ctx) -> new GladiatorRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ARGENT_BOLT, (ctx) -> new ArgentBoltRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.GRENADE, (ctx) -> new GrenadeRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.SHOTGUN_SHELL, (ctx) -> new ShotgunShellRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ENERGY_CELL, (ctx) -> new EnergyCellRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.BFG_CELL, (ctx) -> new BFGCellRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKET, (ctx) -> new RocketRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.BARENBLAST, (ctx) -> new BarenBlastRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLETS, (ctx) -> new BulletsRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.CHAINGUN_BULLET,
				(ctx) -> new ChaingunBulletRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.UNMAYKR, (ctx) -> new UnmaykrBulletRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKET_MOB, (ctx) -> new RocketMobRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.ENERGY_CELL_MOB,
				(ctx) -> new EnergyCellMobRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.CHAINGUN_MOB, (ctx) -> new ChaingunMobRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.FIRING, (ctx) -> new ArchvileFiringRender(ctx));

		EntityRendererRegistry.register(ProjectilesEntityRegister.CHAINBLADE, (ctx) -> new ChainBladeRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.GLADIATOR_MACE,
				(ctx) -> new GladiatorMaceRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.DRONEBOLT_MOB, (ctx) -> new DroneBoltRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.BLOODBOLT_MOB, (ctx) -> new BloodBoltRender(ctx));

		BlockEntityRendererRegistry.register(DoomMod.TOTEM,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new TotemRender());
		BlockEntityRendererRegistry.register(DoomMod.GUN_TABLE_ENTITY,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new GunCraftingRender());

		GeoArmorRenderer.registerArmorRenderer(new DoomicornRender(), DoomItems.DOOMICORN_DOOM_BOOTS,
				DoomItems.DOOMICORN_DOOM_CHESTPLATE, DoomItems.DOOMICORN_DOOM_HELMET,
				DoomItems.DOOMICORN_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new NightmareRender(), DoomItems.NIGHTMARE_DOOM_BOOTS,
				DoomItems.NIGHTMARE_DOOM_CHESTPLATE, DoomItems.NIGHTMARE_DOOM_HELMET,
				DoomItems.NIGHTMARE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new PurplePonyRender(), DoomItems.PURPLEPONY_DOOM_BOOTS,
				DoomItems.PURPLEPONY_DOOM_CHESTPLATE, DoomItems.PURPLEPONY_DOOM_HELMET,
				DoomItems.PURPLEPONY_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new DoomRender(), DoomItems.DOOM_BOOTS, DoomItems.DOOM_CHESTPLATE,
				DoomItems.DOOM_HELMET, DoomItems.DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new AstroRender(), DoomItems.ASTRO_DOOM_BOOTS,
				DoomItems.ASTRO_DOOM_CHESTPLATE, DoomItems.ASTRO_DOOM_HELMET, DoomItems.ASTRO_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new BronzeRender(), DoomItems.BRONZE_DOOM_BOOTS,
				DoomItems.BRONZE_DOOM_CHESTPLATE, DoomItems.BRONZE_DOOM_HELMET, DoomItems.BRONZE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new CrimsonRender(), DoomItems.CRIMSON_DOOM_BOOTS,
				DoomItems.CRIMSON_DOOM_CHESTPLATE, DoomItems.CRIMSON_DOOM_HELMET, DoomItems.CRIMSON_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new DemoncideRender(), DoomItems.DEMONCIDE_DOOM_BOOTS,
				DoomItems.DEMONCIDE_DOOM_CHESTPLATE, DoomItems.DEMONCIDE_DOOM_HELMET,
				DoomItems.DEMONCIDE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new DemonicRender(), DoomItems.DEMONIC_DOOM_BOOTS,
				DoomItems.DEMONIC_DOOM_CHESTPLATE, DoomItems.DEMONIC_DOOM_HELMET, DoomItems.DEMONIC_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new EmberRender(), DoomItems.EMBER_DOOM_BOOTS,
				DoomItems.EMBER_DOOM_CHESTPLATE, DoomItems.EMBER_DOOM_HELMET, DoomItems.EMBER_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new GoldRender(), DoomItems.GOLD_DOOM_BOOTS,
				DoomItems.GOLD_DOOM_CHESTPLATE, DoomItems.GOLD_DOOM_HELMET, DoomItems.GOLD_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new HotrodRender(), DoomItems.HOTROD_BOOTS, DoomItems.HOTROD_CHESTPLATE,
				DoomItems.HOTROD_HELMET, DoomItems.HOTROD_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new MidnightRender(), DoomItems.MIDNIGHT_DOOM_BOOTS,
				DoomItems.MIDNIGHT_DOOM_CHESTPLATE, DoomItems.MIDNIGHT_DOOM_HELMET, DoomItems.MIDNIGHT_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new PhobosRender(), DoomItems.PHOBOS_DOOM_BOOTS,
				DoomItems.PHOBOS_DOOM_CHESTPLATE, DoomItems.PHOBOS_DOOM_HELMET, DoomItems.PHOBOS_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new PraetorRender(), DoomItems.PRAETOR_DOOM_BOOTS,
				DoomItems.PRAETOR_DOOM_CHESTPLATE, DoomItems.PRAETOR_DOOM_HELMET, DoomItems.PRAETOR_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new TwentyFiveRender(), DoomItems.TWENTY_FIVE_DOOM_BOOTS,
				DoomItems.TWENTY_FIVE_DOOM_CHESTPLATE, DoomItems.TWENTY_FIVE_DOOM_HELMET,
				DoomItems.TWENTY_FIVE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new ClassicBronzeRender(), DoomItems.CLASSIC_BRONZE_DOOM_CHESTPLATE,
				DoomItems.CLASSIC_BRONZE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new ClassicRender(), DoomItems.CLASSIC_DOOM_BOOTS,
				DoomItems.CLASSIC_DOOM_CHESTPLATE, DoomItems.CLASSIC_DOOM_HELMET, DoomItems.CLASSIC_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new ClassicIndigoRender(), DoomItems.CLASSIC_INDIGO_DOOM_CHESTPLATE,
				DoomItems.CLASSIC_INDIGO_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new ClassicRedRender(), DoomItems.CLASSIC_RED_DOOM_CHESTPLATE,
				DoomItems.CLASSIC_RED_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new Mullet1Render(), DoomItems.MULLET_DOOM_BOOTS1,
				DoomItems.MULLET_DOOM_CHESTPLATE1, DoomItems.MULLET_DOOM_HELMET1, DoomItems.MULLET_DOOM_LEGGINGS1);
		GeoArmorRenderer.registerArmorRenderer(new Mullet2Render(), DoomItems.MULLET_DOOM_CHESTPLATE2);
		GeoArmorRenderer.registerArmorRenderer(new Mullet3Render(), DoomItems.MULLET_DOOM_CHESTPLATE3);
		GeoArmorRenderer.registerArmorRenderer(new PainterRender(), DoomItems.PAINTER_DOOM_CHESTPLATE,
				DoomItems.PAINTER_DOOM_HELMET);
		GeoArmorRenderer.registerArmorRenderer(new CultistRender(), DoomItems.CULTIST_DOOM_BOOTS,
				DoomItems.CULTIST_DOOM_CHESTPLATE, DoomItems.CULTIST_DOOM_HELMET, DoomItems.CULTIST_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new MaykrRender(), DoomItems.MAYKR_DOOM_BOOTS,
				DoomItems.MAYKR_DOOM_CHESTPLATE, DoomItems.MAYKR_DOOM_HELMET, DoomItems.MAYKR_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new SentinelRender(), DoomItems.SENTINEL_DOOM_BOOTS,
				DoomItems.SENTINEL_DOOM_CHESTPLATE, DoomItems.SENTINEL_DOOM_HELMET, DoomItems.SENTINEL_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new ZombieRender(), DoomItems.ZOMBIE_DOOM_BOOTS,
				DoomItems.ZOMBIE_DOOM_CHESTPLATE, DoomItems.ZOMBIE_DOOM_HELMET, DoomItems.ZOMBIE_DOOM_LEGGINGS);
		GeoArmorRenderer.registerArmorRenderer(new SantaRender(), DoomItems.SANTA_HELMET);
		GeoArmorRenderer.registerArmorRenderer(new DarkLordArmorRender(), DoomItems.DARKLORD_BOOTS,
				DoomItems.DARKLORD_CHESTPLATE, DoomItems.DARKLORD_HELMET, DoomItems.DARKLORD_LEGGINGS);

		BlockRenderLayerMap.INSTANCE.putBlock(DoomBlocks.JUMP_PAD, RenderLayer.getTranslucent());

		EntityRendererRegistry.register(ProjectilesEntityRegister.MEATHOOOK_ENTITY, MeatHookEntityRenderer::new);
	}
}