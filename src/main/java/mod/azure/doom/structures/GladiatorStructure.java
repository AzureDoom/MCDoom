package mod.azure.doom.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class GladiatorStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public GladiatorStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, GladiatorStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

	public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool
			.of(new SpawnSettings.SpawnEntry(ModEntityTypes.GLADIATOR, 100, 4, 9));

	private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);
		int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG, context.world());
		VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(),
				spawnXZPosition.getZ(), context.world());
		BlockState topBlock = columnOfBlocks.getState(landHeight);
		return topBlock.getFluidState().isEmpty();
	}

	public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(
			StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		if (!GladiatorStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(() -> context.registryManager()
				.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(DoomMod.MODID, "gladiator_fight/start_pool")),
				10);

		StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), newConfig,
				context.world(), context.validBiome(), context.structureManager(), context.registryManager());

		BlockPos blockpos = new BlockPos(context.chunkPos().getStartX(), 32, context.chunkPos().getStartZ());
		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.generate(newContext, PoolStructurePiece::new, blockpos, true, false);
		return structurePiecesGenerator;
	}
}