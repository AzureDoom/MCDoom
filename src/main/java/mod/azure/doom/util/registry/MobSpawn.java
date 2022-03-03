package mod.azure.doom.util.registry;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig.Spawning;
import mod.azure.doom.entity.DemonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class MobSpawn {

	private static Spawning config = DoomMod.config.spawn;

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.imp_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.imp_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.IMP_STONE, config.impstone_spawn_weight, config.impstone_min_group,
				config.impstone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.pinky_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PINKY, config.pinky_spawn_weight, config.pinky_min_group,
				config.pinky_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.spectre_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPECTRE, config.spectre_spawn_weight, config.spectre_min_group,
				config.spectre_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.lost_soul_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.LOST_SOUL, config.lost_soul_spawn_weight, config.lost_soul_min_group,
				config.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.lost_soul_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.LOST_SOUL_ETERNAL, config.lost_soul_spawn_weight,
				config.lost_soul_min_group, config.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.cacodemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CACODEMON, config.cacodemon_spawn_weight, config.cacodemon_min_group,
				config.cacodemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.archvile_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHVILE, config.archvile_spawn_weight, config.archvile_min_group,
				config.archvile_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BARON, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.mancubus_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MANCUBUS, config.mancubus_spawn_weight, config.mancubus_min_group,
				config.mancubus_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.revenant_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT, config.revenant_spawn_weight, config.revenant_min_group,
				config.revenant_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.revenant_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT2016, config.revenant_spawn_weight,
				config.revenant_min_group, config.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.spider_mastermind_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND, config.spider_mastermind_spawn_weight,
				config.spider_mastermind_min_group, config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.zombieman_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ZOMBIEMAN, config.zombieman_spawn_weight, config.zombieman_min_group,
				config.zombieman_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.arachnotron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRON, config.arachnotron_spawn_weight,
				config.arachnotron_min_group, config.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.arachnotron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRONETERNAL, config.arachnotron_spawn_weight,
				config.arachnotron_min_group, config.arachnotron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.gargoyle_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GARGOYLE, config.gargoyle_spawn_weight, config.gargoyle_min_group,
				config.gargoyle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.chaingunner_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CHAINGUNNER, config.chaingunner_spawn_weight,
				config.chaingunner_min_group, config.chaingunner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.shotgunguy_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SHOTGUNGUY, config.shotgunguy_spawn_weight,
				config.shotgunguy_min_group, config.shotgunguy_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.marauder_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MARAUDER, config.marauder_spawn_weight, config.marauder_min_group,
				config.marauder_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.pain_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PAIN, config.pain_spawn_weight, config.pain_min_group,
				config.pain_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.hellknight_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT, config.hellknight_spawn_weight,
				config.hellknight_min_group, config.hellknight_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.hellknight2016_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT2016, config.hellknight2016_spawn_weight,
				config.hellknight2016_min_group, config.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.hellknight2016_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.DREADKNIGHT, config.hellknight2016_spawn_weight,
				config.hellknight2016_min_group, config.hellknight2016_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.cyberdemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CYBERDEMON, config.cyberdemon_spawn_weight,
				config.cyberdemon_min_group, config.cyberdemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.unwilling_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.UNWILLING, config.unwilling_spawn_weight, config.unwilling_min_group,
				config.unwilling_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.possessed_scientist_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSCIENTIST, config.possessed_scientist_spawn_weight,
				config.possessed_scientist_min_group, config.possessed_scientist_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.possessed_soldier_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSOLDIER, config.possessed_soldier_spawn_weight,
				config.possessed_soldier_min_group, config.possessed_soldier_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.mechazombie_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MECHAZOMBIE, config.mechazombie_spawn_weight,
				config.mechazombie_min_group, config.mechazombie_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.cueball_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.CUEBALL, config.cueball_spawn_weight, config.cueball_min_group,
				config.cueball_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.prowler_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.PROWLER, config.prowler_spawn_weight, config.prowler_min_group,
				config.prowler_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.gorenest_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GORE_NEST, config.gorenest_spawn_weight, config.gorenest_min_group,
				config.gorenest_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.possessed_worker_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDWORKER, config.possessed_worker_spawn_weight,
				config.possessed_worker_min_group, config.possessed_worker_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.spider_mastermind_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND2016, config.spider_mastermind_spawn_weight,
				config.spider_mastermind_min_group, config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.doomhunter_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.DOOMHUNTER, config.doomhunter_spawn_weight,
				config.doomhunter_min_group, config.doomhunter_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.tentacle_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.TENTACLE, config.tentacle_spawn_weight, config.tentacle_min_group,
				config.tentacle_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.summoner_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.SUMMONER, config.summoner_spawn_weight, config.summoner_min_group,
				config.summoner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.whiplash_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.WHIPLASH, config.whiplash_spawn_weight, config.whiplash_min_group,
				config.whiplash_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BARON2016, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.baron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.FIREBARON, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.armoredbaron_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARMORBARON, config.armoredbaron_spawn_weight,
				config.armoredbaron_min_group, config.armoredbaron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.all().and(context -> parseBiomes(config.motherdemon_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MOTHERDEMON, config.motherdemon_spawn_weight,
				config.motherdemon_min_group, config.motherdemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.maykrdrone_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.MAYKRDRONE, config.maykrdrone_spawn_weight,
				config.maykrdrone_min_group, config.maykrdrone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.bloodmaykr_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.BLOODMAYKR, config.bloodmaykr_spawn_weight,
				config.bloodmaykr_min_group, config.bloodmaykr_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.archmaykr_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHMAKER, config.archmaykr_spawn_weight, config.archmaykr_min_group,
				config.archmaykr_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.all().and(context -> parseBiomes(config.gladiator_biomes, context)),
				SpawnGroup.MONSTER, ModEntityTypes.GLADIATOR, config.gladiator_spawn_weight, config.gladiator_min_group,
				config.gladiator_max_group);
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

	@SuppressWarnings("deprecation")
	private static boolean parseBiomes(List<String> biomes, BiomeSelectionContext biomeContext) {
		return biomes.contains(biomeContext.getBiomeKey().getValue().toString())
				|| biomes.contains("#" + Biome.getCategory(biomeContext.getBiomeRegistryEntry()).asString());
	}
}