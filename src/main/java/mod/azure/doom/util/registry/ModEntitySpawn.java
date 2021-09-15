package mod.azure.doom.util.registry;

import java.util.List;

import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.config.DoomConfig.Server;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModEntitySpawn {

	public static void onBiomesLoad(BiomeLoadingEvent event) {
		List<Spawners> base = event.getSpawns().getSpawner(EntityClassification.MONSTER);
		Server config = DoomConfig.SERVER;
		if (parseBiomes(config.imp_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.IMP.get(), config.imp_spawn_weight.get(), config.imp_min_group.get(),
					config.imp_max_group.get()));
		}
		if (parseBiomes(config.impstone_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.IMP_STONE.get(), config.impstone_spawn_weight.get(),
					config.impstone_min_group.get(), config.impstone_max_group.get()));
		}
		if (parseBiomes(config.pinky_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.PINKY.get(), config.pinky_spawn_weight.get(),
					config.pinky_min_group.get(), config.pinky_max_group.get()));
		}
		if (parseBiomes(config.spectre_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.SPECTRE.get(), config.spectre_spawn_weight.get(),
					config.spectre_min_group.get(), config.spectre_max_group.get()));
		}
		if (parseBiomes(config.lost_soul_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.LOST_SOUL.get(), config.lost_soul_spawn_weight.get(),
					config.lost_soul_min_group.get(), config.lost_soul_max_group.get()));
		}
		if (parseBiomes(config.cacodemon_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.CACODEMON.get(), config.cacodemon_spawn_weight.get(),
					config.cacodemon_min_group.get(), config.cacodemon_max_group.get()));
		}
		if (parseBiomes(config.archvile_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ARCHVILE.get(), config.archvile_spawn_weight.get(),
					config.archvile_min_group.get(), config.archvile_max_group.get()));
		}
		if (parseBiomes(config.baron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.BARON.get(), config.baron_spawn_weight.get(),
					config.baron_min_group.get(), config.baron_max_group.get()));
		}
		if (parseBiomes(config.mancubus_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.MANCUBUS.get(), config.mancubus_spawn_weight.get(),
					config.mancubus_min_group.get(), config.mancubus_max_group.get()));
		}
		if (parseBiomes(config.revenant_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.REVENANT.get(), config.revenant_spawn_weight.get(),
					config.revenant_min_group.get(), config.revenant_max_group.get()));
		}
		if (parseBiomes(config.revenant_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.REVENANT2016.get(), config.revenant_spawn_weight.get(),
					config.revenant_min_group.get(), config.revenant_max_group.get()));
		}
		if (parseBiomes(config.spider_mastermind_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.SPIDERMASTERMIND.get(), config.spider_mastermind_spawn_weight.get(),
					config.spider_mastermind_min_group.get(), config.spider_mastermind_max_group.get()));
		}
		if (parseBiomes(config.zombieman_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ZOMBIEMAN.get(), config.zombieman_spawn_weight.get(),
					config.zombieman_min_group.get(), config.zombieman_max_group.get()));
		}
		if (parseBiomes(config.arachnotron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ARACHNOTRON.get(), config.arachnotron_spawn_weight.get(),
					config.arachnotron_min_group.get(), config.arachnotron_max_group.get()));
		}
		if (parseBiomes(config.arachnotron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ARACHNOTRONETERNAL.get(), config.arachnotron_spawn_weight.get(),
					config.arachnotron_min_group.get(), config.arachnotron_max_group.get()));
		}
		if (parseBiomes(config.imp2016_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.IMP2016.get(), config.imp2016_spawn_weight.get(),
					config.imp2016_min_group.get(), config.imp2016_min_group.get()));
		}
		if (parseBiomes(config.gargoyle_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.GARGOYLE.get(), config.gargoyle_spawn_weight.get(),
					config.gargoyle_min_group.get(), config.gargoyle_max_group.get()));
		}
		if (parseBiomes(config.nightmare_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.NIGHTMARE_IMP.get(), config.nightmare_spawn_weight.get(),
					config.nightmare_min_group.get(), config.nightmare_max_group.get()));
		}
		if (parseBiomes(config.chaingunner_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.CHAINGUNNER.get(), config.chaingunner_spawn_weight.get(),
					config.chaingunner_min_group.get(), config.chaingunner_max_group.get()));
		}
		if (parseBiomes(config.shotgunguy_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.SHOTGUNGUY.get(), config.shotgunguy_spawn_weight.get(),
					config.shotgunguy_min_group.get(), config.shotgunguy_max_group.get()));
		}
		if (parseBiomes(config.marauder_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.MARAUDER.get(), config.marauder_spawn_weight.get(),
					config.marauder_min_group.get(), config.marauder_max_group.get()));
		}
		if (parseBiomes(config.pain_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.PAIN.get(), config.pain_spawn_weight.get(),
					config.pain_min_group.get(), config.pain_max_group.get()));
		}
		if (parseBiomes(config.hellknight_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.HELLKNIGHT.get(), config.hellknight_spawn_weight.get(),
					config.hellknight_min_group.get(), config.hellknight_max_group.get()));
		}
		if (parseBiomes(config.hellknight2016_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.HELLKNIGHT2016.get(), config.hellknight2016_spawn_weight.get(),
					config.hellknight2016_min_group.get(), config.hellknight2016_max_group.get()));
		}
		if (parseBiomes(config.hellknight2016_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.DREADKNIGHT.get(), config.hellknight2016_spawn_weight.get(),
					config.hellknight2016_min_group.get(), config.hellknight2016_max_group.get()));
		}
		if (parseBiomes(config.cyberdemon_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.CYBERDEMON.get(), config.cyberdemon_spawn_weight.get(),
					config.cyberdemon_min_group.get(), config.cyberdemon_max_group.get()));
		}
		if (parseBiomes(config.unwilling_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.UNWILLING.get(), config.unwilling_spawn_weight.get(),
					config.unwilling_min_group.get(), config.unwilling_max_group.get()));
		}
		if (parseBiomes(config.possessed_scientist_biomes.get(), event)) {
			base.add(
					new Spawners(ModEntityTypes.POSSESSEDSCIENTIST.get(), config.possessed_scientist_spawn_weight.get(),
							config.possessed_scientist_min_group.get(), config.possessed_scientist_max_group.get()));
		}
		if (parseBiomes(config.possessed_soldier_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.POSSESSEDSOLDIER.get(), config.possessed_soldier_spawn_weight.get(),
					config.possessed_soldier_min_group.get(), config.possessed_soldier_max_group.get()));
		}
		if (parseBiomes(config.mechazombie_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.MECHAZOMBIE.get(), config.mechazombie_spawn_weight.get(),
					config.mechazombie_min_group.get(), config.mechazombie_max_group.get()));
		}
		if (parseBiomes(config.cueball_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.CUEBALL.get(), config.cueball_spawn_weight.get(),
					config.cueball_min_group.get(), config.cueball_max_group.get()));
		}
		if (parseBiomes(config.prowler_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.PROWLER.get(), config.prowler_spawn_weight.get(),
					config.prowler_min_group.get(), config.prowler_max_group.get()));
		}
		if (parseBiomes(config.gorenest_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.GORE_NEST.get(), config.gorenest_spawn_weight.get(),
					config.gorenest_min_group.get(), config.gorenest_max_group.get()));
		}
		if (parseBiomes(config.cyberdemon2016_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.CYBERDEMON2016.get(), config.cyberdemon2016_spawn_weight.get(),
					config.cyberdemon2016_min_group.get(), config.cyberdemon2016_max_group.get()));
		}
		if (parseBiomes(config.possessed_worker_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.POSSESSEDWORKER.get(), config.possessed_worker_spawn_weight.get(),
					config.possessed_worker_min_group.get(), config.possessed_worker_max_group.get()));
		}
		if (parseBiomes(config.doomhunter_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.DOOMHUNTER.get(), config.doomhunter_spawn_weight.get(),
					config.doomhunter_min_group.get(), config.doomhunter_max_group.get()));
		}
		if (parseBiomes(config.pinky_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.PINKY2016.get(), config.pinky_spawn_weight.get(),
					config.pinky_min_group.get(), config.pinky_max_group.get()));
		}
		if (parseBiomes(config.archvile_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ARCHVILEETERNAL.get(), config.archvile_spawn_weight.get(),
					config.archvile_min_group.get(), config.archvile_max_group.get()));
		}
		if (parseBiomes(config.tentacle_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.TENTACLE.get(), config.tentacle_spawn_weight.get(),
					config.tentacle_min_group.get(), config.tentacle_max_group.get()));
		}
		if (parseBiomes(config.summoner_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.SUMMONER.get(), config.summoner_spawn_weight.get(),
					config.summoner_min_group.get(), config.summoner_max_group.get()));
		}
		if (parseBiomes(config.whiplash_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.WHIPLASH.get(), config.whiplash_spawn_weight.get(),
					config.whiplash_min_group.get(), config.whiplash_max_group.get()));
		}
		if (parseBiomes(config.baron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.BARON2016.get(), config.baron_spawn_weight.get(),
					config.baron_min_group.get(), config.baron_max_group.get()));
		}
		if (parseBiomes(config.baron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.FIREBARON.get(), config.baron_spawn_weight.get(),
					config.baron_min_group.get(), config.baron_max_group.get()));
		}
		if (parseBiomes(config.armoredbaron_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.ARMORBARON.get(), config.armoredbaron_spawn_weight.get(),
					config.armoredbaron_min_group.get(), config.armoredbaron_max_group.get()));
		}
		if (parseBiomes(config.spider_mastermind_biomes.get(), event)) {
			base.add(
					new Spawners(ModEntityTypes.SPIDERMASTERMIND2016.get(), config.spider_mastermind_spawn_weight.get(),
							config.spider_mastermind_min_group.get(), config.spider_mastermind_max_group.get()));
		}
		if (parseBiomes(config.tyrant_biomes.get(), event)) {
			base.add(new Spawners(ModEntityTypes.TYRANT.get(), config.tyrant_spawn_weight.get(),
					config.tyrant_min_group.get(), config.tyrant_max_group.get()));
		}
	}

	private static boolean parseBiomes(List<? extends String> list, BiomeLoadingEvent event) {
		RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
		return list.contains(biomeKey.getRegistryName().toString())
				|| list.contains("#" + event.getCategory().getName());
	}
}