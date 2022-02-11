package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DoomStructures {

	public static StructureFeature<StructurePoolFeatureConfig> HELL_CHURCH = new HellChurchStructure(
			StructurePoolFeatureConfig.CODEC);
	public static StructureFeature<StructurePoolFeatureConfig> ICON_FIGHT = new IconStructure(
			StructurePoolFeatureConfig.CODEC);

	public static void setupAndRegisterStructureFeatures() {
		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "hell_church"), HELL_CHURCH)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567890))
				.adjustsSurface().register();
		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "icon_fight"), ICON_FIGHT)
		.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567891))
		.adjustsSurface().register();
	}

}
