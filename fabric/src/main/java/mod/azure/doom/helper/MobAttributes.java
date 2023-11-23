package mod.azure.doom.helper;

import mod.azure.doom.entities.tierambient.CueBallEntity;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import mod.azure.doom.entities.tierambient.TurretEntity;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.entities.tierfodder.*;
import mod.azure.doom.entities.tierheavy.*;
import mod.azure.doom.entities.tiersuperheavy.*;
import mod.azure.doom.registry.FabricDoomEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public record MobAttributes() {

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.GLADIATOR, GladiatorEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ARCHVILE, ArchvileEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.BARON, BaronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.CACODEMON, CacodemonEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.CHAINGUNNER, ChaingunnerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.CYBERDEMON, CyberdemonEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.HELLKNIGHT, HellknightEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.HELLKNIGHT2016, Hellknight2016Entity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ICONOFSIN, IconofsinEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.IMP, ImpEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.IMP_STONE, ImpStoneEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ARACHNOTRON, ArachnotronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.LOST_SOUL, LostSoulEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.LOST_SOUL_ETERNAL, LostSoulEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.MANCUBUS, MancubusEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.MARAUDER, MarauderEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.PAIN, PainEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.PINKY, PinkyEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.SPECTRE, SpectreEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.POSSESSEDSCIENTIST, PossessedScientistEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.POSSESSEDSOLDIER, PossessedSoldierEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.MECHAZOMBIE, MechaZombieEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.REVENANT, RevenantEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.SHOTGUNGUY, ShotgunguyEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.SPIDERMASTERMIND, SpiderMastermindEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.UNWILLING, UnwillingEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ZOMBIEMAN, ZombiemanEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.GORE_NEST, GoreNestEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.GARGOYLE, GargoyleEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.CUEBALL, CueBallEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.PROWLER, ProwlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.DREADKNIGHT, Hellknight2016Entity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.POSSESSEDWORKER, PossessedScientistEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.DOOMHUNTER, DoomHunterEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.WHIPLASH, WhiplashEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.BARON2016, BaronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ARMORBARON, ArmoredBaronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ARACHNOTRONETERNAL, ArachnotronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.MAYKRDRONE, MaykrDroneEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.SPIDERMASTERMIND2016, SpiderMastermindEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.BLOODMAYKR, BloodMaykrEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.ARCHMAKER, ArchMakyrEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.FIREBARON, FireBaronEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.TENTACLE, TentacleEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.MOTHERDEMON, MotherDemonEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.TURRET, TurretEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.SUMMONER, SummonerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.REVENANT2016, Revenant2016Entity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FabricDoomEntities.CARCASS, CarcassEntity.createMobAttributes());
    }
}