package mod.azure.doom.util.registry;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.ArchMaykrStructure;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import mod.azure.doom.structures.MotherdemonStructure;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class DoomStructures {

	public static StructureType<?> HELL_CHURCH;
	public static StructureType<?> ICON_FIGHT;
	public static StructureType<?> GLADIATOR_FIGHT;
	public static StructureType<?> MOTHERDEMON;
	public static StructureType<?> ARCHMAYKR;

	public static void registerStructureFeatures() {
		HELL_CHURCH = register(new Identifier(DoomMod.MODID, "hell_church"), HellChurchStructure.CODEC);
		ICON_FIGHT = register(new Identifier(DoomMod.MODID, "icon_fight"), IconStructure.CODEC);
		GLADIATOR_FIGHT = register(new Identifier(DoomMod.MODID, "gladiator_fight"), GladiatorStructure.CODEC);
		MOTHERDEMON = register(new Identifier(DoomMod.MODID, "motherdemon1a"), MotherdemonStructure.CODEC);
		ARCHMAYKR = register(new Identifier(DoomMod.MODID, "archmakyr"), ArchMaykrStructure.CODEC);
	}

	private static <S extends Structure> StructureType<S> register(Identifier id, Codec<S> codec) {
		return Registry.register(Registry.STRUCTURE_TYPE, id, () -> codec);
	}

}
