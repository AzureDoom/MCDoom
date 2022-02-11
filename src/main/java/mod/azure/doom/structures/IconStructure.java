package mod.azure.doom.structures;

import java.util.Optional;

import org.apache.logging.log4j.Level;

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

public class IconStructure extends StructureFeature<StructurePoolFeatureConfig> {

	public IconStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, IconStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

	public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool
			.of(new SpawnSettings.SpawnEntry(ModEntityTypes.ICONOFSIN, 100, 4, 9));

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

		if (!IconStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(() -> context.registryManager()
				.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(DoomMod.MODID, "icon_fight/start_pool")), 10);

		StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), newConfig,
				context.world(), context.validBiome(), context.structureManager(), context.registryManager());

		BlockPos blockpos = new BlockPos(context.chunkPos().getStartX(), -63, context.chunkPos().getStartZ());
		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator
				.generate(newContext, PoolStructurePiece::new, blockpos, false, false);

		if (structurePiecesGenerator.isPresent()) {
			// I use to debug and quickly find out if the structure is spawning or not and
			// where it is.
			// This is returning the coordinates of the center starting piece.
			DoomMod.LOGGER.log(Level.DEBUG, "Icon Structure at " + blockpos);
		}
		return structurePiecesGenerator;
	}
}