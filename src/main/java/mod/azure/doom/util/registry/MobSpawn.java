package mod.azure.doom.util.registry;

import java.util.List;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.maykrdrone_biomes, context)),
				MobCategory.MONSTER, DoomEntities.MAYKRDRONE, DoomConfig.maykrdrone_spawn_weight,
				DoomConfig.maykrdrone_min_group, DoomConfig.maykrdrone_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.bloodmaykr_biomes, context)),
				MobCategory.MONSTER, DoomEntities.BLOODMAYKR, DoomConfig.bloodmaykr_spawn_weight,
				DoomConfig.bloodmaykr_min_group, DoomConfig.bloodmaykr_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.imp_biomes, context)),
				MobCategory.MONSTER, DoomEntities.IMP, DoomConfig.imp_spawn_weight, DoomConfig.imp_min_group,
				DoomConfig.imp_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.imp_biomes, context)),
				MobCategory.MONSTER, DoomEntities.IMP_STONE, DoomConfig.impstone_spawn_weight,
				DoomConfig.impstone_min_group, DoomConfig.impstone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.pinky_biomes, context)),
				MobCategory.MONSTER, DoomEntities.PINKY, DoomConfig.pinky_spawn_weight, DoomConfig.pinky_min_group,
				DoomConfig.pinky_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spectre_biomes, context)),
				MobCategory.MONSTER, DoomEntities.SPECTRE, DoomConfig.spectre_spawn_weight,
				DoomConfig.spectre_min_group, DoomConfig.spectre_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.lost_soul_biomes, context)),
				MobCategory.MONSTER, DoomEntities.LOST_SOUL, DoomConfig.lost_soul_spawn_weight,
				DoomConfig.lost_soul_min_group, DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.lost_soul_biomes, context)),
				MobCategory.MONSTER, DoomEntities.LOST_SOUL_ETERNAL, DoomConfig.lost_soul_spawn_weight,
				DoomConfig.lost_soul_min_group, DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cacodemon_biomes, context)),
				MobCategory.MONSTER, DoomEntities.CACODEMON, DoomConfig.cacodemon_spawn_weight,
				DoomConfig.cacodemon_min_group, DoomConfig.cacodemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.archvile_biomes, context)),
				MobCategory.MONSTER, DoomEntities.ARCHVILE, DoomConfig.archvile_spawn_weight,
				DoomConfig.archvile_min_group, DoomConfig.archvile_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.BARON, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.mancubus_biomes, context)),
				MobCategory.MONSTER, DoomEntities.MANCUBUS, DoomConfig.mancubus_spawn_weight,
				DoomConfig.mancubus_min_group, DoomConfig.mancubus_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.revenant_biomes, context)),
				MobCategory.MONSTER, DoomEntities.REVENANT, DoomConfig.revenant_spawn_weight,
				DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.revenant_biomes, context)),
				MobCategory.MONSTER, DoomEntities.REVENANT2016, DoomConfig.revenant_spawn_weight,
				DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spider_mastermind_biomes, context)),
				MobCategory.MONSTER, DoomEntities.SPIDERMASTERMIND, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.zombieman_biomes, context)),
				MobCategory.MONSTER, DoomEntities.ZOMBIEMAN, DoomConfig.zombieman_spawn_weight,
				DoomConfig.zombieman_min_group, DoomConfig.zombieman_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.arachnotron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.ARACHNOTRON, DoomConfig.arachnotron_spawn_weight,
				DoomConfig.arachnotron_min_group, DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.arachnotron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.ARACHNOTRONETERNAL, DoomConfig.arachnotron_spawn_weight,
				DoomConfig.arachnotron_min_group, DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.gargoyle_biomes, context)),
				MobCategory.MONSTER, DoomEntities.GARGOYLE, DoomConfig.gargoyle_spawn_weight,
				DoomConfig.gargoyle_min_group, DoomConfig.gargoyle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.chaingunner_biomes, context)),
				MobCategory.MONSTER, DoomEntities.CHAINGUNNER, DoomConfig.chaingunner_spawn_weight,
				DoomConfig.chaingunner_min_group, DoomConfig.chaingunner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.shotgunguy_biomes, context)),
				MobCategory.MONSTER, DoomEntities.SHOTGUNGUY, DoomConfig.shotgunguy_spawn_weight,
				DoomConfig.shotgunguy_min_group, DoomConfig.shotgunguy_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.marauder_biomes, context)),
				MobCategory.MONSTER, DoomEntities.MARAUDER, DoomConfig.marauder_spawn_weight,
				DoomConfig.marauder_min_group, DoomConfig.marauder_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.pain_biomes, context)),
				MobCategory.MONSTER, DoomEntities.PAIN, DoomConfig.pain_spawn_weight, DoomConfig.pain_min_group,
				DoomConfig.pain_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight_biomes, context)),
				MobCategory.MONSTER, DoomEntities.HELLKNIGHT, DoomConfig.hellknight_spawn_weight,
				DoomConfig.hellknight_min_group, DoomConfig.hellknight_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight2016_biomes, context)),
				MobCategory.MONSTER, DoomEntities.HELLKNIGHT2016, DoomConfig.hellknight2016_spawn_weight,
				DoomConfig.hellknight2016_min_group, DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight2016_biomes, context)),
				MobCategory.MONSTER, DoomEntities.DREADKNIGHT, DoomConfig.hellknight2016_spawn_weight,
				DoomConfig.hellknight2016_min_group, DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cyberdemon_biomes, context)),
				MobCategory.MONSTER, DoomEntities.CYBERDEMON, DoomConfig.cyberdemon_spawn_weight,
				DoomConfig.cyberdemon_min_group, DoomConfig.cyberdemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.unwilling_biomes, context)),
				MobCategory.MONSTER, DoomEntities.UNWILLING, DoomConfig.unwilling_spawn_weight,
				DoomConfig.unwilling_min_group, DoomConfig.unwilling_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_scientist_biomes, context)),
				MobCategory.MONSTER, DoomEntities.POSSESSEDSCIENTIST, DoomConfig.possessed_scientist_spawn_weight,
				DoomConfig.possessed_scientist_min_group, DoomConfig.possessed_scientist_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_soldier_biomes, context)),
				MobCategory.MONSTER, DoomEntities.POSSESSEDSOLDIER, DoomConfig.possessed_soldier_spawn_weight,
				DoomConfig.possessed_soldier_min_group, DoomConfig.possessed_soldier_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.mechazombie_biomes, context)),
				MobCategory.MONSTER, DoomEntities.MECHAZOMBIE, DoomConfig.mechazombie_spawn_weight,
				DoomConfig.mechazombie_min_group, DoomConfig.mechazombie_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cueball_biomes, context)),
				MobCategory.MONSTER, DoomEntities.CUEBALL, DoomConfig.cueball_spawn_weight,
				DoomConfig.cueball_min_group, DoomConfig.cueball_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.prowler_biomes, context)),
				MobCategory.MONSTER, DoomEntities.PROWLER, DoomConfig.prowler_spawn_weight,
				DoomConfig.prowler_min_group, DoomConfig.prowler_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.gorenest_biomes, context)),
				MobCategory.MONSTER, DoomEntities.GORE_NEST, DoomConfig.gorenest_spawn_weight,
				DoomConfig.gorenest_min_group, DoomConfig.gorenest_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_worker_biomes, context)),
				MobCategory.MONSTER, DoomEntities.POSSESSEDWORKER, DoomConfig.possessed_worker_spawn_weight,
				DoomConfig.possessed_worker_min_group, DoomConfig.possessed_worker_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spider_mastermind_biomes, context)),
				MobCategory.MONSTER, DoomEntities.SPIDERMASTERMIND2016, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.doomhunter_biomes, context)),
				MobCategory.MONSTER, DoomEntities.DOOMHUNTER, DoomConfig.doomhunter_spawn_weight,
				DoomConfig.doomhunter_min_group, DoomConfig.doomhunter_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.tentacle_biomes, context)),
				MobCategory.MONSTER, DoomEntities.TENTACLE, DoomConfig.tentacle_spawn_weight,
				DoomConfig.tentacle_min_group, DoomConfig.tentacle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.summoner_biomes, context)),
				MobCategory.MONSTER, DoomEntities.SUMMONER, DoomConfig.summoner_spawn_weight,
				DoomConfig.summoner_min_group, DoomConfig.summoner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.whiplash_biomes, context)),
				MobCategory.MONSTER, DoomEntities.WHIPLASH, DoomConfig.whiplash_spawn_weight,
				DoomConfig.whiplash_min_group, DoomConfig.whiplash_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.BARON2016, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.FIREBARON, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.armoredbaron_biomes, context)),
				MobCategory.MONSTER, DoomEntities.ARMORBARON, DoomConfig.armoredbaron_spawn_weight,
				DoomConfig.armoredbaron_min_group, DoomConfig.armoredbaron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether().and(context -> parseBiomes(DoomConfig.turret_biomes, context)),
				MobCategory.MONSTER, DoomEntities.TURRET, DoomConfig.turret_spawn_weight, DoomConfig.turret_min_group,
				DoomConfig.turret_max_group);
		SpawnPlacements.register(DoomEntities.GLADIATOR, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARCHVILE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ZOMBIEMAN, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPIDERMASTERMIND, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARACHNOTRON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MANCUBUS, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BARON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.REVENANT, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.IMP, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PINKY, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPECTRE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CACODEMON, SpawnPlacements.Type.IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.LOST_SOUL, SpawnPlacements.Type.IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.LOST_SOUL_ETERNAL, SpawnPlacements.Type.IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CHAINGUNNER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MARAUDER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SHOTGUNGUY, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PAIN, SpawnPlacements.Type.IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.HELLKNIGHT, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.HELLKNIGHT2016, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CYBERDEMON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.UNWILLING, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDSCIENTIST, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDSOLDIER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ICONOFSIN, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MECHAZOMBIE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.GORE_NEST, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.GARGOYLE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.CUEBALL, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.PROWLER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.DREADKNIGHT, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.IMP_STONE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.POSSESSEDWORKER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.DOOMHUNTER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.WHIPLASH, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.FIREBARON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BARON2016, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARMORBARON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARACHNOTRONETERNAL, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MAYKRDRONE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SPIDERMASTERMIND2016, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.BLOODMAYKR, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.ARCHMAKER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.TENTACLE, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.MOTHERDEMON, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.TURRET, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.SUMMONER, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnPlacements.register(DoomEntities.REVENANT2016, SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().registry().toString());
	}
}