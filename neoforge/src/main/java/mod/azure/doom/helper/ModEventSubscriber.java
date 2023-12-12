package mod.azure.doom.helper;

import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierambient.CueBallEntity;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import mod.azure.doom.entities.tierambient.TurretEntity;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.entities.tierfodder.*;
import mod.azure.doom.entities.tierheavy.*;
import mod.azure.doom.entities.tiersuperheavy.*;
import mod.azure.doom.registry.NeoDoomEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MCDoom.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onRegisterEvent(SpawnPlacementRegisterEvent event) {
        event.register(NeoDoomEntities.GLADIATOR.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ARCHVILE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.LOST_SOUL.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.LOST_SOUL_ETERNAL.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ZOMBIEMAN.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.SPIDERMASTERMIND.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ARACHNOTRON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.MANCUBUS.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.BARON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.REVENANT.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.IMP.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.PINKY.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.SPECTRE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.CACODEMON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.CHAINGUNNER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.MARAUDER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.SHOTGUNGUY.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.PAIN.get(), SpawnPlacements.Type.IN_LAVA,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.HELLKNIGHT.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.HELLKNIGHT2016.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.CYBERDEMON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.UNWILLING.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.POSSESSEDSCIENTIST.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.POSSESSEDSOLDIER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ICONOFSIN.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.GORE_NEST.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.MECHAZOMBIE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.GARGOYLE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.CUEBALL.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.PROWLER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.DREADKNIGHT.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.IMP_STONE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.POSSESSEDWORKER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.DOOMHUNTER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.WHIPLASH.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.FIREBARON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.BARON2016.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ARMORBARON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ARACHNOTRONETERNAL.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.MAYKRDRONE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.SPIDERMASTERMIND2016.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.BLOODMAYKR.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.ARCHMAKER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.TENTACLE.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.MOTHERDEMON.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.TURRET.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.SUMMONER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.REVENANT2016.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
        event.register(NeoDoomEntities.CARCASS.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark,
                SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(NeoDoomEntities.GLADIATOR.get(), GladiatorEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.CYBERDEMON.get(), CyberdemonEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ARCHVILE.get(), ArchvileEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.BARON.get(), BaronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.CHAINGUNNER.get(), ChaingunnerEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.HELLKNIGHT.get(), HellknightEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.HELLKNIGHT2016.get(), Hellknight2016Entity.createMobAttributes().build());
        event.put(NeoDoomEntities.ICONOFSIN.get(), IconofsinEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.IMP.get(), ImpEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.MANCUBUS.get(), MancubusEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.MARAUDER.get(), MarauderEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.PINKY.get(), PinkyEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.SPECTRE.get(), SpectreEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.LOST_SOUL.get(), LostSoulEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.LOST_SOUL_ETERNAL.get(), LostSoulEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.POSSESSEDSCIENTIST.get(), PossessedScientistEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.POSSESSEDSOLDIER.get(), PossessedSoldierEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.REVENANT.get(), RevenantEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.SHOTGUNGUY.get(), ShotgunguyEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ARACHNOTRON.get(), ArachnotronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.SPIDERMASTERMIND.get(), SpiderMastermindEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.UNWILLING.get(), UnwillingEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ZOMBIEMAN.get(), ZombiemanEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.CACODEMON.get(), CacodemonEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.PAIN.get(), PainEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.GORE_NEST.get(), GoreNestEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.MECHAZOMBIE.get(), MechaZombieEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.GARGOYLE.get(), GargoyleEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.CUEBALL.get(), CueBallEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.PROWLER.get(), ProwlerEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.DREADKNIGHT.get(), Hellknight2016Entity.createMobAttributes().build());
        event.put(NeoDoomEntities.IMP_STONE.get(), ImpStoneEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.POSSESSEDWORKER.get(), PossessedScientistEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.DOOMHUNTER.get(), DoomHunterEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.WHIPLASH.get(), WhiplashEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.BARON2016.get(), BaronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ARMORBARON.get(), ArmoredBaronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ARACHNOTRONETERNAL.get(), ArachnotronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.MAYKRDRONE.get(), MaykrDroneEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.SPIDERMASTERMIND2016.get(), SpiderMastermindEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.BLOODMAYKR.get(), BloodMaykrEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.ARCHMAKER.get(), ArchMakyrEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.FIREBARON.get(), FireBaronEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.TENTACLE.get(), TentacleEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.MOTHERDEMON.get(), MotherDemonEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.TURRET.get(), TurretEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.SUMMONER.get(), SummonerEntity.createMobAttributes().build());
        event.put(NeoDoomEntities.REVENANT2016.get(), Revenant2016Entity.createMobAttributes().build());
        event.put(NeoDoomEntities.CARCASS.get(), CarcassEntity.createMobAttributes().build());
    }
}