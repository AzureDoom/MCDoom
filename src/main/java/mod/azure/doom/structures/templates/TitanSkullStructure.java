package mod.azure.doom.structures.templates;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class TitanSkullStructure extends StructureFeature<DefaultFeatureConfig> {
	public TitanSkullStructure(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
		return TitanSkullStructure.Start::new;
	}

	private static final List<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = ImmutableList.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.LOST_SOUL, 100, 4, 9),
			new SpawnSettings.SpawnEntry(ModEntityTypes.TURRET, 20, 1, 2));

	@Override
	public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed,
			ChunkRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			DefaultFeatureConfig featureConfig) {
		BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
		int landHeight = chunkGenerator.getHeightInGround(centerOfChunk.getX(), centerOfChunk.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG);
		BlockView columnOfBlocks = chunkGenerator.getColumnSample(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.up(landHeight));
		return !this.isNear(chunkGenerator, seed, chunkRandom, chunkX, chunkZ) ? topBlock.getFluidState().isEmpty()
				: false;
	}

	private boolean isNear(ChunkGenerator chunkGenerator, long l, ChunkRandom chunkRandom, int i, int j) {
		StructureConfig structureConfig = chunkGenerator.getStructuresConfig().getForType(DoomStructures.MOTHERDEMON);
		if (structureConfig == null) {
			return false;
		} else {
			for (int k = i - 10; k <= i + 10; ++k) {
				for (int m = j - 10; m <= j + 10; ++m) {
					ChunkPos chunkPos = DoomStructures.MOTHERDEMON.getStartChunk(structureConfig, l, chunkRandom, k, m);
					if (k == chunkPos.x && m == chunkPos.z) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
		public Start(StructureFeature<DefaultFeatureConfig> structureIn, int chunkX, int chunkZ, BlockBox blockBox,
				int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, blockBox, referenceIn, seedIn);
		}

		@Override
		public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator,
				StructureManager structureManager, int chunkX, int chunkZ, Biome biome,
				DefaultFeatureConfig defaultFeatureConfig) {
			ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
			BlockPos.Mutable blockpos = new BlockPos.Mutable(chunkPos.getStartX() + this.random.nextInt(16), 33,
					chunkPos.getStartZ() + this.random.nextInt(16));
			StructurePoolBasedGenerator.method_30419(dynamicRegistryManager,
					new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN)
							.get(new Identifier(DoomMod.MODID, "titan_skull/start_pool")), 10),
					PoolStructurePiece::new, chunkGenerator, structureManager, blockpos, this.children, this.random,
					false, false);
			this.children.forEach(piece -> piece.translate(0, 1, 0));
			this.children.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.setBoundingBoxFromChildren();
		}

	}
}