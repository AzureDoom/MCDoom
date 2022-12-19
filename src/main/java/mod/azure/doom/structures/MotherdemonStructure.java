package mod.azure.doom.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.azure.doom.util.registry.DoomStructures;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class MotherdemonStructure extends Structure {

	public static final Codec<MotherdemonStructure> CODEC = RecordCodecBuilder.<MotherdemonStructure>mapCodec(
			instance -> instance.group(MotherdemonStructure.configCodecBuilder(instance),
					StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
					Identifier.CODEC.optionalFieldOf("start_jigsaw_name")
							.forGetter(structure -> structure.startJigsawName),
					Codec.intRange(0, 4).fieldOf("size").forGetter(structure -> structure.size),
					Codec.intRange(1, 128).fieldOf("max_distance_from_center")
							.forGetter(structure -> structure.maxDistanceFromCenter))
					.apply(instance, MotherdemonStructure::new))
			.codec();
	private final RegistryEntry<StructurePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final int maxDistanceFromCenter;

	public MotherdemonStructure(Structure.Config config, RegistryEntry<StructurePool> startPool,
			Optional<Identifier> startJigsawName, int size, int maxDistanceFromCenter) {
		super(config);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.size = size;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
		BlockPos blockpos = new BlockPos(context.chunkPos().getStartX(), 32, context.chunkPos().getStartZ());

		Optional<StructurePosition> structurePiecesGenerator = StructurePoolBasedGenerator.generate(context,
				this.startPool, this.startJigsawName, this.size, blockpos, false, Optional.empty(),
				this.maxDistanceFromCenter);
		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> getType() {
		return DoomStructures.MOTHERDEMON;
	}
}