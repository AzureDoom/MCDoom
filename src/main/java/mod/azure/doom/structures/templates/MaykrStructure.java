package mod.azure.doom.structures.templates;

import java.util.function.Predicate;

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
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.ChunkRandom;

public class MaykrStructure extends StructureFeature<DefaultFeatureConfig> {

	public MaykrStructure(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
		return MaykrStructure.Start::new;
	}

	private static final Pool<SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.MAYKRDRONE, 50, 2, 5),
			new SpawnSettings.SpawnEntry(ModEntityTypes.BLOODMAYKR, 50, 1, 2));

	@Override
	public Pool<SpawnEntry> getMonsterSpawns() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed,
			ChunkRandom random, ChunkPos pos, ChunkPos chunkPos, DefaultFeatureConfig config, HeightLimitView world) {
		BlockPos centerOfChunk = new BlockPos(pos.x, 0, pos.z);
		int landHeight = chunkGenerator.getHeightInGround(centerOfChunk.getX(), centerOfChunk.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG, world);
		VerticalBlockSample columnOfBlocks = chunkGenerator.getColumnSample(centerOfChunk.getX(), centerOfChunk.getZ(),
				world);
		BlockState topBlock = columnOfBlocks.getState(centerOfChunk.up(landHeight).getY());
		return !this.isNearby(chunkGenerator, worldSeed, random, chunkPos) ? topBlock.getFluidState().isEmpty() : false;
	}

	private boolean isNearby(ChunkGenerator generator, long worldSeed, ChunkRandom random, ChunkPos pos) {
		StructureConfig structureConfig = generator.getStructuresConfig().getForType(DoomStructures.ARCHMAYKR);
		if (structureConfig == null) {
			return false;
		} else {
			int i = pos.x;
			int j = pos.z;
			for (int k = i - 10; k <= i + 10; ++k) {
				for (int l = j - 10; l <= j + 10; ++l) {
					ChunkPos chunkPos = DoomStructures.ARCHMAYKR.getStartChunk(structureConfig, worldSeed, random, k,
							l);
					if (k == chunkPos.x && l == chunkPos.z) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
		public Start(StructureFeature<DefaultFeatureConfig> structureFeature, ChunkPos chunkPos, int i, long l) {
			super(structureFeature, chunkPos, i, l);
		}

		@Override
		public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator,
				StructureManager manager, ChunkPos pos, DefaultFeatureConfig featureConfig, HeightLimitView world,
				Predicate<Biome> predicate) {
			int x = (pos.x << 4) + 7;
			int z = (pos.z << 4) + 7;
			BlockPos.Mutable blockpos = new BlockPos.Mutable(x, 0, z);
			StructurePoolFeatureConfig structureSettingsAndStartPool = new StructurePoolFeatureConfig(
					() -> registryManager.get(Registry.STRUCTURE_POOL_KEY)
							.get(new Identifier(DoomMod.MODID, "archmaykr/start_pool")),
					10);
			StructurePoolBasedGenerator.generate(registryManager, structureSettingsAndStartPool,
					PoolStructurePiece::new, chunkGenerator, manager, blockpos, this, this.random, false, true, world,
					predicate);
			this.children.forEach(piece -> piece.translate(0, 0, 0));
			this.children.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.setBoundingBoxFromChildren();
		}

	}
}