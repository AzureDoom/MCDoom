package mod.azure.doom.structures.templates;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
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

public class ArchMaykrStructure extends StructureFeature<DefaultFeatureConfig> {
	public ArchMaykrStructure(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
		return ArchMaykrStructure.Start::new;
	}

	private static final List<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = ImmutableList
			.of(new SpawnSettings.SpawnEntry(ModEntityTypes.ARCHMAKER, 100, 1, 1));

	@Override
	public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed,
			ChunkRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			DefaultFeatureConfig featureConfig) {
		return !this.isNear(chunkGenerator, seed, chunkRandom, chunkX, chunkZ)
				? getGenerationHeight(chunkX, chunkZ, chunkGenerator) >= 60
				: false;
	}

	private boolean isNear(ChunkGenerator chunkGenerator, long l, ChunkRandom chunkRandom, int i, int j) {
		StructureConfig structureConfig = chunkGenerator.getStructuresConfig().getForType(DoomStructures.MAYKR);
		if (structureConfig == null) {
			return false;
		} else {
			for (int k = i - 10; k <= i + 10; ++k) {
				for (int m = j - 10; m <= j + 10; ++m) {
					ChunkPos chunkPos = DoomStructures.MAYKR.getStartChunk(structureConfig, l, chunkRandom, k, m);
					if (k == chunkPos.x && m == chunkPos.z) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
		Random random = new Random((long) (chunkX + chunkZ * 10387313));
		BlockRotation blockRotation = BlockRotation.random(random);
		int i = 5;
		int j = 5;
		if (blockRotation == BlockRotation.CLOCKWISE_90) {
			i = -5;
		} else if (blockRotation == BlockRotation.CLOCKWISE_180) {
			i = -5;
			j = -5;
		} else if (blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
			j = -5;
		}

		int k = (chunkX << 4) + 7;
		int l = (chunkZ << 4) + 7;
		int m = chunkGenerator.getHeightInGround(k, l, Heightmap.Type.WORLD_SURFACE_WG);
		int n = chunkGenerator.getHeightInGround(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int o = chunkGenerator.getHeightInGround(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
		int p = chunkGenerator.getHeightInGround(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		return Math.min(Math.min(m, n), Math.min(o, p));
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

			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			BlockPos.Mutable blockpos = new BlockPos.Mutable(x, 0, z);
			StructurePoolBasedGenerator.method_30419(dynamicRegistryManager,
					new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN)
							.get(new Identifier(DoomMod.MODID, "archmaykr/start_pool")), 10),
					PoolStructurePiece::new, chunkGenerator, structureManager, blockpos, this.children, this.random,
					false, true);
			this.children.forEach(piece -> piece.translate(0, 0, 0));
			this.children.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.setBoundingBoxFromChildren();
		}

	}
}