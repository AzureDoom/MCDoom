package mod.azure.doom.util.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.azure.doom.DoomMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record ModEntitySpawn(HolderSet<Biome> biomes, SpawnerData spawn) implements BiomeModifier {

	private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject
			.create(DoomMod.ADD_SPAWNS_TO_BIOMES, ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DoomMod.MODID);

//		if (parseBiomes(DoomConfig.SERVER.imp_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.IMP.get(), DoomConfig.SERVER.imp_spawn_weight.get();
//							DoomConfig.SERVER.imp_min_group.get(), DoomConfig.SERVER.imp_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.impstone_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.IMP_STONE.get(), DoomConfig.SERVER.impstone_spawn_weight.get(),
//							DoomConfig.SERVER.impstone_min_group.get(), DoomConfig.SERVER.impstone_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.pinky_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.PINKY.get(), DoomConfig.SERVER.pinky_spawn_weight.get(),
//							DoomConfig.SERVER.pinky_min_group.get(), DoomConfig.SERVER.pinky_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.spectre_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.SPECTRE.get(), DoomConfig.SERVER.spectre_spawn_weight.get(),
//							DoomConfig.SERVER.spectre_min_group.get(), DoomConfig.SERVER.spectre_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.lost_soul_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.LOST_SOUL.get(),
//							DoomConfig.SERVER.lost_soul_spawn_weight.get(), DoomConfig.SERVER.lost_soul_min_group.get(),
//							DoomConfig.SERVER.lost_soul_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.lost_soul_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.LOST_SOUL_ETERNAL.get(),
//							DoomConfig.SERVER.lost_soul_spawn_weight.get(), DoomConfig.SERVER.lost_soul_min_group.get(),
//							DoomConfig.SERVER.lost_soul_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.cacodemon_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.CACODEMON.get(),
//							DoomConfig.SERVER.cacodemon_spawn_weight.get(), DoomConfig.SERVER.cacodemon_min_group.get(),
//							DoomConfig.SERVER.cacodemon_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.archvile_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.ARCHVILE.get(), DoomConfig.SERVER.archvile_spawn_weight.get(),
//							DoomConfig.SERVER.archvile_min_group.get(), DoomConfig.SERVER.archvile_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.BARON.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
//							DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.mancubus_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.MANCUBUS.get(), DoomConfig.SERVER.mancubus_spawn_weight.get(),
//							DoomConfig.SERVER.mancubus_min_group.get(), DoomConfig.SERVER.mancubus_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.revenant_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.REVENANT.get(), DoomConfig.SERVER.revenant_spawn_weight.get(),
//							DoomConfig.SERVER.revenant_min_group.get(), DoomConfig.SERVER.revenant_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.revenant_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.REVENANT2016.get(),
//							DoomConfig.SERVER.revenant_spawn_weight.get(), DoomConfig.SERVER.revenant_min_group.get(),
//							DoomConfig.SERVER.revenant_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.spider_mastermind_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.SPIDERMASTERMIND.get(),
//							DoomConfig.SERVER.spider_mastermind_spawn_weight.get(),
//							DoomConfig.SERVER.spider_mastermind_min_group.get(),
//							DoomConfig.SERVER.spider_mastermind_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.zombieman_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.ZOMBIEMAN.get(),
//							DoomConfig.SERVER.zombieman_spawn_weight.get(), DoomConfig.SERVER.zombieman_min_group.get(),
//							DoomConfig.SERVER.zombieman_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.arachnotron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.ARACHNOTRON.get(), DoomConfig.SERVER.arachnotron_spawn_weight.get(),
//							DoomConfig.SERVER.arachnotron_min_group.get(),
//							DoomConfig.SERVER.arachnotron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.arachnotron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, new SpawnerData(
//					DoomEntities.ARACHNOTRONETERNAL.get(), DoomConfig.SERVER.arachnotron_spawn_weight.get(),
//					DoomConfig.SERVER.arachnotron_min_group.get(), DoomConfig.SERVER.arachnotron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.gargoyle_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.GARGOYLE.get(), DoomConfig.SERVER.gargoyle_spawn_weight.get(),
//							DoomConfig.SERVER.gargoyle_min_group.get(), DoomConfig.SERVER.gargoyle_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.chaingunner_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.CHAINGUNNER.get(), DoomConfig.SERVER.chaingunner_spawn_weight.get(),
//							DoomConfig.SERVER.chaingunner_min_group.get(),
//							DoomConfig.SERVER.chaingunner_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.shotgunguy_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.SHOTGUNGUY.get(), DoomConfig.SERVER.shotgunguy_spawn_weight.get(),
//							DoomConfig.SERVER.shotgunguy_min_group.get(),
//							DoomConfig.SERVER.shotgunguy_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.marauder_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.MARAUDER.get(), DoomConfig.SERVER.marauder_spawn_weight.get(),
//							DoomConfig.SERVER.marauder_min_group.get(), DoomConfig.SERVER.marauder_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.pain_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.PAIN.get(), DoomConfig.SERVER.pain_spawn_weight.get(),
//							DoomConfig.SERVER.pain_min_group.get(), DoomConfig.SERVER.pain_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.hellknight_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.HELLKNIGHT.get(), DoomConfig.SERVER.hellknight_spawn_weight.get(),
//							DoomConfig.SERVER.hellknight_min_group.get(),
//							DoomConfig.SERVER.hellknight_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.hellknight2016_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.HELLKNIGHT2016.get(),
//							DoomConfig.SERVER.hellknight2016_spawn_weight.get(),
//							DoomConfig.SERVER.hellknight2016_min_group.get(),
//							DoomConfig.SERVER.hellknight2016_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.hellknight2016_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.DREADKNIGHT.get(), DoomConfig.SERVER.hellknight2016_spawn_weight.get(),
//							DoomConfig.SERVER.hellknight2016_min_group.get(),
//							DoomConfig.SERVER.hellknight2016_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.cyberdemon_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.CYBERDEMON.get(), DoomConfig.SERVER.cyberdemon_spawn_weight.get(),
//							DoomConfig.SERVER.cyberdemon_min_group.get(),
//							DoomConfig.SERVER.cyberdemon_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.unwilling_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.UNWILLING.get(),
//							DoomConfig.SERVER.unwilling_spawn_weight.get(), DoomConfig.SERVER.unwilling_min_group.get(),
//							DoomConfig.SERVER.unwilling_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.possessed_scientist_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.POSSESSEDSCIENTIST.get(),
//							DoomConfig.SERVER.possessed_scientist_spawn_weight.get(),
//							DoomConfig.SERVER.possessed_scientist_min_group.get(),
//							DoomConfig.SERVER.possessed_scientist_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.possessed_soldier_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.POSSESSEDSOLDIER.get(),
//							DoomConfig.SERVER.possessed_soldier_spawn_weight.get(),
//							DoomConfig.SERVER.possessed_soldier_min_group.get(),
//							DoomConfig.SERVER.possessed_soldier_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.mechazombie_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.MECHAZOMBIE.get(), DoomConfig.SERVER.mechazombie_spawn_weight.get(),
//							DoomConfig.SERVER.mechazombie_min_group.get(),
//							DoomConfig.SERVER.mechazombie_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.cueball_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.CUEBALL.get(), DoomConfig.SERVER.cueball_spawn_weight.get(),
//							DoomConfig.SERVER.cueball_min_group.get(), DoomConfig.SERVER.cueball_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.prowler_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.PROWLER.get(), DoomConfig.SERVER.prowler_spawn_weight.get(),
//							DoomConfig.SERVER.prowler_min_group.get(), DoomConfig.SERVER.prowler_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.gorenest_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.GORE_NEST.get(), DoomConfig.SERVER.gorenest_spawn_weight.get(),
//							DoomConfig.SERVER.gorenest_min_group.get(), DoomConfig.SERVER.gorenest_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.possessed_worker_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.POSSESSEDWORKER.get(),
//							DoomConfig.SERVER.possessed_worker_spawn_weight.get(),
//							DoomConfig.SERVER.possessed_worker_min_group.get(),
//							DoomConfig.SERVER.possessed_worker_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.doomhunter_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.DOOMHUNTER.get(), DoomConfig.SERVER.doomhunter_spawn_weight.get(),
//							DoomConfig.SERVER.doomhunter_min_group.get(),
//							DoomConfig.SERVER.doomhunter_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.tentacle_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.TENTACLE.get(), DoomConfig.SERVER.tentacle_spawn_weight.get(),
//							DoomConfig.SERVER.tentacle_min_group.get(), DoomConfig.SERVER.tentacle_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.summoner_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.SUMMONER.get(), DoomConfig.SERVER.summoner_spawn_weight.get(),
//							DoomConfig.SERVER.summoner_min_group.get(), DoomConfig.SERVER.summoner_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.whiplash_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.WHIPLASH.get(), DoomConfig.SERVER.whiplash_spawn_weight.get(),
//							DoomConfig.SERVER.whiplash_min_group.get(), DoomConfig.SERVER.whiplash_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.BARON2016.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
//							DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.baron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.FIREBARON.get(), DoomConfig.SERVER.baron_spawn_weight.get(),
//							DoomConfig.SERVER.baron_min_group.get(), DoomConfig.SERVER.baron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.armoredbaron_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.ARMORBARON.get(), DoomConfig.SERVER.armoredbaron_spawn_weight.get(),
//							DoomConfig.SERVER.armoredbaron_min_group.get(),
//							DoomConfig.SERVER.armoredbaron_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.spider_mastermind_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.SPIDERMASTERMIND2016.get(),
//							DoomConfig.SERVER.spider_mastermind_spawn_weight.get(),
//							DoomConfig.SERVER.spider_mastermind_min_group.get(),
//							DoomConfig.SERVER.spider_mastermind_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.motherdemon_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.MOTHERDEMON.get(), DoomConfig.SERVER.motherdemon_spawn_weight.get(),
//							DoomConfig.SERVER.motherdemon_min_group.get(),
//							DoomConfig.SERVER.motherdemon_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.maykrdrone_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.MAYKRDRONE.get(), DoomConfig.SERVER.maykrdrone_spawn_weight.get(),
//							DoomConfig.SERVER.maykrdrone_min_group.get(),
//							DoomConfig.SERVER.maykrdrone_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.bloodmaykr_biomes.get(), biome)) {
//			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER,
//					new SpawnerData(DoomEntities.BLOODMAYKR.get(), DoomConfig.SERVER.bloodmaykr_spawn_weight.get(),
//							DoomConfig.SERVER.bloodmaykr_min_group.get(),
//							DoomConfig.SERVER.bloodmaykr_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.archmaykr_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.ARCHMAKER.get(),
//							DoomConfig.SERVER.archmaykr_spawn_weight.get(), DoomConfig.SERVER.archmaykr_min_group.get(),
//							DoomConfig.SERVER.archmaykr_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.gladiator_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.GLADIATOR.get(),
//							DoomConfig.SERVER.gladiator_spawn_weight.get(), DoomConfig.SERVER.gladiator_min_group.get(),
//							DoomConfig.SERVER.gladiator_max_group.get()));
//		}
//		if (parseBiomes(DoomConfig.SERVER.gladiator_biomes.get(), biome)) {
//			builder.getMobSpawnSettings()
//					.addSpawn(MobCategory.MONSTER, new SpawnerData(DoomEntities.GLADIATOR.get(),
//							DoomConfig.SERVER.gladiator_spawn_weight.get(), DoomConfig.SERVER.gladiator_min_group.get(),
//							DoomConfig.SERVER.gladiator_max_group.get()));
//		}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return SERIALIZER.get();
	}

	public static Codec<ModEntitySpawn> makeCodec() {
		return RecordCodecBuilder.create(builder -> builder
				.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntitySpawn::biomes),
						SpawnerData.CODEC.fieldOf("spawn").forGetter(ModEntitySpawn::spawn))
				.apply(builder, ModEntitySpawn::new));
	}
}