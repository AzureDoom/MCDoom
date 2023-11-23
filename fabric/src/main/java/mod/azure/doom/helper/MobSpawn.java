package mod.azure.doom.helper;

import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.registry.DoomTags;
import mod.azure.doom.registry.FabricDoomEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

    public static void addSpawnEntries() {
        // The End
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ARCHMAYKR_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ARCHMAKER, MCDoom.config.archmaykr_spawn_weight, MCDoom.config.archmaykr_min_group, MCDoom.config.archmaykr_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.MAYKREDRONE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.MAYKRDRONE, MCDoom.config.maykrdrone_spawn_weight, MCDoom.config.maykrdrone_min_group, MCDoom.config.maykrdrone_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.BLOODMAYKR_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.BLOODMAYKR, MCDoom.config.bloodmaykr_spawn_weight, MCDoom.config.bloodmaykr_min_group, MCDoom.config.bloodmaykr_max_group);
        // The Nether
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.GLADIATOR_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.GLADIATOR, MCDoom.config.gladiator_spawn_weight, MCDoom.config.gladiator_min_group, MCDoom.config.gladiator_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.MOTHERDEMON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.MOTHERDEMON, MCDoom.config.motherdemon_spawn_weight, MCDoom.config.motherdemon_min_group, MCDoom.config.motherdemon_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.IMP_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.IMP, MCDoom.config.imp_spawn_weight, MCDoom.config.imp_min_group, MCDoom.config.imp_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.IMPSTONE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.IMP_STONE, MCDoom.config.impstone_spawn_weight, MCDoom.config.impstone_min_group, MCDoom.config.impstone_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.PINKY_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.PINKY, MCDoom.config.pinky_spawn_weight, MCDoom.config.pinky_min_group, MCDoom.config.pinky_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.SPECTRE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.SPECTRE, MCDoom.config.spectre_spawn_weight, MCDoom.config.spectre_min_group, MCDoom.config.spectre_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.LOST_SOUL_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.LOST_SOUL, MCDoom.config.lost_soul_spawn_weight, MCDoom.config.lost_soul_min_group, MCDoom.config.lost_soul_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.LOST_SOUL_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.LOST_SOUL_ETERNAL, MCDoom.config.lost_soul_spawn_weight, MCDoom.config.lost_soul_min_group, MCDoom.config.lost_soul_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.CACODEMON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.CACODEMON, MCDoom.config.cacodemon_spawn_weight, MCDoom.config.cacodemon_min_group, MCDoom.config.cacodemon_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ARCHVILE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ARCHVILE, MCDoom.config.archvile_spawn_weight, MCDoom.config.archvile_min_group, MCDoom.config.archvile_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.BARON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.BARON, MCDoom.config.baron_spawn_weight, MCDoom.config.baron_min_group, MCDoom.config.baron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.MANCUBUS_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.MANCUBUS, MCDoom.config.mancubus_spawn_weight, MCDoom.config.mancubus_min_group, MCDoom.config.mancubus_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.REVENANT_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.REVENANT, MCDoom.config.revenant_spawn_weight, MCDoom.config.revenant_min_group, MCDoom.config.revenant_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.REVENANT_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.REVENANT2016, MCDoom.config.revenant_spawn_weight, MCDoom.config.revenant_min_group, MCDoom.config.revenant_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.SPIDER_MASTERMIND_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.SPIDERMASTERMIND, MCDoom.config.spider_mastermind_spawn_weight, MCDoom.config.spider_mastermind_min_group, MCDoom.config.spider_mastermind_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ZOMBIEMAN_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ZOMBIEMAN, MCDoom.config.zombieman_spawn_weight, MCDoom.config.zombieman_min_group, MCDoom.config.zombieman_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ARCHNOTRON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ARACHNOTRON, MCDoom.config.arachnotron_spawn_weight, MCDoom.config.arachnotron_min_group, MCDoom.config.arachnotron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ARCHNOTRON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ARACHNOTRONETERNAL, MCDoom.config.arachnotron_spawn_weight, MCDoom.config.arachnotron_min_group, MCDoom.config.arachnotron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.GARGOYLE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.GARGOYLE, MCDoom.config.gargoyle_spawn_weight, MCDoom.config.gargoyle_min_group, MCDoom.config.gargoyle_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.CHAINGUNNER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.CHAINGUNNER, MCDoom.config.chaingunner_spawn_weight, MCDoom.config.chaingunner_min_group, MCDoom.config.chaingunner_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.SHOTGUNGUY_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.SHOTGUNGUY, MCDoom.config.shotgunguy_spawn_weight, MCDoom.config.shotgunguy_min_group, MCDoom.config.shotgunguy_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.MARAUDER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.MARAUDER, MCDoom.config.marauder_spawn_weight, MCDoom.config.marauder_min_group, MCDoom.config.marauder_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.PAIN_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.PAIN, MCDoom.config.pain_spawn_weight, MCDoom.config.pain_min_group, MCDoom.config.pain_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.HELLKNIGHT_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.HELLKNIGHT, MCDoom.config.hellknight_spawn_weight, MCDoom.config.hellknight_min_group, MCDoom.config.hellknight_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.HELLKNIGHT2016_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.HELLKNIGHT2016, MCDoom.config.hellknight2016_spawn_weight, MCDoom.config.hellknight2016_min_group, MCDoom.config.hellknight2016_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.HELLKNIGHT2016_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.DREADKNIGHT, MCDoom.config.hellknight2016_spawn_weight, MCDoom.config.hellknight2016_min_group, MCDoom.config.hellknight2016_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.CYBERDEMON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.CYBERDEMON, MCDoom.config.cyberdemon_spawn_weight, MCDoom.config.cyberdemon_min_group, MCDoom.config.cyberdemon_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.UNWILLING_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.UNWILLING, MCDoom.config.unwilling_spawn_weight, MCDoom.config.unwilling_min_group, MCDoom.config.unwilling_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.POSSESSED_SCIENTIST_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.POSSESSEDSCIENTIST, MCDoom.config.possessed_scientist_spawn_weight, MCDoom.config.possessed_scientist_min_group, MCDoom.config.possessed_scientist_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.POSSESSED_SOLDIER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.POSSESSEDSOLDIER, MCDoom.config.possessed_soldier_spawn_weight, MCDoom.config.possessed_soldier_min_group, MCDoom.config.possessed_soldier_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.MECHAZOMBIE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.MECHAZOMBIE, MCDoom.config.mechazombie_spawn_weight, MCDoom.config.mechazombie_min_group, MCDoom.config.mechazombie_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.CUEBALL_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.CUEBALL, MCDoom.config.cueball_spawn_weight, MCDoom.config.cueball_min_group, MCDoom.config.cueball_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.PROWLER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.PROWLER, MCDoom.config.prowler_spawn_weight, MCDoom.config.prowler_min_group, MCDoom.config.prowler_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.GORENEST_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.GORE_NEST, MCDoom.config.gorenest_spawn_weight, MCDoom.config.gorenest_min_group, MCDoom.config.gorenest_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.POSSESSED_WORKER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.POSSESSEDWORKER, MCDoom.config.possessed_worker_spawn_weight, MCDoom.config.possessed_worker_min_group, MCDoom.config.possessed_worker_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.SPIDER_MASTERMIND_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.SPIDERMASTERMIND2016, MCDoom.config.spider_mastermind_spawn_weight, MCDoom.config.spider_mastermind_min_group, MCDoom.config.spider_mastermind_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.DOOMHUNTER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.DOOMHUNTER, MCDoom.config.doomhunter_spawn_weight, MCDoom.config.doomhunter_min_group, MCDoom.config.doomhunter_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.TENTACLE_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.TENTACLE, MCDoom.config.tentacle_spawn_weight, MCDoom.config.tentacle_min_group, MCDoom.config.tentacle_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.SUMMONER_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.SUMMONER, MCDoom.config.summoner_spawn_weight, MCDoom.config.summoner_min_group, MCDoom.config.summoner_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.WHIPLASH_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.WHIPLASH, MCDoom.config.whiplash_spawn_weight, MCDoom.config.whiplash_min_group, MCDoom.config.whiplash_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.BARON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.BARON2016, MCDoom.config.baron_spawn_weight, MCDoom.config.baron_min_group, MCDoom.config.baron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.BARON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.FIREBARON, MCDoom.config.baron_spawn_weight, MCDoom.config.baron_min_group, MCDoom.config.baron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.ARMOREDBARON_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.ARMORBARON, MCDoom.config.armoredbaron_spawn_weight, MCDoom.config.armoredbaron_min_group, MCDoom.config.armoredbaron_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.TURRET_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.TURRET, MCDoom.config.turret_spawn_weight, MCDoom.config.turret_min_group, MCDoom.config.turret_max_group);
        BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomTags.CARCASS_BIOMES, context)), MobCategory.MONSTER, FabricDoomEntities.CARCASS, MCDoom.config.carcass_spawn_weight, MCDoom.config.carcass_min_group, MCDoom.config.carcass_max_group);
        SpawnPlacements.register(FabricDoomEntities.CARCASS, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.GLADIATOR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ARCHVILE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ZOMBIEMAN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.SPIDERMASTERMIND, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ARACHNOTRON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.MANCUBUS, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.BARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.REVENANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.IMP, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.PINKY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.SPECTRE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.CACODEMON, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.LOST_SOUL, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.LOST_SOUL_ETERNAL, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.CHAINGUNNER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.MARAUDER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.SHOTGUNGUY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.PAIN, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.HELLKNIGHT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.HELLKNIGHT2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.CYBERDEMON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.UNWILLING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.POSSESSEDSCIENTIST, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.POSSESSEDSOLDIER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ICONOFSIN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.MECHAZOMBIE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.GORE_NEST, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.GARGOYLE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.CUEBALL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.PROWLER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.DREADKNIGHT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.IMP_STONE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.POSSESSEDWORKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.DOOMHUNTER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.WHIPLASH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.FIREBARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.BARON2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ARMORBARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ARACHNOTRONETERNAL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.MAYKRDRONE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.SPIDERMASTERMIND2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.BLOODMAYKR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.ARCHMAKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.TENTACLE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.MOTHERDEMON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.TURRET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.SUMMONER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
        SpawnPlacements.register(FabricDoomEntities.REVENANT2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
    }

    private static boolean parseBiomes(TagKey<Biome> biomes, BiomeSelectionContext biomeContext) {
        return biomeContext.hasTag(biomes);
    }
}