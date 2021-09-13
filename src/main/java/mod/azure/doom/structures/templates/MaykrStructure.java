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
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class MaykrStructure extends StructureFeature<NoneFeatureConfiguration> {
	public MaykrStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return MaykrStructure.FeatureStart::new;
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList.of(
			new MobSpawnSettings.SpawnerData(ModEntityTypes.MAYKRDRONE.get(), 50, 2, 5),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.BLOODMAYKR.get(), 50, 1, 2));

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
		StructureFeatureConfiguration structureConfig = generator.getSettings()
				.getConfig(DoomStructures.ARCHMAYKR.get());
		if (structureConfig == null) {
			return false;
		} else {
			int i = pos.x;
			int j = pos.z;
			for (int k = i - 10; k <= i + 10; ++k) {
				for (int l = j - 10; l <= j + 10; ++l) {
					ChunkPos chunkPos = DoomStructures.ARCHMAYKR.get().getPotentialFeatureChunk(structureConfig,
							worldSeed, random, k, l);
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
			JigsawConfiguration structureSettingsAndStartPool = new JigsawConfiguration(
					() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "maykr/start_pool")),
					10);
			JigsawPlacement.addPieces(dynamicRegistryManager, structureSettingsAndStartPool,
					PoolElementStructurePiece::new, chunkGenerator, templateManagerIn, blockpos, this, this.random,
					false, false, p_163621_);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.createBoundingBox();
		}
	}
}