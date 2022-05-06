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

public class ArchMaykrStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public ArchMaykrStructure() {
		super(StructurePoolFeatureConfig.CODEC, ArchMaykrStructure::createPiecesGenerator,
				PostPlacementProcessor.EMPTY);
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

		if (!ArchMaykrStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}
		BlockPos blockpos = context.chunkPos().getCenterAtY(0);
		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.method_30419(context, PoolStructurePiece::new, blockpos, true, true);
		return structurePiecesGenerator;
	}
}