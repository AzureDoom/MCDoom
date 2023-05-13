package mod.azure.doom.util.registry;

import java.util.Arrays;
import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.maykrdrone_biomes, context)), MobCategory.MONSTER, DoomEntities.MAYKRDRONE, DoomMod.config.maykrdrone_spawn_weight, DoomMod.config.maykrdrone_min_group, DoomMod.config.maykrdrone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.bloodmaykr_biomes, context)), MobCategory.MONSTER, DoomEntities.BLOODMAYKR, DoomMod.config.bloodmaykr_spawn_weight, DoomMod.config.bloodmaykr_min_group, DoomMod.config.bloodmaykr_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.imp_biomes, context)), MobCategory.MONSTER, DoomEntities.IMP, DoomMod.config.imp_spawn_weight, DoomMod.config.imp_min_group, DoomMod.config.imp_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.imp_biomes, context)), MobCategory.MONSTER, DoomEntities.IMP_STONE, DoomMod.config.impstone_spawn_weight, DoomMod.config.impstone_min_group, DoomMod.config.impstone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.pinky_biomes, context)), MobCategory.MONSTER, DoomEntities.PINKY, DoomMod.config.pinky_spawn_weight, DoomMod.config.pinky_min_group, DoomMod.config.pinky_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.spectre_biomes, context)), MobCategory.MONSTER, DoomEntities.SPECTRE, DoomMod.config.spectre_spawn_weight, DoomMod.config.spectre_min_group, DoomMod.config.spectre_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.lost_soul_biomes, context)), MobCategory.MONSTER, DoomEntities.LOST_SOUL, DoomMod.config.lost_soul_spawn_weight, DoomMod.config.lost_soul_min_group, DoomMod.config.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.lost_soul_biomes, context)), MobCategory.MONSTER, DoomEntities.LOST_SOUL_ETERNAL, DoomMod.config.lost_soul_spawn_weight, DoomMod.config.lost_soul_min_group, DoomMod.config.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.cacodemon_biomes, context)), MobCategory.MONSTER, DoomEntities.CACODEMON, DoomMod.config.cacodemon_spawn_weight, DoomMod.config.cacodemon_min_group, DoomMod.config.cacodemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.archvile_biomes, context)), MobCategory.MONSTER, DoomEntities.ARCHVILE, DoomMod.config.archvile_spawn_weight, DoomMod.config.archvile_min_group, DoomMod.config.archvile_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.baron_biomes, context)), MobCategory.MONSTER, DoomEntities.BARON, DoomMod.config.baron_spawn_weight, DoomMod.config.baron_min_group, DoomMod.config.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.mancubus_biomes, context)), MobCategory.MONSTER, DoomEntities.MANCUBUS, DoomMod.config.mancubus_spawn_weight, DoomMod.config.mancubus_min_group, DoomMod.config.mancubus_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.revenant_biomes, context)), MobCategory.MONSTER, DoomEntities.REVENANT, DoomMod.config.revenant_spawn_weight, DoomMod.config.revenant_min_group, DoomMod.config.revenant_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.revenant_biomes, context)), MobCategory.MONSTER, DoomEntities.REVENANT2016, DoomMod.config.revenant_spawn_weight, DoomMod.config.revenant_min_group, DoomMod.config.revenant_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.spider_mastermind_biomes, context)), MobCategory.MONSTER, DoomEntities.SPIDERMASTERMIND, DoomMod.config.spider_mastermind_spawn_weight, DoomMod.config.spider_mastermind_min_group, DoomMod.config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.zombieman_biomes, context)), MobCategory.MONSTER, DoomEntities.ZOMBIEMAN, DoomMod.config.zombieman_spawn_weight, DoomMod.config.zombieman_min_group, DoomMod.config.zombieman_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.arachnotron_biomes, context)), MobCategory.MONSTER, DoomEntities.ARACHNOTRON, DoomMod.config.arachnotron_spawn_weight, DoomMod.config.arachnotron_min_group, DoomMod.config.arachnotron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.arachnotron_biomes, context)), MobCategory.MONSTER, DoomEntities.ARACHNOTRONETERNAL, DoomMod.config.arachnotron_spawn_weight, DoomMod.config.arachnotron_min_group, DoomMod.config.arachnotron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.gargoyle_biomes, context)), MobCategory.MONSTER, DoomEntities.GARGOYLE, DoomMod.config.gargoyle_spawn_weight, DoomMod.config.gargoyle_min_group, DoomMod.config.gargoyle_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.chaingunner_biomes, context)), MobCategory.MONSTER, DoomEntities.CHAINGUNNER, DoomMod.config.chaingunner_spawn_weight, DoomMod.config.chaingunner_min_group, DoomMod.config.chaingunner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.shotgunguy_biomes, context)), MobCategory.MONSTER, DoomEntities.SHOTGUNGUY, DoomMod.config.shotgunguy_spawn_weight, DoomMod.config.shotgunguy_min_group, DoomMod.config.shotgunguy_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.marauder_biomes, context)), MobCategory.MONSTER, DoomEntities.MARAUDER, DoomMod.config.marauder_spawn_weight, DoomMod.config.marauder_min_group, DoomMod.config.marauder_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.pain_biomes, context)), MobCategory.MONSTER, DoomEntities.PAIN, DoomMod.config.pain_spawn_weight, DoomMod.config.pain_min_group, DoomMod.config.pain_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.hellknight_biomes, context)), MobCategory.MONSTER, DoomEntities.HELLKNIGHT, DoomMod.config.hellknight_spawn_weight, DoomMod.config.hellknight_min_group, DoomMod.config.hellknight_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.hellknight2016_biomes, context)), MobCategory.MONSTER, DoomEntities.HELLKNIGHT2016, DoomMod.config.hellknight2016_spawn_weight, DoomMod.config.hellknight2016_min_group, DoomMod.config.hellknight2016_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.hellknight2016_biomes, context)), MobCategory.MONSTER, DoomEntities.DREADKNIGHT, DoomMod.config.hellknight2016_spawn_weight, DoomMod.config.hellknight2016_min_group, DoomMod.config.hellknight2016_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.cyberdemon_biomes, context)), MobCategory.MONSTER, DoomEntities.CYBERDEMON, DoomMod.config.cyberdemon_spawn_weight, DoomMod.config.cyberdemon_min_group, DoomMod.config.cyberdemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.unwilling_biomes, context)), MobCategory.MONSTER, DoomEntities.UNWILLING, DoomMod.config.unwilling_spawn_weight, DoomMod.config.unwilling_min_group, DoomMod.config.unwilling_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.possessed_scientist_biomes, context)), MobCategory.MONSTER, DoomEntities.POSSESSEDSCIENTIST, DoomMod.config.possessed_scientist_spawn_weight, DoomMod.config.possessed_scientist_min_group, DoomMod.config.possessed_scientist_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.possessed_soldier_biomes, context)), MobCategory.MONSTER, DoomEntities.POSSESSEDSOLDIER, DoomMod.config.possessed_soldier_spawn_weight, DoomMod.config.possessed_soldier_min_group, DoomMod.config.possessed_soldier_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.mechazombie_biomes, context)), MobCategory.MONSTER, DoomEntities.MECHAZOMBIE, DoomMod.config.mechazombie_spawn_weight, DoomMod.config.mechazombie_min_group, DoomMod.config.mechazombie_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.cueball_biomes, context)), MobCategory.MONSTER, DoomEntities.CUEBALL, DoomMod.config.cueball_spawn_weight, DoomMod.config.cueball_min_group, DoomMod.config.cueball_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.prowler_biomes, context)), MobCategory.MONSTER, DoomEntities.PROWLER, DoomMod.config.prowler_spawn_weight, DoomMod.config.prowler_min_group, DoomMod.config.prowler_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.gorenest_biomes, context)), MobCategory.MONSTER, DoomEntities.GORE_NEST, DoomMod.config.gorenest_spawn_weight, DoomMod.config.gorenest_min_group, DoomMod.config.gorenest_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.possessed_worker_biomes, context)), MobCategory.MONSTER, DoomEntities.POSSESSEDWORKER, DoomMod.config.possessed_worker_spawn_weight, DoomMod.config.possessed_worker_min_group, DoomMod.config.possessed_worker_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.spider_mastermind_biomes, context)), MobCategory.MONSTER, DoomEntities.SPIDERMASTERMIND2016, DoomMod.config.spider_mastermind_spawn_weight, DoomMod.config.spider_mastermind_min_group, DoomMod.config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.doomhunter_biomes, context)), MobCategory.MONSTER, DoomEntities.DOOMHUNTER, DoomMod.config.doomhunter_spawn_weight, DoomMod.config.doomhunter_min_group, DoomMod.config.doomhunter_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.tentacle_biomes, context)), MobCategory.MONSTER, DoomEntities.TENTACLE, DoomMod.config.tentacle_spawn_weight, DoomMod.config.tentacle_min_group, DoomMod.config.tentacle_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.summoner_biomes, context)), MobCategory.MONSTER, DoomEntities.SUMMONER, DoomMod.config.summoner_spawn_weight, DoomMod.config.summoner_min_group, DoomMod.config.summoner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.whiplash_biomes, context)), MobCategory.MONSTER, DoomEntities.WHIPLASH, DoomMod.config.whiplash_spawn_weight, DoomMod.config.whiplash_min_group, DoomMod.config.whiplash_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.baron_biomes, context)), MobCategory.MONSTER, DoomEntities.BARON2016, DoomMod.config.baron_spawn_weight, DoomMod.config.baron_min_group, DoomMod.config.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.baron_biomes, context)), MobCategory.MONSTER, DoomEntities.FIREBARON, DoomMod.config.baron_spawn_weight, DoomMod.config.baron_min_group, DoomMod.config.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomMod.config.armoredbaron_biomes, context)), MobCategory.MONSTER, DoomEntities.ARMORBARON, DoomMod.config.armoredbaron_spawn_weight, DoomMod.config.armoredbaron_min_group, DoomMod.config.armoredbaron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether().and(context -> parseBiomes(DoomMod.config.turret_biomes, context)), MobCategory.MONSTER, DoomEntities.TURRET, DoomMod.config.turret_spawn_weight, DoomMod.config.turret_min_group, DoomMod.config.turret_max_group);
		SpawnPlacements.register(DoomEntities.GLADIATOR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARCHVILE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ZOMBIEMAN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPIDERMASTERMIND, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARACHNOTRON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MANCUBUS, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.REVENANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.IMP, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PINKY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPECTRE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CACODEMON, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.LOST_SOUL, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.LOST_SOUL_ETERNAL, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CHAINGUNNER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MARAUDER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SHOTGUNGUY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PAIN, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.HELLKNIGHT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.HELLKNIGHT2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CYBERDEMON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.UNWILLING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDSCIENTIST, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDSOLDIER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ICONOFSIN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MECHAZOMBIE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.GORE_NEST, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.GARGOYLE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CUEBALL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PROWLER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.DREADKNIGHT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.IMP_STONE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDWORKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.DOOMHUNTER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.WHIPLASH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.FIREBARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BARON2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARMORBARON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARACHNOTRONETERNAL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MAYKRDRONE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPIDERMASTERMIND2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BLOODMAYKR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARCHMAKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.TENTACLE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MOTHERDEMON, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.TURRET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SUMMONER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.REVENANT2016, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
	}

	private static boolean parseBiomes(String[] biomes, BiomeSelectionContext biomeContext) {
		List<String> biomelist = Arrays.asList(biomes);
		return biomelist.contains(biomeContext.getBiomeKey().location().toString()); 
	}
}