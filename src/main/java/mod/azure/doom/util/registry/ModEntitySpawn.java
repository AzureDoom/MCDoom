package mod.azure.doom.util.registry;

import java.util.HashMap;
import java.util.List;

import mod.azure.doom.util.config.BiomeConfig;
import mod.azure.doom.util.config.BiomeEvaluator;
import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.config.EntityConfig;
import mod.azure.doom.util.config.EntityDefaults.EntityConfigType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntitySpawn {

	public static void onBiomesLoad(BiomeLoadingEvent event) {
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		List<Spawners> base = event.getSpawns().getSpawner(EntityClassification.MONSTER);
		HashMap<EntityConfigType, EntityConfig> config = Config.SERVER.entityConfig;
        if (event.getName() != null ) {
            ResourceLocation name = event.getName();
            RegistryKey<Biome> biome1 = RegistryKey.create(Registry.BIOME_REGISTRY, name);
            if (biome1 == Biomes.SOUL_SAND_VALLEY) {
            	
            }
            if (biome1 == Biomes.NETHER_WASTES) {
            	
            }
            if (biome1 == Biomes.CRIMSON_FOREST) {
            	
            }
            if (biome1 == Biomes.WARPED_FOREST) {
            	
            }
            if (biome1 == Biomes.BASALT_DELTAS) {
            	
            }
        }
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.PROWLER, biome)
				&& config.get(EntityConfigType.PROWLER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.PROWLER.get(), config.get(EntityConfigType.PROWLER).SPAWN_WEIGHT,
					config.get(EntityConfigType.PROWLER).MIN_GROUP, config.get(EntityConfigType.PROWLER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.IMP, biome)
				&& config.get(EntityConfigType.IMP).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.IMP.get(), config.get(EntityConfigType.IMP).SPAWN_WEIGHT,
					config.get(EntityConfigType.IMP).MIN_GROUP, config.get(EntityConfigType.IMP).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.IMP_STONE, biome)
				&& config.get(EntityConfigType.IMP_STONE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.IMP_STONE.get(), config.get(EntityConfigType.IMP_STONE).SPAWN_WEIGHT,
					config.get(EntityConfigType.IMP_STONE).MIN_GROUP,
					config.get(EntityConfigType.IMP_STONE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.PINKY, biome)
				&& config.get(EntityConfigType.PINKY).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.PINKY.get(), config.get(EntityConfigType.PINKY).SPAWN_WEIGHT,
					config.get(EntityConfigType.PINKY).MIN_GROUP, config.get(EntityConfigType.PINKY).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SPECTRE, biome)
				&& config.get(EntityConfigType.SPECTRE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.SPECTRE.get(), config.get(EntityConfigType.SPECTRE).SPAWN_WEIGHT,
					config.get(EntityConfigType.SPECTRE).MIN_GROUP, config.get(EntityConfigType.SPECTRE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.LOST_SOUL, biome)
				&& config.get(EntityConfigType.LOST_SOUL).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.LOST_SOUL.get(), config.get(EntityConfigType.LOST_SOUL).SPAWN_WEIGHT,
					config.get(EntityConfigType.LOST_SOUL).MIN_GROUP,
					config.get(EntityConfigType.LOST_SOUL).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.CACODEMON, biome)
				&& config.get(EntityConfigType.CACODEMON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.CACODEMON.get(), config.get(EntityConfigType.CACODEMON).SPAWN_WEIGHT,
					config.get(EntityConfigType.CACODEMON).MIN_GROUP,
					config.get(EntityConfigType.CACODEMON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.ARCHVILE, biome)
				&& config.get(EntityConfigType.ARCHVILE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ARCHVILE.get(), config.get(EntityConfigType.ARCHVILE).SPAWN_WEIGHT,
					config.get(EntityConfigType.ARCHVILE).MIN_GROUP, config.get(EntityConfigType.ARCHVILE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.BARON, biome)
				&& config.get(EntityConfigType.BARON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.BARON.get(), config.get(EntityConfigType.BARON).SPAWN_WEIGHT,
					config.get(EntityConfigType.BARON).MIN_GROUP, config.get(EntityConfigType.BARON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.MANCUBUS, biome)
				&& config.get(EntityConfigType.MANCUBUS).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.MANCUBUS.get(), config.get(EntityConfigType.MANCUBUS).SPAWN_WEIGHT,
					config.get(EntityConfigType.MANCUBUS).MIN_GROUP, config.get(EntityConfigType.MANCUBUS).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.REVENANT, biome)
				&& config.get(EntityConfigType.REVENANT).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.REVENANT.get(), config.get(EntityConfigType.REVENANT).SPAWN_WEIGHT,
					config.get(EntityConfigType.REVENANT).MIN_GROUP, config.get(EntityConfigType.REVENANT).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.REVENANT, biome)
				&& config.get(EntityConfigType.REVENANT).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.REVENANT2016.get(), config.get(EntityConfigType.REVENANT).SPAWN_WEIGHT,
					config.get(EntityConfigType.REVENANT).MIN_GROUP, config.get(EntityConfigType.REVENANT).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SPIDERMASTERMIND, biome)
				&& config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.SPIDERMASTERMIND.get(),
					config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT,
					config.get(EntityConfigType.SPIDERMASTERMIND).MIN_GROUP,
					config.get(EntityConfigType.SPIDERMASTERMIND).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SPIDERMASTERMIND, biome)
				&& config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.SPIDERMASTERMIND2016.get(),
					config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT,
					config.get(EntityConfigType.SPIDERMASTERMIND).MIN_GROUP,
					config.get(EntityConfigType.SPIDERMASTERMIND).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.ZOMBIEMAN, biome)
				&& config.get(EntityConfigType.ZOMBIEMAN).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ZOMBIEMAN.get(), config.get(EntityConfigType.ZOMBIEMAN).SPAWN_WEIGHT,
					config.get(EntityConfigType.ZOMBIEMAN).MIN_GROUP,
					config.get(EntityConfigType.ZOMBIEMAN).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.NIGHTMARE_IMP, biome)
				&& config.get(EntityConfigType.NIGHTMARE_IMP).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.NIGHTMARE_IMP.get(),
					config.get(EntityConfigType.NIGHTMARE_IMP).SPAWN_WEIGHT,
					config.get(EntityConfigType.NIGHTMARE_IMP).MIN_GROUP,
					config.get(EntityConfigType.NIGHTMARE_IMP).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.GARGOYLE, biome)
				&& config.get(EntityConfigType.GARGOYLE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.GARGOYLE.get(), config.get(EntityConfigType.GARGOYLE).SPAWN_WEIGHT,
					config.get(EntityConfigType.GARGOYLE).MIN_GROUP, config.get(EntityConfigType.GARGOYLE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.IMP_2016, biome)
				&& config.get(EntityConfigType.IMP_2016).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.IMP2016.get(), config.get(EntityConfigType.IMP_2016).SPAWN_WEIGHT,
					config.get(EntityConfigType.IMP_2016).MIN_GROUP, config.get(EntityConfigType.IMP_2016).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.MECHA_ZOMBIE, biome)
				&& config.get(EntityConfigType.MECHA_ZOMBIE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.MECHAZOMBIE.get(),
					config.get(EntityConfigType.MECHA_ZOMBIE).SPAWN_WEIGHT,
					config.get(EntityConfigType.MECHA_ZOMBIE).MIN_GROUP,
					config.get(EntityConfigType.MECHA_ZOMBIE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.ARACHNOTRON, biome)
				&& config.get(EntityConfigType.ARACHNOTRON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ARACHNOTRON.get(),
					config.get(EntityConfigType.ARACHNOTRON).SPAWN_WEIGHT,
					config.get(EntityConfigType.ARACHNOTRON).MIN_GROUP,
					config.get(EntityConfigType.ARACHNOTRON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.ARACHNOTRON, biome)
				&& config.get(EntityConfigType.ARACHNOTRON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ARACHNOTRONETERNAL.get(),
					config.get(EntityConfigType.ARACHNOTRON).SPAWN_WEIGHT,
					config.get(EntityConfigType.ARACHNOTRON).MIN_GROUP,
					config.get(EntityConfigType.ARACHNOTRON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.CHAINGUNNER, biome)
				&& config.get(EntityConfigType.CHAINGUNNER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.CHAINGUNNER.get(),
					config.get(EntityConfigType.CHAINGUNNER).SPAWN_WEIGHT,
					config.get(EntityConfigType.CHAINGUNNER).MIN_GROUP,
					config.get(EntityConfigType.CHAINGUNNER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SHOTGUN_GUY, biome)
				&& config.get(EntityConfigType.SHOTGUN_GUY).SPAWN_WEIGHT > 0) {
			base.add(
					new Spawners(ModEntityTypes.SHOTGUNGUY.get(), config.get(EntityConfigType.SHOTGUN_GUY).SPAWN_WEIGHT,
							config.get(EntityConfigType.SHOTGUN_GUY).MIN_GROUP,
							config.get(EntityConfigType.SHOTGUN_GUY).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.MARAUDER, biome)
				&& config.get(EntityConfigType.MARAUDER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.MARAUDER.get(), config.get(EntityConfigType.MARAUDER).SPAWN_WEIGHT,
					config.get(EntityConfigType.MARAUDER).MIN_GROUP, config.get(EntityConfigType.MARAUDER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.PAIN, biome)
				&& config.get(EntityConfigType.PAIN).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.PAIN.get(), config.get(EntityConfigType.PAIN).SPAWN_WEIGHT,
					config.get(EntityConfigType.PAIN).MIN_GROUP, config.get(EntityConfigType.PAIN).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.HELL_KNIGHT, biome)
				&& config.get(EntityConfigType.HELL_KNIGHT).SPAWN_WEIGHT > 0) {
			base.add(
					new Spawners(ModEntityTypes.HELLKNIGHT.get(), config.get(EntityConfigType.HELL_KNIGHT).SPAWN_WEIGHT,
							config.get(EntityConfigType.HELL_KNIGHT).MIN_GROUP,
							config.get(EntityConfigType.HELL_KNIGHT).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.HELL_KNIGHT_2016, biome)
				&& config.get(EntityConfigType.HELL_KNIGHT_2016).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.HELLKNIGHT2016.get(),
					config.get(EntityConfigType.HELL_KNIGHT_2016).SPAWN_WEIGHT,
					config.get(EntityConfigType.HELL_KNIGHT_2016).MIN_GROUP,
					config.get(EntityConfigType.HELL_KNIGHT_2016).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.CYBER_DEMON, biome)
				&& config.get(EntityConfigType.CYBER_DEMON).SPAWN_WEIGHT > 0) {
			base.add(
					new Spawners(ModEntityTypes.CYBERDEMON.get(), config.get(EntityConfigType.CYBER_DEMON).SPAWN_WEIGHT,
							config.get(EntityConfigType.CYBER_DEMON).MIN_GROUP,
							config.get(EntityConfigType.CYBER_DEMON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.UNWILLING, biome)
				&& config.get(EntityConfigType.UNWILLING).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.UNWILLING.get(), config.get(EntityConfigType.UNWILLING).SPAWN_WEIGHT,
					config.get(EntityConfigType.UNWILLING).MIN_GROUP,
					config.get(EntityConfigType.UNWILLING).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.CUEBALL, biome)
				&& config.get(EntityConfigType.CUEBALL).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.CUEBALL.get(), config.get(EntityConfigType.CUEBALL).SPAWN_WEIGHT,
					config.get(EntityConfigType.CUEBALL).MIN_GROUP, config.get(EntityConfigType.CUEBALL).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.POSSESSED_SCIENTIST, biome)
				&& config.get(EntityConfigType.POSSESSED_SCIENTIST).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.POSSESSEDSCIENTIST.get(),
					config.get(EntityConfigType.POSSESSED_SCIENTIST).SPAWN_WEIGHT,
					config.get(EntityConfigType.POSSESSED_SCIENTIST).MIN_GROUP,
					config.get(EntityConfigType.POSSESSED_SCIENTIST).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.POSSESSED_SOLDIER, biome)
				&& config.get(EntityConfigType.POSSESSED_SOLDIER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.POSSESSEDSOLDIER.get(),
					config.get(EntityConfigType.POSSESSED_SOLDIER).SPAWN_WEIGHT,
					config.get(EntityConfigType.POSSESSED_SOLDIER).MIN_GROUP,
					config.get(EntityConfigType.POSSESSED_SOLDIER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.GORE_NEST, biome)
				&& config.get(EntityConfigType.GORE_NEST).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.GORE_NEST.get(), config.get(EntityConfigType.GORE_NEST).SPAWN_WEIGHT,
					config.get(EntityConfigType.GORE_NEST).MIN_GROUP,
					config.get(EntityConfigType.GORE_NEST).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.CYBER_DEMON_2016, biome)
				&& config.get(EntityConfigType.CYBER_DEMON_2016).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.CYBERDEMON2016.get(),
					config.get(EntityConfigType.CYBER_DEMON_2016).SPAWN_WEIGHT,
					config.get(EntityConfigType.CYBER_DEMON_2016).MIN_GROUP,
					config.get(EntityConfigType.CYBER_DEMON_2016).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.TYRANT, biome)
				&& config.get(EntityConfigType.TYRANT).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.TYRANT.get(), config.get(EntityConfigType.TYRANT).SPAWN_WEIGHT,
					config.get(EntityConfigType.TYRANT).MIN_GROUP, config.get(EntityConfigType.TYRANT).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.POSSESSEDWORKER, biome)
				&& config.get(EntityConfigType.POSSESSEDWORKER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.POSSESSEDWORKER.get(),
					config.get(EntityConfigType.POSSESSEDWORKER).SPAWN_WEIGHT,
					config.get(EntityConfigType.POSSESSEDWORKER).MIN_GROUP,
					config.get(EntityConfigType.POSSESSEDWORKER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SPIDERMASTERMIND, biome)
				&& config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.SPIDERMASTERMIND2016.get(),
					config.get(EntityConfigType.SPIDERMASTERMIND).SPAWN_WEIGHT,
					config.get(EntityConfigType.SPIDERMASTERMIND).MIN_GROUP,
					config.get(EntityConfigType.SPIDERMASTERMIND).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.DOOMHUNTER, biome)
				&& config.get(EntityConfigType.DOOMHUNTER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.DOOMHUNTER.get(), config.get(EntityConfigType.DOOMHUNTER).SPAWN_WEIGHT,
					config.get(EntityConfigType.DOOMHUNTER).MIN_GROUP,
					config.get(EntityConfigType.DOOMHUNTER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.ARCHVILE, biome)
				&& config.get(EntityConfigType.ARCHVILE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ARCHVILEETERNAL.get(),
					config.get(EntityConfigType.ARCHVILE).SPAWN_WEIGHT, config.get(EntityConfigType.ARCHVILE).MIN_GROUP,
					config.get(EntityConfigType.ARCHVILE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.PINKY, biome)
				&& config.get(EntityConfigType.PINKY).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.PINKY2016.get(), config.get(EntityConfigType.PINKY).SPAWN_WEIGHT,
					config.get(EntityConfigType.PINKY).MIN_GROUP, config.get(EntityConfigType.PINKY).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.TENTACLE, biome)
				&& config.get(EntityConfigType.TENTACLE).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.TENTACLE.get(), config.get(EntityConfigType.TENTACLE).SPAWN_WEIGHT,
					config.get(EntityConfigType.TENTACLE).MIN_GROUP, config.get(EntityConfigType.TENTACLE).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.WHIPLASH, biome)
				&& config.get(EntityConfigType.WHIPLASH).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.WHIPLASH.get(), config.get(EntityConfigType.WHIPLASH).SPAWN_WEIGHT,
					config.get(EntityConfigType.WHIPLASH).MIN_GROUP, config.get(EntityConfigType.WHIPLASH).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.SUMMONER, biome)
				&& config.get(EntityConfigType.SUMMONER).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.SUMMONER.get(), config.get(EntityConfigType.SUMMONER).SPAWN_WEIGHT,
					config.get(EntityConfigType.SUMMONER).MIN_GROUP, config.get(EntityConfigType.SUMMONER).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.BARON, biome)
				&& config.get(EntityConfigType.BARON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.BARON2016.get(), config.get(EntityConfigType.BARON).SPAWN_WEIGHT,
					config.get(EntityConfigType.BARON).MIN_GROUP, config.get(EntityConfigType.BARON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.BARON, biome)
				&& config.get(EntityConfigType.BARON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.FIREBARON.get(), config.get(EntityConfigType.BARON).SPAWN_WEIGHT,
					config.get(EntityConfigType.BARON).MIN_GROUP, config.get(EntityConfigType.BARON).MAX_GROUP));
		}
		if (BiomeEvaluator.parseListForBiomeCheck(BiomeConfig.BARON, biome)
				&& config.get(EntityConfigType.BARON).SPAWN_WEIGHT > 0) {
			base.add(new Spawners(ModEntityTypes.ARMORBARON.get(), config.get(EntityConfigType.BARON).SPAWN_WEIGHT,
					config.get(EntityConfigType.BARON).MIN_GROUP, config.get(EntityConfigType.BARON).MAX_GROUP));
		}
	}
}