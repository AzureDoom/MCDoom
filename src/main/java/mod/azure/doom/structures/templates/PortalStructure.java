package mod.azure.doom.structures.templates;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
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
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class PortalStructure extends StructureFeature<NoneFeatureConfiguration> {
	public PortalStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return PortalStructure.FeatureStart::new;
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList.of(
			new MobSpawnSettings.SpawnerData(ModEntityTypes.LOST_SOUL.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.TURRET.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.ZOMBIEMAN.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.CHAINGUNNER.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.POSSESSEDWORKER.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.ARACHNOTRONETERNAL.get(), 20, 1, 2));

	@Override
	public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList() {
		return STRUCTURE_MONSTERS;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
			new MobSpawnSettings.SpawnerData(ModEntityTypes.LOST_SOUL.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.ZOMBIEMAN.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.CHAINGUNNER.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.POSSESSEDWORKER.get(), 20, 1, 2),
			new MobSpawnSettings.SpawnerData(ModEntityTypes.ARACHNOTRONETERNAL.get(), 20, 1, 2));

	@Override
	public List<MobSpawnSettings.SpawnerData> getDefaultCreatureSpawnList() {
		return STRUCTURE_CREATURES;
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
		return topBlock.getFluidState().isEmpty();
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
			BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(x, chunkGenerator.getSeaLevel(), z);
			JigsawConfiguration structureSettingsAndStartPool = new JigsawConfiguration(
					() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "portal/start_pool")),
					10);
			JigsawPlacement.addPieces(dynamicRegistryManager, structureSettingsAndStartPool,
					PoolElementStructurePiece::new, chunkGenerator, templateManagerIn, blockpos, this, this.random,
					false, false, p_163621_);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.getBoundingBox();
		}
	}
}