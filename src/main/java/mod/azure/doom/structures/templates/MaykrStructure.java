package mod.azure.doom.structures.templates;

import java.util.Optional;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.mixin.StructurePoolFeatureConfigAccessor;
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
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class MaykrStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public MaykrStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, (context) -> {
			if (!MaykrStructure.canGenerate(context)) {
				return Optional.empty();
			} else {
				return MaykrStructure.createPiecesGenerator(context);
			}
		}, PostPlacementProcessor.EMPTY);
	}

	public static final Pool<SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.MAYKRDRONE, 50, 2, 5),
			new SpawnSettings.SpawnEntry(ModEntityTypes.BLOODMAYKR, 50, 1, 2));

	public static final Pool<SpawnEntry> STRUCTURE_CREATURES = Pool.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.MAYKRDRONE, 50, 2, 5),
			new SpawnSettings.SpawnEntry(ModEntityTypes.BLOODMAYKR, 50, 1, 2));

	private static boolean canGenerate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);
		int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG, context.world());
		VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(),
				spawnXZPosition.getZ(), context.world());

		BlockState topBlock = columnOfBlocks.getState(landHeight);

		return topBlock.getFluidState().isEmpty() && landHeight >= 60;
	}

	public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(
			StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		BlockPos blockpos = context.chunkPos().getCenterAtY(0);
		((StructurePoolFeatureConfigAccessor) context.config()).setStructures(() -> context.registryManager()
				.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(DoomMod.MODID, "maykr/start_pool")));
		((StructurePoolFeatureConfigAccessor) context.config()).setSize(10);

		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.generate(context, PoolStructurePiece::new, blockpos, false, true);
		return structurePiecesGenerator;
	}

}