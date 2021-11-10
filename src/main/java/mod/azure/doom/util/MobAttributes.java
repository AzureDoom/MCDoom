package mod.azure.doom.util;

import mod.azure.doom.entity.tierambient.CueBallEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import mod.azure.doom.entity.tierambient.TurretEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import mod.azure.doom.entity.tierfodder.Imp2016Entity;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import mod.azure.doom.entity.tierfodder.NightmareImpEntity;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import mod.azure.doom.entity.tierfodder.ShotgunguyEntity;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.entity.tierheavy.Pinky2016;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class MobAttributes {

	public static void init() {
		// FabricDefaultAttributeRegistry.register(ModEntityTypes.GLADIATOR,
		// GladiatorEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARCHVILE, ArchvileEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.BARON, BaronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.CACODEMON, CacodemonEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.CHAINGUNNER, ChaingunnerEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.CYBERDEMON2016,
				Cyberdemon2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.CYBERDEMON, CyberdemonEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.HELLKNIGHT, HellknightEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.HELLKNIGHT2016,
				Hellknight2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ICONOFSIN, IconofsinEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.IMP2016, Imp2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.IMP, ImpEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.IMP_STONE, ImpStoneEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.NIGHTMARE_IMP, NightmareImpEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARACHNOTRON, ArachnotronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.LOST_SOUL, LostSoulEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.MANCUBUS, MancubusEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.MARAUDER, MarauderEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.PAIN, PainEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.PINKY, PinkyEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.SPECTRE, SpectreEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.POSSESSEDSCIENTIST,
				PossessedScientistEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.POSSESSEDSOLDIER,
				PossessedSoldierEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.MECHAZOMBIE, MechaZombieEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.REVENANT, RevenantEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.SHOTGUNGUY, ShotgunguyEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.SPIDERMASTERMIND,
				SpiderMastermindEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.UNWILLING, UnwillingEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ZOMBIEMAN, ZombiemanEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.GORE_NEST, GoreNestEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.GARGOYLE, GargoyleEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.CUEBALL, CueBallEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.PROWLER, ProwlerEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.DREADKNIGHT, Hellknight2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.TYRANT, Cyberdemon2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.POSSESSEDWORKER,
				PossessedScientistEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.DOOMHUNTER, DoomHunterEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.WHIPLASH, WhiplashEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.PINKY2016, Pinky2016.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.BARON2016, BaronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARMORBARON, ArmoredBaronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARACHNOTRONETERNAL,
				ArachnotronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.MAYKRDRONE, MaykrDroneEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.SPIDERMASTERMIND2016,
				SpiderMastermind2016Entity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.BLOODMAYKR, BloodMaykrEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARCHMAKER, ArchMakyrEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.FIREBARON, FireBaronEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.ARCHVILEETERNAL, ArchvileEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.TENTACLE, TentacleEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.MOTHERDEMON, MotherDemonEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.TURRET, TurretEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.SUMMONER, SummonerEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.REVENANT2016, Revenant2016Entity.createMobAttributes());
	}
}