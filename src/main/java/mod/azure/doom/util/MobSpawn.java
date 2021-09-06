package mod.azure.doom.util;

import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig.Spawning;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("deprecation")
public class MobSpawn {

	private static Spawning config = DoomMod.config.spawn;

	/* Major fucking thanks to Corgi Taco for figuring this shit out */
	public static void addSpawnEntries() {
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP_STONE, config.impstone_spawn_weight, config.impstone_min_group,
				config.impstone_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.PINKY, config.pinky_spawn_weight, config.pinky_min_group,
				config.pinky_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.SPECTRE, config.spectre_spawn_weight, config.spectre_min_group,
				config.spectre_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP, config.imp_spawn_weight, config.imp_min_group,
				config.imp_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.CYBERDEMON2016, config.cyberdemon2016_spawn_weight,
				config.cyberdemon2016_min_group, config.cyberdemon2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDWORKER, config.possessed_worker_spawn_weight,
				config.possessed_worker_min_group, config.possessed_worker_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND2016, config.spider_mastermind_spawn_weight,
				config.spider_mastermind_min_group, config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.PINKY2016, config.pinky_spawn_weight, config.pinky_min_group,
				config.pinky_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHVILEETERNAL, config.archvile_spawn_weight,
				config.archvile_min_group, config.archvile_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.TENTACLE, config.tentacle_spawn_weight, config.tentacle_min_group,
				config.tentacle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.TYRANT, config.tyrant_spawn_weight, config.tyrant_min_group,
				config.tyrant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.LOST_SOUL, config.lost_soul_spawn_weight, config.lost_soul_min_group,
				config.lost_soul_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.CACODEMON, config.cacodemon_spawn_weight, config.cacodemon_min_group,
				config.cacodemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ARCHVILE, config.archvile_spawn_weight, config.archvile_min_group,
				config.archvile_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.BARON, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.MANCUBUS, config.mancubus_spawn_weight, config.mancubus_min_group,
				config.mancubus_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT, config.revenant_spawn_weight, config.revenant_min_group,
				config.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.REVENANT2016, config.revenant_spawn_weight, config.revenant_min_group,
				config.revenant_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.SPIDERMASTERMIND, config.spider_mastermind_spawn_weight,
				config.spider_mastermind_min_group, config.spider_mastermind_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ZOMBIEMAN, config.zombieman_spawn_weight, config.zombieman_min_group,
				config.zombieman_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRON, config.arachnotron_spawn_weight,
				config.arachnotron_min_group, config.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ARACHNOTRONETERNAL, config.arachnotron_spawn_weight,
				config.arachnotron_min_group, config.arachnotron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.IMP2016, config.imp2016_spawn_weight, config.imp2016_min_group,
				config.imp2016_min_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.GARGOYLE, config.gargoyle_spawn_weight, config.gargoyle_min_group,
				config.gargoyle_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.NIGHTMARE_IMP, config.nightmare_imp_spawn_weight,
				config.nightmare_min_group, config.nightmare_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.CHAINGUNNER, config.chaingunner_spawn_weight,
				config.chaingunner_min_group, config.chaingunner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.SHOTGUNGUY, config.shotgunguy_spawn_weight,
				config.shotgunguy_min_group, config.shotgunguy_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.MARAUDER, config.marauder_spawn_weight, config.marauder_min_group,
				config.marauder_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.PAIN, config.pain_spawn_weight, config.pain_min_group,
				config.pain_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT, config.hellknight_spawn_weight,
				config.hellknight_min_group, config.hellknight_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.HELLKNIGHT2016, config.hellknight2016_spawn_weight,
				config.hellknight2016_min_group, config.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.DREADKNIGHT, config.hellknight2016_spawn_weight,
				config.hellknight2016_min_group, config.hellknight2016_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.CYBERDEMON, config.cyberdemon_spawn_weight,
				config.cyberdemon_min_group, config.cyberdemon_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.UNWILLING, config.unwilling_spawn_weight, config.unwilling_min_group,
				config.unwilling_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSCIENTIST, config.possessed_scientist_spawn_weight,
				config.possessed_scientist_min_group, config.possessed_scientist_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.POSSESSEDSOLDIER, config.possessed_soldier_spawn_weight,
				config.possessed_soldier_min_group, config.possessed_soldier_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.MECHAZOMBIE, config.mechazombie_spawn_weight,
				config.mechazombie_min_group, config.mechazombie_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.CUEBALL, config.cueball_spawn_weight, config.cueball_min_group,
				config.cueball_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.PROWLER, config.prowler_spawn_weight, config.prowler_min_group,
				config.prowler_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.GORE_NEST, config.gorenest_spawn_weight, config.gorenest_min_group,
				config.gorenest_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.DOOMHUNTER, config.doomhunter_spawn_weight,
				config.doomhunter_min_group, config.doomhunter_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.WHIPLASH, config.whiplash_spawn_weight, config.whiplash_min_group,
				config.whiplash_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.SUMMONER, config.summoner_spawn_weight, config.summoner_min_group,
				config.summoner_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.BARON2016, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.FIREBARON, config.baron_spawn_weight, config.baron_min_group,
				config.baron_max_group);
		BiomeModifications.addSpawn(
				BiomeSelectors.foundInTheNether()
						.and(context -> context.getBiome().getCategory() == Biome.Category.NETHER),
				SpawnGroup.MONSTER, ModEntityTypes.ARMORBARON, config.armoredbaron_spawn_weight,
				config.armoredbaron_min_group, config.armoredbaron_max_group);
	}
}