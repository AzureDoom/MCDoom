package mod.azure.doom.structures.templates;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class ArchMaykrStructure extends Structure<NoFeatureConfig> {
	public ArchMaykrStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return ArchMaykrStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList
			.of(new MobSpawnInfo.Spawners(ModEntityTypes.ARCHMAKER.get(), 100, 1, 1));

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed,
			SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			NoFeatureConfig featureConfig) {
		return !this.isNear(chunkGenerator, seed, chunkRandom, chunkX, chunkZ)
				? getYPositionForFeature(chunkX, chunkZ, chunkGenerator) >= 60
				: false;
	}

	private boolean isNear(ChunkGenerator p_242782_1_, long p_242782_2_, SharedSeedRandom p_242782_4_,
			int p_242782_5_, int p_242782_6_) {
		StructureSeparationSettings structureseparationsettings = p_242782_1_.getSettings()
				.getConfig(DoomStructures.MAYKR.get());
		if (structureseparationsettings == null) {
			return false;
		} else {
			for (int i = p_242782_5_ - 10; i <= p_242782_5_ + 10; ++i) {
				for (int j = p_242782_6_ - 10; j <= p_242782_6_ + 10; ++j) {
					ChunkPos chunkpos = DoomStructures.MAYKR.get().getPotentialFeatureChunk(structureseparationsettings,
							p_242782_2_, p_242782_4_, i, j);
					if (i == chunkpos.x && j == chunkpos.z) {
						return true;
					}
				}
			}

			return false;
		}
	}

	private static int getYPositionForFeature(int p_191070_0_, int p_191070_1_, ChunkGenerator p_191070_2_) {
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
		int i1 = p_191070_2_.getFirstOccupiedHeight(k, l, Heightmap.Type.WORLD_SURFACE_WG);
		int j1 = p_191070_2_.getFirstOccupiedHeight(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int k1 = p_191070_2_.getFirstOccupiedHeight(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
		int l1 = p_191070_2_.getFirstOccupiedHeight(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		return Math.min(Math.min(i1, j1), Math.min(k1, l1));
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
			BlockPos blockpos = new BlockPos(x, 0, z);
			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(DoomMod.MODID, "archmaykr/start_pool")), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random,
					false, true);
			this.pieces.forEach(piece -> piece.move(0, 0, 0));
			this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);
			this.calculateBoundingBox();
		}
	}
}