package mod.azure.doom.structures;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class IconStructure extends StructureFeature<JigsawConfiguration> {

	public IconStructure() {
		super(JigsawConfiguration.CODEC, IconStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockPos = context.chunkPos().getWorldPosition();
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(),
				context.heightAccessor());
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);
		return topBlock.getFluidState().isEmpty();
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		if (!IconStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), -63, context.chunkPos().getMinBlockZ());
		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
				PoolElementStructurePiece::new, blockpos, false, false);
		return structurePiecesGenerator;
	}
}