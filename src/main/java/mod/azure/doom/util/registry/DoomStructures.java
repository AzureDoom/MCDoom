package mod.azure.doom.util.registry;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import mod.azure.doom.structures.MotherdemonStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class DoomStructures {

	public static StructureType<?> HELL_CHURCH;
	public static StructureType<?> ICON_FIGHT;
	public static StructureType<?> GLADIATOR_FIGHT;
	public static StructureType<?> MOTHERDEMON;
	public static StructureType<?> ARCHMAYKR;

	public static void registerStructureFeatures() {
		HELL_CHURCH = register(DoomMod.modResource("hell_church"), HellChurchStructure.CODEC);
		ICON_FIGHT = register(DoomMod.modResource("icon_fight"), IconStructure.CODEC);
		GLADIATOR_FIGHT = register(DoomMod.modResource("gladiator_fight"), GladiatorStructure.CODEC);
		MOTHERDEMON = register(DoomMod.modResource("motherdemon1a"), MotherdemonStructure.CODEC);
		ARCHMAYKR = register(DoomMod.modResource("archmakyr"), JigsawStructure.CODEC);
	}

	private static <S extends Structure> StructureType<S> register(ResourceLocation id, Codec<S> codec) {
		return Registry.register(Registry.STRUCTURE_TYPES, id, () -> codec);
	}

}
