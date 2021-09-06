package mod.azure.doom.structures.templates;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
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
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class PortalStructure extends StructureFeature<DefaultFeatureConfig> {
	public PortalStructure(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
		return PortalStructure.Start::new;
	}

	private static final List<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = ImmutableList.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.LOST_SOUL, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.TURRET, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ZOMBIEMAN, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.CHAINGUNNER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.POSSESSEDWORKER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ARACHNOTRONETERNAL, 20, 1, 2));

	@Override
	public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
		return STRUCTURE_MONSTERS;
	}

	private static final List<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = ImmutableList.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.LOST_SOUL, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.TURRET, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ZOMBIEMAN, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.CHAINGUNNER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.POSSESSEDWORKER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ARACHNOTRONETERNAL, 20, 1, 2));

	@Override
	public List<SpawnSettings.SpawnEntry> getCreatureSpawns() {
		return STRUCTURE_CREATURES;
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
		return topBlock.getFluidState().isEmpty();
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
			BlockPos.Mutable blockpos = new BlockPos.Mutable(x, chunkGenerator.getSeaLevel(), z);
			StructurePoolBasedGenerator.method_30419(dynamicRegistryManager,
					new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN)
							.get(new Identifier(DoomMod.MODID, "portal/start_pool")), 10),
					PoolStructurePiece::new, chunkGenerator, structureManager, blockpos, this.children, this.random,
					false, false);
			this.children.forEach(piece -> piece.translate(0, 1, 0));
			this.children.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.setBoundingBoxFromChildren();
		}

	}
}