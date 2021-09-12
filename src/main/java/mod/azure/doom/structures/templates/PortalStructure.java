package mod.azure.doom.structures.templates;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class PortalStructure extends StructureFeature<NoneFeatureConfiguration> {
	public PortalStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return PortalStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
			new MobSpawnInfo.Spawners(ModEntityTypes.LOST_SOUL.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.TURRET.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.ZOMBIEMAN.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.CHAINGUNNER.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.POSSESSEDWORKER.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.ARACHNOTRONETERNAL.get(), 20, 1, 2));

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
		return STRUCTURE_MONSTERS;
	}

	private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
			new MobSpawnInfo.Spawners(ModEntityTypes.LOST_SOUL.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.ZOMBIEMAN.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.CHAINGUNNER.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.POSSESSEDWORKER.get(), 20, 1, 2),
			new MobSpawnInfo.Spawners(ModEntityTypes.ARACHNOTRONETERNAL.get(), 20, 1, 2));

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
		return STRUCTURE_CREATURES;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed,
			SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			NoFeatureConfig featureConfig) {
		BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
		int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG);
		IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
		return topBlock.getFluidState().isEmpty();
	}

	public static class Start extends StructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ,
				MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator,
				TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			BlockPos blockpos = new BlockPos(x, chunkGenerator.getSeaLevel(), z);
			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "portal/start_pool")), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random,
					false, false);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);
			this.calculateBoundingBox();
		}
	}
}