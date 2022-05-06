package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.mixin.StructureFeatureAccessor;
import mod.azure.doom.structures.ArchMaykrStructure;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import mod.azure.doom.structures.MotherdemonStructure;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;

public class DoomStructures {

	public static StructureFeature<?> HELL_CHURCH = new HellChurchStructure();
	public static StructureFeature<?> ICON_FIGHT = new IconStructure();
	public static StructureFeature<?> GLADIATOR_FIGHT = new GladiatorStructure();
	public static StructureFeature<?> MOTHERDEMON = new MotherdemonStructure();
	public static StructureFeature<?> ARCHMAYKR = new ArchMaykrStructure();

	public static void setupAndRegisterStructureFeatures() {
		StructureFeatureAccessor.callRegister(DoomMod.MODID + ":hell_church", HELL_CHURCH,
				GenerationStep.Feature.SURFACE_STRUCTURES);
		StructureFeatureAccessor.callRegister(DoomMod.MODID + ":icon_fight", ICON_FIGHT,
				GenerationStep.Feature.SURFACE_STRUCTURES);
		StructureFeatureAccessor.callRegister(DoomMod.MODID + ":gladiator_fight", GLADIATOR_FIGHT,
				GenerationStep.Feature.SURFACE_STRUCTURES);
		StructureFeatureAccessor.callRegister(DoomMod.MODID + ":motherdemon1a", MOTHERDEMON,
				GenerationStep.Feature.SURFACE_STRUCTURES);
		StructureFeatureAccessor.callRegister(DoomMod.MODID + ":archmakyr", ARCHMAYKR,
				GenerationStep.Feature.SURFACE_STRUCTURES);
	}

}
