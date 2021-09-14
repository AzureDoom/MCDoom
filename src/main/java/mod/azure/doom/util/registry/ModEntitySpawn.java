package mod.azure.doom.util.registry;

import java.util.List;

import mod.azure.doom.util.config.DoomConfig;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntitySpawn {

	public static void onBiomesLoad(BiomeLoadingEvent event) {
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		List<SpawnerData> base = event.getSpawns().getSpawner(MobCategory.MONSTER);
		if (parseBiomes(DoomConfig.SERVER.imp_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.IMP.get(), DoomConfig.SERVER.imp_spawn_weight.get(),
					DoomConfig.SERVER.imp_min_group.get(), DoomConfig.SERVER.imp_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.imp_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.IMP_STONE.get(), DoomConfig.SERVER.impstone_spawn_weight.get(),
					DoomConfig.SERVER.impstone_min_group.get(), DoomConfig.SERVER.impstone_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.pinky_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.PINKY.get(), DoomConfig.SERVER.pinky_spawn_weight.get(),
					DoomConfig.SERVER.pinky_min_group.get(), DoomConfig.SERVER.pinky_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.spectre_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.SPECTRE.get(), DoomConfig.SERVER.spectre_spawn_weight.get(),
					DoomConfig.SERVER.spectre_min_group.get(), DoomConfig.SERVER.spectre_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.lost_soul_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.LOST_SOUL.get(), DoomConfig.SERVER.lost_soul_spawn_weight.get(),
					DoomConfig.SERVER.lost_soul_min_group.get(), DoomConfig.SERVER.lost_soul_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.cacodemon_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.CACODEMON.get(), DoomConfig.SERVER.cacodemon_spawn_weight.get(),
					DoomConfig.SERVER.cacodemon_min_group.get(), DoomConfig.SERVER.cacodemon_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.archvile_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.ARCHVILE.get(), DoomConfig.SERVER.archvile_spawn_weight.get(),
					DoomConfig.SERVER.archvile_min_group.get(), DoomConfig.SERVER.archvile_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.BARON.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
					DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.mancubus_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.MANCUBUS.get(), DoomConfig.SERVER.mancubus_spawn_weight.get(),
					DoomConfig.SERVER.mancubus_min_group.get(), DoomConfig.SERVER.mancubus_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.revenant_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.REVENANT.get(), DoomConfig.SERVER.revenant_spawn_weight.get(),
					DoomConfig.SERVER.revenant_min_group.get(), DoomConfig.SERVER.revenant_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.revenant_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.REVENANT2016.get(), DoomConfig.SERVER.revenant_spawn_weight.get(),
					DoomConfig.SERVER.revenant_min_group.get(), DoomConfig.SERVER.revenant_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.spider_mastermind_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.SPIDERMASTERMIND.get(),
					DoomConfig.SERVER.spider_mastermind_spawn_weight.get(),
					DoomConfig.SERVER.spider_mastermind_min_group.get(),
					DoomConfig.SERVER.spider_mastermind_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.zombieman_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.ZOMBIEMAN.get(), DoomConfig.SERVER.zombieman_spawn_weight.get(),
					DoomConfig.SERVER.zombieman_min_group.get(), DoomConfig.SERVER.zombieman_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.arachnotron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.ARACHNOTRON.get(), DoomConfig.SERVER.arachnotron_spawn_weight.get(),
					DoomConfig.SERVER.arachnotron_min_group.get(), DoomConfig.SERVER.arachnotron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.arachnotron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.ARACHNOTRONETERNAL.get(),
					DoomConfig.SERVER.arachnotron_spawn_weight.get(), DoomConfig.SERVER.arachnotron_min_group.get(),
					DoomConfig.SERVER.arachnotron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.imp2016_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.IMP2016.get(), DoomConfig.SERVER.imp2016_spawn_weight.get(),
					DoomConfig.SERVER.imp2016_min_group.get(), DoomConfig.SERVER.imp2016_min_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.gargoyle_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.GARGOYLE.get(), DoomConfig.SERVER.gargoyle_spawn_weight.get(),
					DoomConfig.SERVER.gargoyle_min_group.get(), DoomConfig.SERVER.gargoyle_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.nightmare_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.NIGHTMARE_IMP.get(),
					DoomConfig.SERVER.nightmare_spawn_weight.get(), DoomConfig.SERVER.nightmare_min_group.get(),
					DoomConfig.SERVER.nightmare_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.chaingunner_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.CHAINGUNNER.get(), DoomConfig.SERVER.chaingunner_spawn_weight.get(),
					DoomConfig.SERVER.chaingunner_min_group.get(), DoomConfig.SERVER.chaingunner_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.shotgunguy_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.SHOTGUNGUY.get(), DoomConfig.SERVER.shotgunguy_spawn_weight.get(),
					DoomConfig.SERVER.shotgunguy_min_group.get(), DoomConfig.SERVER.shotgunguy_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.marauder_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.MARAUDER.get(), DoomConfig.SERVER.marauder_spawn_weight.get(),
					DoomConfig.SERVER.marauder_min_group.get(), DoomConfig.SERVER.marauder_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.pain_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.PAIN.get(), DoomConfig.SERVER.pain_spawn_weight.get(),
					DoomConfig.SERVER.pain_min_group.get(), DoomConfig.SERVER.pain_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.hellknight_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.HELLKNIGHT.get(), DoomConfig.SERVER.hellknight_spawn_weight.get(),
					DoomConfig.SERVER.hellknight_min_group.get(), DoomConfig.SERVER.hellknight_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.hellknight2016_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.HELLKNIGHT2016.get(),
					DoomConfig.SERVER.hellknight2016_spawn_weight.get(),
					DoomConfig.SERVER.hellknight2016_min_group.get(),
					DoomConfig.SERVER.hellknight2016_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.hellknight2016_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.DREADKNIGHT.get(),
					DoomConfig.SERVER.hellknight2016_spawn_weight.get(),
					DoomConfig.SERVER.hellknight2016_min_group.get(),
					DoomConfig.SERVER.hellknight2016_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.cyberdemon_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.CYBERDEMON.get(), DoomConfig.SERVER.cyberdemon_spawn_weight.get(),
					DoomConfig.SERVER.cyberdemon_min_group.get(), DoomConfig.SERVER.cyberdemon_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.unwilling_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.UNWILLING.get(), DoomConfig.SERVER.unwilling_spawn_weight.get(),
					DoomConfig.SERVER.unwilling_min_group.get(), DoomConfig.SERVER.unwilling_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.possessed_scientist_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.POSSESSEDSCIENTIST.get(),
					DoomConfig.SERVER.possessed_scientist_spawn_weight.get(),
					DoomConfig.SERVER.possessed_scientist_min_group.get(),
					DoomConfig.SERVER.possessed_scientist_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.possessed_soldier_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.POSSESSEDSOLDIER.get(),
					DoomConfig.SERVER.possessed_soldier_spawn_weight.get(),
					DoomConfig.SERVER.possessed_soldier_min_group.get(),
					DoomConfig.SERVER.possessed_soldier_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.mechazombie_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.MECHAZOMBIE.get(), DoomConfig.SERVER.mechazombie_spawn_weight.get(),
					DoomConfig.SERVER.mechazombie_min_group.get(), DoomConfig.SERVER.mechazombie_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.cueball_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.CUEBALL.get(), DoomConfig.SERVER.cueball_spawn_weight.get(),
					DoomConfig.SERVER.cueball_min_group.get(), DoomConfig.SERVER.cueball_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.prowler_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.PROWLER.get(), DoomConfig.SERVER.prowler_spawn_weight.get(),
					DoomConfig.SERVER.prowler_min_group.get(), DoomConfig.SERVER.prowler_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.gorenest_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.GORE_NEST.get(), DoomConfig.SERVER.gorenest_spawn_weight.get(),
					DoomConfig.SERVER.gorenest_min_group.get(), DoomConfig.SERVER.gorenest_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.cyberdemon2016_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.CYBERDEMON2016.get(),
					DoomConfig.SERVER.cyberdemon2016_spawn_weight.get(),
					DoomConfig.SERVER.cyberdemon2016_min_group.get(),
					DoomConfig.SERVER.cyberdemon2016_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.possessed_worker_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.POSSESSEDWORKER.get(),
					DoomConfig.SERVER.possessed_worker_spawn_weight.get(),
					DoomConfig.SERVER.possessed_worker_min_group.get(),
					DoomConfig.SERVER.possessed_worker_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.doomhunter_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.DOOMHUNTER.get(), DoomConfig.SERVER.doomhunter_spawn_weight.get(),
					DoomConfig.SERVER.doomhunter_min_group.get(), DoomConfig.SERVER.doomhunter_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.pinky_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.PINKY2016.get(), DoomConfig.SERVER.pinky_spawn_weight.get(),
					DoomConfig.SERVER.pinky_min_group.get(), DoomConfig.SERVER.pinky_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.archvile_biomes.get(), biome)) {
			base.add(
					new SpawnerData(ModEntityTypes.ARCHVILEETERNAL.get(), DoomConfig.SERVER.archvile_spawn_weight.get(),
							DoomConfig.SERVER.archvile_min_group.get(), DoomConfig.SERVER.archvile_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.tentacle_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.TENTACLE.get(), DoomConfig.SERVER.tentacle_spawn_weight.get(),
					DoomConfig.SERVER.tentacle_min_group.get(), DoomConfig.SERVER.tentacle_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.summoner_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.SUMMONER.get(), DoomConfig.SERVER.summoner_spawn_weight.get(),
					DoomConfig.SERVER.summoner_min_group.get(), DoomConfig.SERVER.summoner_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.whiplash_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.WHIPLASH.get(), DoomConfig.SERVER.whiplash_spawn_weight.get(),
					DoomConfig.SERVER.whiplash_min_group.get(), DoomConfig.SERVER.whiplash_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.BARON2016.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
					DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.FIREBARON.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
					DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.armoredbaron_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.ARMORBARON.get(), DoomConfig.SERVER.armoredbaron_spawn_weight.get(),
					DoomConfig.SERVER.armoredbaron_min_group.get(), DoomConfig.SERVER.armoredbaron_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.spider_mastermind_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.SPIDERMASTERMIND2016.get(),
					DoomConfig.SERVER.spider_mastermind_spawn_weight.get(),
					DoomConfig.SERVER.spider_mastermind_min_group.get(),
					DoomConfig.SERVER.spider_mastermind_max_group.get()));
		}
		if (parseBiomes(DoomConfig.SERVER.tyrant_biomes.get(), biome)) {
			base.add(new SpawnerData(ModEntityTypes.TYRANT.get(), DoomConfig.SERVER.tyrant_spawn_weight.get(),
					DoomConfig.SERVER.tyrant_min_group.get(), DoomConfig.SERVER.tyrant_max_group.get()));
		}
	}

	private static boolean parseBiomes(List<String> biomes, Biome biome) {
		return biomes.contains(biome.getRegistryName().toString())
				|| biomes.contains("#" + biome.getBiomeCategory().getName());
	}
}