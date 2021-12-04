package mod.azure.doom.structures.templates;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.MobSpawnSettings;
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
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;

public class ArchMaykrStructure extends StructureFeature<JigsawConfiguration> {

	public ArchMaykrStructure(Codec<JigsawConfiguration> codec) {
		super(codec, (context) -> {
			if (!ArchMaykrStructure.isFeatureChunk(context)) {
				return Optional.empty();
			} else {
				return ArchMaykrStructure.createPiecesGenerator(context);
			}
		}, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList
			.of(new MobSpawnSettings.SpawnerData(ModEntityTypes.ARCHMAKER.get(), 100, 1, 1));

	public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
		if (event.getStructure() == DoomStructures.ARCHMAYKR.get()) {
			event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS);
			event.addEntitySpawns(MobCategory.CREATURE, STRUCTURE_MONSTERS);
		}
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockPos = context.chunkPos().getWorldPosition();
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(),
				context.heightAccessor());
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);
		return topBlock.getFluidState().isEmpty() && landHeight >= 60;
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
		context.config().startPool = () -> context.registryAccess()
				.ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				.get(new ResourceLocation(DoomMod.MODID, "archmaykr/start_pool"));
		context.config().maxDepth = 10;

		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
				PoolElementStructurePiece::new, blockpos, false, true);
		return structurePiecesGenerator;
	}
}