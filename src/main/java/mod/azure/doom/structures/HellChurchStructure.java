package mod.azure.doom.structures;

import java.util.Optional;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import net.minecraft.block.BlockState;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class HellChurchStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public HellChurchStructure(Codec<StructurePoolFeatureConfig> codec) {
		// Create the pieces layout of the structure and give it to the game
		super(codec, HellChurchStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

//	public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
//            new SpawnSettings.SpawnEntry(EntityType.ILLUSIONER, 100, 4, 9),
//            new SpawnSettings.SpawnEntry(EntityType.VINDICATOR, 100, 4, 9)
//    );

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

		if (!HellChurchStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(() -> context.registryManager()
				.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(DoomMod.MODID, "hell_church/start_pool")), 10);

		StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), newConfig,
				context.world(), context.validBiome(), context.structureManager(), context.registryManager());

		BlockPos blockpos = context.chunkPos().getCenterAtY(0);
		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.generate(newContext, PoolStructurePiece::new, blockpos, false, true);

		if (structurePiecesGenerator.isPresent()) {
			// I use to debug and quickly find out if the structure is spawning or not and
			// where it is.
			// This is returning the coordinates of the center starting piece.
			DoomMod.LOGGER.log(Level.DEBUG, "Hell Church at " + blockpos);
		}
		return structurePiecesGenerator;
	}
}