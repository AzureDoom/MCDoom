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
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class DoomRenderRegistry {

	public static void init() {
		EntityRendererRegistry.register(DoomEntities.ARCHVILE, (ctx) -> new ArchvileRender(ctx));

		EntityRendererRegistry.register(DoomEntities.BARREL, (ctx) -> new BarrelRender(ctx));

		EntityRendererRegistry.register(DoomEntities.IMP, (ctx) -> new ImpRender(ctx));

		EntityRendererRegistry.register(DoomEntities.PINKY, (ctx) -> new PinkyRender(ctx));

		EntityRendererRegistry.register(DoomEntities.SPECTRE, (ctx) -> new SpectreRender(ctx));

		EntityRendererRegistry.register(DoomEntities.LOST_SOUL, (ctx) -> new LostSoulRender(ctx));

		EntityRendererRegistry.register(DoomEntities.LOST_SOUL_ETERNAL, (ctx) -> new LostSoulEternalRender(ctx));

		EntityRendererRegistry.register(DoomEntities.CACODEMON, (ctx) -> new CacodemonRender(ctx));

		EntityRendererRegistry.register(DoomEntities.BARON, (ctx) -> new BaronRender(ctx));

		EntityRendererRegistry.register(DoomEntities.MANCUBUS, (ctx) -> new MancubusRender(ctx));

		EntityRendererRegistry.register(DoomEntities.SPIDERMASTERMIND, (ctx) -> new SpiderMastermindRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ARACHNOTRON, (ctx) -> new ArachonotronRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ZOMBIEMAN, (ctx) -> new ZombiemanRender(ctx));

		EntityRendererRegistry.register(DoomEntities.REVENANT, (ctx) -> new RevenantRender(ctx));

		EntityRendererRegistry.register(DoomEntities.GORE_NEST, (ctx) -> new GoreNestRender(ctx));

		EntityRendererRegistry.register(DoomEntities.CHAINGUNNER, (ctx) -> new ChaingunnerRender(ctx));

		EntityRendererRegistry.register(DoomEntities.SHOTGUNGUY, (ctx) -> new ShotgunguyRender(ctx));

		EntityRendererRegistry.register(DoomEntities.MARAUDER, (ctx) -> new MarauderRender(ctx));

		EntityRendererRegistry.register(DoomEntities.PAIN, (ctx) -> new PainRender(ctx));

		EntityRendererRegistry.register(DoomEntities.HELLKNIGHT, (ctx) -> new HellknightRender(ctx));

		EntityRendererRegistry.register(DoomEntities.HELLKNIGHT2016, (ctx) -> new Hellknight2016Render(ctx));

		EntityRendererRegistry.register(DoomEntities.CYBERDEMON, (ctx) -> new CyberdemonRender(ctx));

		EntityRendererRegistry.register(DoomEntities.UNWILLING, (ctx) -> new UnwillingRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ICONOFSIN, (ctx) -> new IconofsinRender(ctx));

		EntityRendererRegistry.register(DoomEntities.POSSESSEDSCIENTIST, (ctx) -> new PossessedScientistRender(ctx));

		EntityRendererRegistry.register(DoomEntities.POSSESSEDSOLDIER, (ctx) -> new PossessedSoldierRender(ctx));

		EntityRendererRegistry.register(DoomEntities.GARGOYLE, (ctx) -> new GargoyleRender(ctx));

		EntityRendererRegistry.register(DoomEntities.MECHAZOMBIE, (ctx) -> new MechaZombieRender(ctx));

		EntityRendererRegistry.register(DoomEntities.CUEBALL, (ctx) -> new CueBallRender(ctx));

		EntityRendererRegistry.register(DoomEntities.PROWLER, (ctx) -> new ProwlerRender(ctx));

		EntityRendererRegistry.register(DoomEntities.DREADKNIGHT, (ctx) -> new DreadKnightRender(ctx));

		EntityRendererRegistry.register(DoomEntities.IMP_STONE, (ctx) -> new ImpStoneRender(ctx));

		EntityRendererRegistry.register(DoomEntities.POSSESSEDWORKER, (ctx) -> new PossessedWorkerRender(ctx));

		EntityRendererRegistry.register(DoomEntities.DOOMHUNTER, (ctx) -> new DoomHunterRender(ctx));

		EntityRendererRegistry.register(DoomEntities.MAYKRDRONE, (ctx) -> new MaykrDroneRender(ctx));

		EntityRendererRegistry.register(DoomEntities.WHIPLASH, (ctx) -> new WhiplashRender(ctx));

		EntityRendererRegistry.register(DoomEntities.BARON2016, (ctx) -> new Baron2016Render(ctx));

		EntityRendererRegistry.register(DoomEntities.FIREBARON, (ctx) -> new FireBaronRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ARMORBARON, (ctx) -> new ArmoredBaronRender(ctx));

		EntityRendererRegistry.register(DoomEntities.BLOODMAYKR, (ctx) -> new BloodMaykrRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ARCHMAKER, (ctx) -> new ArchMaykrRender(ctx));

		EntityRendererRegistry.register(DoomEntities.ARACHNOTRONETERNAL, (ctx) -> new ArachonotronEternalRender(ctx));

		EntityRendererRegistry.register(DoomEntities.SPIDERMASTERMIND2016,
				(ctx) -> new SpiderMastermind2016Render(ctx));

		EntityRendererRegistry.register(DoomEntities.TENTACLE, (ctx) -> new TentacleRender(ctx));

		EntityRendererRegistry.register(DoomEntities.TURRET, (ctx) -> new TurretRender(ctx));

		EntityRendererRegistry.register(DoomEntities.MOTHERDEMON, (ctx) -> new MotherDemonRender(ctx));

		EntityRendererRegistry.register(DoomEntities.SUMMONER, (ctx) -> new SummonerRender(ctx));

		EntityRendererRegistry.register(DoomEntities.REVENANT2016, (ctx) -> new Revenant2016Render(ctx));

		EntityRendererRegistry.register(DoomEntities.GLADIATOR, (ctx) -> new GladiatorRender(ctx));

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

		EntityRendererRegistry.register(ProjectilesEntityRegister.GLADIATOR_MACE,
				(ctx) -> new GladiatorMaceRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.DRONEBOLT_MOB, (ctx) -> new DroneBoltRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.BLOODBOLT_MOB, (ctx) -> new BloodBoltRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.FIRE_MOB, (ctx) -> new FireProjectileRender(ctx));

		BlockEntityRendererRegistry.register(DoomMod.TOTEM,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new TotemRender());
		BlockEntityRendererRegistry.register(DoomMod.GUN_TABLE_ENTITY,
				(BlockEntityRendererFactory.Context rendererDispatcherIn) -> new GunCraftingRender());

		BlockRenderLayerMap.INSTANCE.putBlock(DoomBlocks.JUMP_PAD, RenderLayer.getTranslucent());

		EntityRendererRegistry.register(ProjectilesEntityRegister.MEATHOOOK_ENTITY, MeatHookEntityRenderer::new);
	}
}