package mod.azure.doom.structures.templates;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ArchMaykrStructure extends StructureFeature<NoneFeatureConfiguration> {
	public ArchMaykrStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return ArchMaykrStructure.FeatureStart::new;
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList
			.of(new MobSpawnSettings.SpawnerData(ModEntityTypes.ARCHMAKER.get(), 100, 1, 1));

	@Override
	public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed,
			WorldgenRandom chunkRandom, ChunkPos pos, Biome biome, ChunkPos chunkPos,
			NoneFeatureConfiguration featureConfig, LevelHeightAccessor world) {
		BlockPos centerOfChunk = new BlockPos(pos.x, 0, pos.z);
		int landHeight = chunkGenerator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, world);
		NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), world);
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
		return !this.isNearby(chunkGenerator, seed, chunkRandom, chunkPos) ? topBlock.getFluidState().isEmpty() : false;
	}

	private boolean isNearby(ChunkGenerator generator, long worldSeed, WorldgenRandom random, ChunkPos pos) {
		StructureFeatureConfiguration structureConfig = generator.getSettings().getConfig(DoomStructures.MAYKR.get());
		if (structureConfig == null) {
			return false;
		} else {
			int i = pos.x;
			int j = pos.z;
			for (int k = i - 10; k <= i + 10; ++k) {
				for (int l = j - 10; l <= j + 10; ++l) {
					ChunkPos chunkPos = DoomStructures.MAYKR.get().getPotentialFeatureChunk(structureConfig, worldSeed,
							random, k, l);
					if (k == chunkPos.x && l == chunkPos.z) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static class FeatureStart extends StructureStart<NoneFeatureConfiguration> {
		public FeatureStart(StructureFeature<NoneFeatureConfiguration> structureIn, ChunkPos pos, int referenceIn,
				long seedIn) {
			super(structureIn, pos, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator,
				StructureManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config,
				LevelHeightAccessor p_163621_) {
			int x = (pos.x << 4) + 7;
			int z = (pos.z << 4) + 7;
			BlockPos blockpos = new BlockPos(x, 0, z);
			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "archmaykr/start_pool")), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random,
					false, true);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().yo -= 1);
			this.createBoundingBox();
		}
	}
}