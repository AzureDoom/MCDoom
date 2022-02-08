package mod.azure.doom.structures;

import java.util.Optional;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public class HellChurchStructure extends StructureFeature<JigsawConfiguration> {

	public HellChurchStructure(Codec<JigsawConfiguration> codec) {
		super(codec, HellChurchStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

//	private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_MONSTERS = Lazy
//			.of(() -> ImmutableList.of(new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 100, 4, 9),
//					new MobSpawnSettings.SpawnerData(EntityType.VINDICATOR, 100, 4, 9)));
//
//	public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
//		if (event.getStructure() == DoomStructures.HELL_CHURCH.get()) {
//			event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS.get());
//		}
//	}

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
		if (!HellChurchStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		JigsawConfiguration newConfig = new JigsawConfiguration(
				() -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
						.get(new ResourceLocation(DoomMod.MODID, "hell_church/start_pool")),
				10);
		PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), newConfig,
				context.heightAccessor(), context.validBiome(), context.structureManager(), context.registryAccess());
		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0).offset(0, -3, 0);

		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(newContext,
				PoolElementStructurePiece::new, blockpos, false, true);

		if (structurePiecesGenerator.isPresent()) {
			// I use to debug and quickly find out if the structure is spawning or not and
			// where it is.
			// This is returning the coordinates of the center starting piece.
			DoomMod.LOGGER.log(Level.DEBUG, "Hell Church at " + blockpos);
		}
		return structurePiecesGenerator;
	}
}