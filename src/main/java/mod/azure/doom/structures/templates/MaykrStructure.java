package mod.azure.doom.structures.templates;

import java.util.List;
import java.util.Random;

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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Rotation;
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
		return !this.isNearby(chunkGenerator, seed, chunkRandom, chunkPos)
				? getYPositionForFeature(pos.x, pos.z, chunkGenerator, world) >= 60
				: false;
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

	private static int getYPositionForFeature(int p_191070_0_, int p_191070_1_, ChunkGenerator p_191070_2_,
			LevelHeightAccessor world) {
		Random random = new Random((long) (p_191070_0_ + p_191070_1_ * 10387313));
		Rotation rotation = Rotation.getRandom(random);
		int i = 5;
		int j = 5;
		if (rotation == Rotation.CLOCKWISE_90) {
			i = -5;
		} else if (rotation == Rotation.CLOCKWISE_180) {
			i = -5;
			j = -5;
		} else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
			j = -5;
		}

		int k = (p_191070_0_ << 4) + 7;
		int l = (p_191070_1_ << 4) + 7;
		int i1 = p_191070_2_.getFirstOccupiedHeight(k, l, Heightmap.Types.WORLD_SURFACE_WG, world);
		int j1 = p_191070_2_.getFirstOccupiedHeight(k, l + j, Heightmap.Types.WORLD_SURFACE_WG, world);
		int k1 = p_191070_2_.getFirstOccupiedHeight(k + i, l, Heightmap.Types.WORLD_SURFACE_WG, world);
		int l1 = p_191070_2_.getFirstOccupiedHeight(k + i, l + j, Heightmap.Types.WORLD_SURFACE_WG, world);
		return Math.min(Math.min(i1, j1), Math.min(k1, l1));
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
			BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(x, 0, z);
			JigsawConfiguration structureSettingsAndStartPool = new JigsawConfiguration(
					() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "maykr/start_pool")),
					10);
			JigsawPlacement.addPieces(dynamicRegistryManager, structureSettingsAndStartPool,
					PoolElementStructurePiece::new, chunkGenerator, templateManagerIn, blockpos, this, this.random,
					false, true, p_163621_);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.getBoundingBox();
		}
	}
}