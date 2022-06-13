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
import net.minecraft.world.gen.feature.StructureFeature;

public class MotherdemonStructure extends StructureFeature {

	public static final Codec<MotherdemonStructure> CODEC = RecordCodecBuilder
			.<MotherdemonStructure>mapCodec(instance -> instance.group(MotherdemonStructure.settingsCodec(instance),
					StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
					Identifier.CODEC.optionalFieldOf("start_jigsaw_name")
							.forGetter(structure -> structure.startJigsawName),
					Codec.intRange(0, 4).fieldOf("size").forGetter(structure -> structure.size),
					Codec.intRange(1, 128).fieldOf("max_distance_from_center")
							.forGetter(structure -> structure.maxDistanceFromCenter))
					.apply(instance, MotherdemonStructure::new))
			.codec();
	private final Holder<StructurePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final int maxDistanceFromCenter;

	public MotherdemonStructure(StructureFeature.StructureSettings config, Holder<StructurePool> startPool,
			Optional<Identifier> startJigsawName, int size, int maxDistanceFromCenter) {
		super(config);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.size = size;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<GenerationStub> findGenerationPos(GenerationContext context) {
		BlockPos blockpos = new BlockPos(context.chunkPos().getStartX(), 32, context.chunkPos().getStartZ());

		Optional<GenerationStub> structurePiecesGenerator = StructurePoolBasedGenerator.method_30419(context,
				this.startPool, this.startJigsawName, this.size, blockpos, false, Optional.empty(),
				this.maxDistanceFromCenter);
		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> getType() {
		return DoomStructures.MOTHERDEMON;
	}
}