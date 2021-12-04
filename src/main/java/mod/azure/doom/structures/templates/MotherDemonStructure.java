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

public class MotherDemonStructure extends StructureFeature<JigsawConfiguration> {

	public MotherDemonStructure(Codec<JigsawConfiguration> codec) {
		super(codec, (context) -> {
			if (!MotherDemonStructure.isFeatureChunk(context)) {
				return Optional.empty();
			} else {
				return MotherDemonStructure.createPiecesGenerator(context);
			}
		}, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList
			.of(new MobSpawnSettings.SpawnerData(ModEntityTypes.MOTHERDEMON.get(), 100, 1, 1));

	public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
		if (event.getStructure() == DoomStructures.MOTHERDEMON.get()) {
			event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS);
			event.addEntitySpawns(MobCategory.CREATURE, STRUCTURE_MONSTERS);
		}
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockPos = context.chunkPos().getMiddleBlockPosition(0);
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(),
				context.heightAccessor());
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);
		return topBlock.getFluidState().isEmpty() && landHeight >= 33;
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(context.chunkPos().getMinBlockX(), 33,
				context.chunkPos().getMinBlockZ());
		context.config().startPool = () -> context.registryAccess()
				.ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				.get(new ResourceLocation(DoomMod.MODID, "motherdemon/start_pool"));
		context.config().maxDepth = 10;

		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
				PoolElementStructurePiece::new, blockpos, false, false);
		return structurePiecesGenerator;
	}
}