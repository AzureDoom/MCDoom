package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.ArchMaykrStructure;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import mod.azure.doom.structures.MotherdemonStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomStructures {

	public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, DoomMod.MODID);

	public static final RegistryObject<StructureFeature<?>> HELL_CHURCH = DEFERRED_REGISTRY_STRUCTURE
			.register("hell_church", HellChurchStructure::new);

	public static final RegistryObject<StructureFeature<?>> ICON_FIGHT = DEFERRED_REGISTRY_STRUCTURE
			.register("icon_fight", IconStructure::new);

	public static final RegistryObject<StructureFeature<?>> GLADIATOR_FIGHT = DEFERRED_REGISTRY_STRUCTURE
			.register("gladiator_fight", GladiatorStructure::new);

	public static final RegistryObject<StructureFeature<?>> MOTHERDEMON = DEFERRED_REGISTRY_STRUCTURE
			.register("motherdemon1a", MotherdemonStructure::new);

	public static final RegistryObject<StructureFeature<?>> ARCHMAYKR = DEFERRED_REGISTRY_STRUCTURE
			.register("archmakyr", ArchMaykrStructure::new);;

}
