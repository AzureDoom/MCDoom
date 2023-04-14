package mod.azure.doom.util.registry;

import com.mojang.serialization.Codec;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.ArchMaykrStructure;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import mod.azure.doom.structures.MotherdemonStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DoomStructures {

	public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, DoomMod.MODID);

	public static final RegistryObject<StructureType<?>> HELL_CHURCH = DEFERRED_REGISTRY_STRUCTURE.register("hell_church", () -> typeConvert(HellChurchStructure.CODEC));

	public static final RegistryObject<StructureType<?>> ICON_FIGHT = DEFERRED_REGISTRY_STRUCTURE.register("icon_fight", () -> typeConvert(IconStructure.CODEC));

	public static final RegistryObject<StructureType<?>> GLADIATOR_FIGHT = DEFERRED_REGISTRY_STRUCTURE.register("gladiator_fight", () -> typeConvert(GladiatorStructure.CODEC));

	public static final RegistryObject<StructureType<?>> MOTHERDEMON = DEFERRED_REGISTRY_STRUCTURE.register("motherdemon1a", () -> typeConvert(MotherdemonStructure.CODEC));

	public static final RegistryObject<StructureType<?>> ARCHMAYKR = DEFERRED_REGISTRY_STRUCTURE.register("archmakyr", () -> typeConvert(ArchMaykrStructure.CODEC));

	private static <S extends Structure> StructureType<S> typeConvert(Codec<S> codec) {
		return () -> codec;
	}

}
