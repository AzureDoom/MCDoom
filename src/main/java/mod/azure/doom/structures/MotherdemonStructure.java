package mod.azure.doom.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.azure.doom.util.registry.DoomStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class MotherdemonStructure extends Structure {

	public static final Codec<MotherdemonStructure> CODEC = RecordCodecBuilder.<MotherdemonStructure>mapCodec(
			instance -> instance.group(MotherdemonStructure.settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName), Codec.intRange(0, 4).fieldOf("size").forGetter(structure -> structure.size), Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)).apply(instance,
					MotherdemonStructure::new))
			.codec();
	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;
	private final int size;
	private final int maxDistanceFromCenter;

	public MotherdemonStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, int maxDistanceFromCenter) {
		super(config);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.size = size;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		final var blockpos = new BlockPos(context.chunkPos().getMinBlockX(), 32, context.chunkPos().getMinBlockZ());

		final var structurePiecesGenerator = JigsawPlacement.addPieces(context, startPool, startJigsawName, size, blockpos, false, Optional.empty(), maxDistanceFromCenter);
		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> type() {
		return DoomStructures.MOTHERDEMON;
	}
}