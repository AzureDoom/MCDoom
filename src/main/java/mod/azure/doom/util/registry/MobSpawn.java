package mod.azure.doom.util.registry;

import java.util.List;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.imp_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, DoomConfig.imp_spawn_weight, DoomConfig.imp_min_group,
				DoomConfig.imp_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.imp_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.IMP_STONE, DoomConfig.impstone_spawn_weight,
				DoomConfig.impstone_min_group, DoomConfig.impstone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.pinky_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PINKY, DoomConfig.pinky_spawn_weight, DoomConfig.pinky_min_group,
				DoomConfig.pinky_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spectre_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPECTRE, DoomConfig.spectre_spawn_weight,
				DoomConfig.spectre_min_group, DoomConfig.spectre_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.lost_soul_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.LOST_SOUL, DoomConfig.lost_soul_spawn_weight,
				DoomConfig.lost_soul_min_group, DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.lost_soul_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.LOST_SOUL_ETERNAL, DoomConfig.lost_soul_spawn_weight,
				DoomConfig.lost_soul_min_group, DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cacodemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CACODEMON, DoomConfig.cacodemon_spawn_weight,
				DoomConfig.cacodemon_min_group, DoomConfig.cacodemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.archvile_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHVILE, DoomConfig.archvile_spawn_weight,
				DoomConfig.archvile_min_group, DoomConfig.archvile_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BARON, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.mancubus_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MANCUBUS, DoomConfig.mancubus_spawn_weight,
				DoomConfig.mancubus_min_group, DoomConfig.mancubus_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.revenant_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT, DoomConfig.revenant_spawn_weight,
				DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.revenant_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT2016, DoomConfig.revenant_spawn_weight,
				DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spider_mastermind_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.zombieman_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ZOMBIEMAN, DoomConfig.zombieman_spawn_weight,
				DoomConfig.zombieman_min_group, DoomConfig.zombieman_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.arachnotron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRON, DoomConfig.arachnotron_spawn_weight,
				DoomConfig.arachnotron_min_group, DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.arachnotron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRONETERNAL, DoomConfig.arachnotron_spawn_weight,
				DoomConfig.arachnotron_min_group, DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.gargoyle_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GARGOYLE, DoomConfig.gargoyle_spawn_weight,
				DoomConfig.gargoyle_min_group, DoomConfig.gargoyle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.chaingunner_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CHAINGUNNER, DoomConfig.chaingunner_spawn_weight,
				DoomConfig.chaingunner_min_group, DoomConfig.chaingunner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.shotgunguy_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SHOTGUNGUY, DoomConfig.shotgunguy_spawn_weight,
				DoomConfig.shotgunguy_min_group, DoomConfig.shotgunguy_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.marauder_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MARAUDER, DoomConfig.marauder_spawn_weight,
				DoomConfig.marauder_min_group, DoomConfig.marauder_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.pain_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PAIN, DoomConfig.pain_spawn_weight, DoomConfig.pain_min_group,
				DoomConfig.pain_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT, DoomConfig.hellknight_spawn_weight,
				DoomConfig.hellknight_min_group, DoomConfig.hellknight_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight2016_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT2016, DoomConfig.hellknight2016_spawn_weight,
				DoomConfig.hellknight2016_min_group, DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.hellknight2016_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.DREADKNIGHT, DoomConfig.hellknight2016_spawn_weight,
				DoomConfig.hellknight2016_min_group, DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cyberdemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CYBERDEMON, DoomConfig.cyberdemon_spawn_weight,
				DoomConfig.cyberdemon_min_group, DoomConfig.cyberdemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.unwilling_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.UNWILLING, DoomConfig.unwilling_spawn_weight,
				DoomConfig.unwilling_min_group, DoomConfig.unwilling_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_scientist_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSCIENTIST, DoomConfig.possessed_scientist_spawn_weight,
				DoomConfig.possessed_scientist_min_group, DoomConfig.possessed_scientist_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_soldier_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSOLDIER, DoomConfig.possessed_soldier_spawn_weight,
				DoomConfig.possessed_soldier_min_group, DoomConfig.possessed_soldier_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.mechazombie_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MECHAZOMBIE, DoomConfig.mechazombie_spawn_weight,
				DoomConfig.mechazombie_min_group, DoomConfig.mechazombie_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.cueball_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CUEBALL, DoomConfig.cueball_spawn_weight,
				DoomConfig.cueball_min_group, DoomConfig.cueball_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.prowler_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PROWLER, DoomConfig.prowler_spawn_weight,
				DoomConfig.prowler_min_group, DoomConfig.prowler_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.gorenest_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GORE_NEST, DoomConfig.gorenest_spawn_weight,
				DoomConfig.gorenest_min_group, DoomConfig.gorenest_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.possessed_worker_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDWORKER, DoomConfig.possessed_worker_spawn_weight,
				DoomConfig.possessed_worker_min_group, DoomConfig.possessed_worker_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.spider_mastermind_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND2016, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.doomhunter_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.DOOMHUNTER, DoomConfig.doomhunter_spawn_weight,
				DoomConfig.doomhunter_min_group, DoomConfig.doomhunter_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.tentacle_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.TENTACLE, DoomConfig.tentacle_spawn_weight,
				DoomConfig.tentacle_min_group, DoomConfig.tentacle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.summoner_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SUMMONER, DoomConfig.summoner_spawn_weight,
				DoomConfig.summoner_min_group, DoomConfig.summoner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.whiplash_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.WHIPLASH, DoomConfig.whiplash_spawn_weight,
				DoomConfig.whiplash_min_group, DoomConfig.whiplash_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BARON2016, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.FIREBARON, DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group,
				DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.armoredbaron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARMORBARON, DoomConfig.armoredbaron_spawn_weight,
				DoomConfig.armoredbaron_min_group, DoomConfig.armoredbaron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.motherdemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MOTHERDEMON, DoomConfig.motherdemon_spawn_weight,
				DoomConfig.motherdemon_min_group, DoomConfig.motherdemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.maykrdrone_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MAYKRDRONE, DoomConfig.maykrdrone_spawn_weight,
				DoomConfig.maykrdrone_min_group, DoomConfig.maykrdrone_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.bloodmaykr_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BLOODMAYKR, DoomConfig.bloodmaykr_spawn_weight,
				DoomConfig.bloodmaykr_min_group, DoomConfig.bloodmaykr_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.archmaykr_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHMAKER, DoomConfig.archmaykr_spawn_weight,
				DoomConfig.archmaykr_min_group, DoomConfig.archmaykr_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(DoomConfig.gladiator_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GLADIATOR, DoomConfig.gladiator_spawn_weight,
				DoomConfig.gladiator_min_group, DoomConfig.gladiator_max_group);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.GLADIATOR, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ARCHVILE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ZOMBIEMAN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.SPIDERMASTERMIND, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ARACHNOTRON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.MANCUBUS, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.BARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.REVENANT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.IMP, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.PINKY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.SPECTRE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.CACODEMON, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.LOST_SOUL, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.LOST_SOUL_ETERNAL, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.CHAINGUNNER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.MARAUDER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.SHOTGUNGUY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.PAIN, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.HELLKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.HELLKNIGHT2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.CYBERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.UNWILLING, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.POSSESSEDSCIENTIST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.POSSESSEDSOLDIER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ICONOFSIN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.MECHAZOMBIE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.GORE_NEST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.GARGOYLE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.CUEBALL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.PROWLER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.DREADKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.IMP_STONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.POSSESSEDWORKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.DOOMHUNTER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.WHIPLASH, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.FIREBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.BARON2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ARMORBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ARACHNOTRONETERNAL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.MAYKRDRONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.SPIDERMASTERMIND2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.BLOODMAYKR, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.ARCHMAKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.TENTACLE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.MOTHERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.TURRET, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.SUMMONER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(ModEntityTypes.REVENANT2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
	}

	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + biomeContext.getBiomeRegistryEntry().toString());
	}
}