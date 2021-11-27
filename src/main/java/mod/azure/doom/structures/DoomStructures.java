package mod.azure.doom.structures;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.templates.ArchMaykrStructure;
import mod.azure.doom.structures.templates.MaykrStructure;
import mod.azure.doom.structures.templates.MotherDemonStructure;
import mod.azure.doom.structures.templates.NetherPortalStructure;
import mod.azure.doom.structures.templates.PortalStructure;
import mod.azure.doom.structures.templates.TitanSkullStructure;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DoomStructures {

	public static StructureFeature<StructurePoolFeatureConfig> MAYKR = new MaykrStructure(
			StructurePoolFeatureConfig.CODEC);

	public static StructureFeature<StructurePoolFeatureConfig> ARCHMAYKR = new ArchMaykrStructure(
			StructurePoolFeatureConfig.CODEC);

	public static StructureFeature<StructurePoolFeatureConfig> TITAN_SKULL = new TitanSkullStructure(
			StructurePoolFeatureConfig.CODEC);

	public static StructureFeature<StructurePoolFeatureConfig> MOTHERDEMON = new MotherDemonStructure(
			StructurePoolFeatureConfig.CODEC);

	public static StructureFeature<StructurePoolFeatureConfig> PORTAL = new PortalStructure(
			StructurePoolFeatureConfig.CODEC);

	public static StructureFeature<StructurePoolFeatureConfig> NETHERPORTAL = new NetherPortalStructure(
			StructurePoolFeatureConfig.CODEC);

	public static void setupAndRegisterStructureFeatures() {

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "maykr"), MAYKR)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567890))
				.adjustsSurface().register();

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "archmaykr"), ARCHMAYKR)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567898))
				.adjustsSurface().register();

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "titan_skull"), TITAN_SKULL)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567890))
				.adjustsSurface().register();

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "motherdemon"), MOTHERDEMON)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567899))
				.adjustsSurface().register();

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "portal"), PORTAL)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567890))
				.adjustsSurface().register();

		FabricStructureBuilder.create(new Identifier(DoomMod.MODID, "netherportal"), NETHERPORTAL)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(80, 20, 1234567895))
				.adjustsSurface().register();
	}
}