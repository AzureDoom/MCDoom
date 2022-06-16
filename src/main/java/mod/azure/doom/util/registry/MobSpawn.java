package mod.azure.doom.util.registry;

import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class MobSpawn {

	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.IMP,
				DoomConfig.imp_spawn_weight, DoomConfig.imp_min_group, DoomConfig.imp_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.IMP_STONE,
				DoomConfig.impstone_spawn_weight, DoomConfig.impstone_min_group, DoomConfig.impstone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.PINKY,
				DoomConfig.pinky_spawn_weight, DoomConfig.pinky_min_group, DoomConfig.pinky_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.SPECTRE,
				DoomConfig.spectre_spawn_weight, DoomConfig.spectre_min_group, DoomConfig.spectre_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.LOST_SOUL,
				DoomConfig.lost_soul_spawn_weight, DoomConfig.lost_soul_min_group, DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.LOST_SOUL_ETERNAL, DoomConfig.lost_soul_spawn_weight, DoomConfig.lost_soul_min_group,
				DoomConfig.lost_soul_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.CACODEMON,
				DoomConfig.cacodemon_spawn_weight, DoomConfig.cacodemon_min_group, DoomConfig.cacodemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.ARCHVILE,
				DoomConfig.archvile_spawn_weight, DoomConfig.archvile_min_group, DoomConfig.archvile_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.BARON,
				DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group, DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.MANCUBUS,
				DoomConfig.mancubus_spawn_weight, DoomConfig.mancubus_min_group, DoomConfig.mancubus_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.REVENANT,
				DoomConfig.revenant_spawn_weight, DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.REVENANT2016,
				DoomConfig.revenant_spawn_weight, DoomConfig.revenant_min_group, DoomConfig.revenant_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.SPIDERMASTERMIND, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.ZOMBIEMAN,
				DoomConfig.zombieman_spawn_weight, DoomConfig.zombieman_min_group, DoomConfig.zombieman_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.ARACHNOTRON,
				DoomConfig.arachnotron_spawn_weight, DoomConfig.arachnotron_min_group,
				DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.ARACHNOTRONETERNAL, DoomConfig.arachnotron_spawn_weight, DoomConfig.arachnotron_min_group,
				DoomConfig.arachnotron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.GARGOYLE,
				DoomConfig.gargoyle_spawn_weight, DoomConfig.gargoyle_min_group, DoomConfig.gargoyle_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.CHAINGUNNER,
				DoomConfig.chaingunner_spawn_weight, DoomConfig.chaingunner_min_group,
				DoomConfig.chaingunner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.SHOTGUNGUY,
				DoomConfig.shotgunguy_spawn_weight, DoomConfig.shotgunguy_min_group, DoomConfig.shotgunguy_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.MARAUDER,
				DoomConfig.marauder_spawn_weight, DoomConfig.marauder_min_group, DoomConfig.marauder_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.PAIN,
				DoomConfig.pain_spawn_weight, DoomConfig.pain_min_group, DoomConfig.pain_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.HELLKNIGHT,
				DoomConfig.hellknight_spawn_weight, DoomConfig.hellknight_min_group, DoomConfig.hellknight_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.HELLKNIGHT2016,
				DoomConfig.hellknight2016_spawn_weight, DoomConfig.hellknight2016_min_group,
				DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.DREADKNIGHT,
				DoomConfig.hellknight2016_spawn_weight, DoomConfig.hellknight2016_min_group,
				DoomConfig.hellknight2016_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.CYBERDEMON,
				DoomConfig.cyberdemon_spawn_weight, DoomConfig.cyberdemon_min_group, DoomConfig.cyberdemon_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.UNWILLING,
				DoomConfig.unwilling_spawn_weight, DoomConfig.unwilling_min_group, DoomConfig.unwilling_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.POSSESSEDSCIENTIST, DoomConfig.possessed_scientist_spawn_weight,
				DoomConfig.possessed_scientist_min_group, DoomConfig.possessed_scientist_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.POSSESSEDSOLDIER, DoomConfig.possessed_soldier_spawn_weight,
				DoomConfig.possessed_soldier_min_group, DoomConfig.possessed_soldier_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.MECHAZOMBIE,
				DoomConfig.mechazombie_spawn_weight, DoomConfig.mechazombie_min_group,
				DoomConfig.mechazombie_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.CUEBALL,
				DoomConfig.cueball_spawn_weight, DoomConfig.cueball_min_group, DoomConfig.cueball_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.PROWLER,
				DoomConfig.prowler_spawn_weight, DoomConfig.prowler_min_group, DoomConfig.prowler_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.GORE_NEST,
				DoomConfig.gorenest_spawn_weight, DoomConfig.gorenest_min_group, DoomConfig.gorenest_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.POSSESSEDWORKER,
				DoomConfig.possessed_worker_spawn_weight, DoomConfig.possessed_worker_min_group,
				DoomConfig.possessed_worker_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER,
				DoomEntities.SPIDERMASTERMIND2016, DoomConfig.spider_mastermind_spawn_weight,
				DoomConfig.spider_mastermind_min_group, DoomConfig.spider_mastermind_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.DOOMHUNTER,
				DoomConfig.doomhunter_spawn_weight, DoomConfig.doomhunter_min_group, DoomConfig.doomhunter_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.TENTACLE,
				DoomConfig.tentacle_spawn_weight, DoomConfig.tentacle_min_group, DoomConfig.tentacle_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.SUMMONER,
				DoomConfig.summoner_spawn_weight, DoomConfig.summoner_min_group, DoomConfig.summoner_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.WHIPLASH,
				DoomConfig.whiplash_spawn_weight, DoomConfig.whiplash_min_group, DoomConfig.whiplash_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.BARON2016,
				DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group, DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.FIREBARON,
				DoomConfig.baron_spawn_weight, DoomConfig.baron_min_group, DoomConfig.baron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.ARMORBARON,
				DoomConfig.armoredbaron_spawn_weight, DoomConfig.armoredbaron_min_group,
				DoomConfig.armoredbaron_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, DoomEntities.MAYKRDRONE,
				DoomConfig.maykrdrone_spawn_weight, DoomConfig.maykrdrone_min_group, DoomConfig.maykrdrone_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, DoomEntities.BLOODMAYKR,
				DoomConfig.bloodmaykr_spawn_weight, DoomConfig.bloodmaykr_min_group, DoomConfig.bloodmaykr_max_group);
		BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.MONSTER, DoomEntities.TURRET,
				DoomConfig.turret_spawn_weight, DoomConfig.turret_min_group, DoomConfig.turret_max_group);
		SpawnRestrictionAccessor.callRegister(DoomEntities.GLADIATOR, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ARCHVILE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ZOMBIEMAN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.SPIDERMASTERMIND, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ARACHNOTRON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.MANCUBUS, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.BARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.REVENANT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.IMP, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.PINKY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.SPECTRE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.CACODEMON, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.LOST_SOUL, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.LOST_SOUL_ETERNAL, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.CHAINGUNNER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.MARAUDER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.SHOTGUNGUY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.PAIN, SpawnRestriction.Location.IN_LAVA,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.HELLKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.HELLKNIGHT2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.CYBERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.UNWILLING, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.POSSESSEDSCIENTIST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.POSSESSEDSOLDIER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ICONOFSIN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.MECHAZOMBIE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.GORE_NEST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.GARGOYLE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.CUEBALL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.PROWLER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.DREADKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.IMP_STONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.POSSESSEDWORKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.DOOMHUNTER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.WHIPLASH, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.FIREBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.BARON2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ARMORBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ARACHNOTRONETERNAL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.MAYKRDRONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.SPIDERMASTERMIND2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.BLOODMAYKR, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.ARCHMAKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.TENTACLE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.MOTHERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.TURRET, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.SUMMONER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		SpawnRestrictionAccessor.callRegister(DoomEntities.REVENANT2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
	}
}