package mod.azure.doom.structures;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.StructurePiecesGeneratorFactory;
import net.minecraft.structure.piece.PoolStructurePiece;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class IconStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public IconStructure() {
		super(StructurePoolFeatureConfig.CODEC, IconStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

	private static boolean isFeatureChunk(StructurePiecesGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);
		int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG, context.heightLimitView());
		VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(),
				spawnXZPosition.getZ(), context.heightLimitView());
		BlockState topBlock = columnOfBlocks.getState(landHeight);
		return topBlock.getFluidState().isEmpty();
	}

	public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(
			StructurePiecesGeneratorFactory.Context<StructurePoolFeatureConfig> context) {

		if (!IconStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}
		BlockPos blockpos = new BlockPos(context.chunkPos().getStartX(), -63, context.chunkPos().getStartZ());
		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.method_30419(context, PoolStructurePiece::new, blockpos, true, false);
		return structurePiecesGenerator;
	}
}