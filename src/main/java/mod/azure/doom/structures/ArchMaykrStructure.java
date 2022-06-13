package mod.azure.doom.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.azure.doom.util.registry.DoomStructures;
import net.minecraft.structure.StructureType;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.heightprovider.HeightProvider;

public class ArchMaykrStructure extends StructureFeature {

	public static final Codec<ArchMaykrStructure> CODEC = RecordCodecBuilder
			.<ArchMaykrStructure>mapCodec(instance -> instance.group(ArchMaykrStructure.settingsCodec(instance),
					StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
					Identifier.CODEC.optionalFieldOf("start_jigsaw_name")
							.forGetter(structure -> structure.startJigsawName),
					Codec.intRange(0, 101).fieldOf("size").forGetter(structure -> structure.size),
					HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
					Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap")
							.forGetter(structure -> structure.projectStartToHeightmap),
					Codec.intRange(1, 128).fieldOf("max_distance_from_center")
							.forGetter(structure -> structure.maxDistanceFromCenter))
					.apply(instance, ArchMaykrStructure::new))
			.codec();
	private final Holder<StructurePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final HeightProvider startHeight;
	private final Optional<Heightmap.Type> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public ArchMaykrStructure(StructureFeature.StructureSettings config, Holder<StructurePool> startPool,
			Optional<Identifier> startJigsawName, int size, HeightProvider startHeight,
			Optional<Heightmap.Type> projectStartToHeightmap, int maxDistanceFromCenter) {
		super(config);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.size = size;
		this.startHeight = startHeight;
		this.projectStartToHeightmap = projectStartToHeightmap;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<GenerationStub> findGenerationPos(GenerationContext context) {
		int startY = this.startHeight.get(context.random(),
				new HeightContext(context.chunkGenerator(), context.world()));
		ChunkPos chunkPos = context.chunkPos();
		BlockPos blockpos = new BlockPos(chunkPos.getStartX(), startY, chunkPos.getStartZ());

		Optional<GenerationStub> structurePiecesGenerator = StructurePoolBasedGenerator.method_30419(context,
				this.startPool, this.startJigsawName, this.size, blockpos, false, this.projectStartToHeightmap,
				this.maxDistanceFromCenter);
		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> getType() {
		return DoomStructures.ARCHMAYKR;
	}
}