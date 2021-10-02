package mod.azure.doom.structures.templates;

import java.util.function.Predicate;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.class_6622;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.ChunkRandom;

public class PortalStructure extends StructureFeature<DefaultFeatureConfig> {

	public PortalStructure(Codec<DefaultFeatureConfig> codec, class_6622<DefaultFeatureConfig> config) {
		super(codec, config);
	}

	private static final Pool<SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
			new SpawnSettings.SpawnEntry(ModEntityTypes.LOST_SOUL, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.TURRET, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ZOMBIEMAN, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.CHAINGUNNER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.POSSESSEDWORKER, 20, 1, 2),
			new SpawnSettings.SpawnEntry(ModEntityTypes.ARACHNOTRONETERNAL, 20, 1, 2));

	public static Pool<SpawnEntry> getStructureMonsters() {
		return STRUCTURE_MONSTERS;
	}
	
	public static Pool<SpawnEntry> getStructureCreatures() {
		return STRUCTURE_MONSTERS;
	}

	@Override
	protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed,
			ChunkRandom random, ChunkPos pos, ChunkPos chunkPos, DefaultFeatureConfig config, HeightLimitView world) {
		return true;
	}

	public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
		public Start(StructureFeature<DefaultFeatureConfig> structureFeature, ChunkPos chunkPos, int i, long l) {
			super(structureFeature, chunkPos, i, l);
		}

		@Override
		public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator,
				StructureManager manager, ChunkPos pos, DefaultFeatureConfig featureConfig, HeightLimitView world,
				Predicate<Biome> predicate) {
			int x = (pos.x << 4) + 7;
			int z = (pos.z << 4) + 7;
			BlockPos.Mutable blockpos = new BlockPos.Mutable(x, 0, z);
			StructurePoolFeatureConfig structureSettingsAndStartPool = new StructurePoolFeatureConfig(
					() -> registryManager.get(Registry.STRUCTURE_POOL_KEY)
							.get(new Identifier(DoomMod.MODID, "portal/start_pool")),
					10);
			StructurePoolBasedGenerator.generate(registryManager, structureSettingsAndStartPool,
					PoolStructurePiece::new, chunkGenerator, manager, blockpos, this, this.random, false, true, world,
					predicate);
			this.children.forEach(piece -> piece.translate(0, 2, 0));
			this.children.forEach(piece -> piece.getBoundingBox().minY -= 1);
			this.setBoundingBoxFromChildren();
		}

	}
}