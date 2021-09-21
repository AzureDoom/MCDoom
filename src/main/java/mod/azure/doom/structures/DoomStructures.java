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
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class DoomStructures {

	public static StructureFeature<DefaultFeatureConfig> MAYKR = new MaykrStructure(DefaultFeatureConfig.CODEC);

	public static StructureFeature<DefaultFeatureConfig> ARCHMAYKR = new ArchMaykrStructure(DefaultFeatureConfig.CODEC);

	public static StructureFeature<DefaultFeatureConfig> TITAN_SKULL = new TitanSkullStructure(
			DefaultFeatureConfig.CODEC);

	public static StructureFeature<DefaultFeatureConfig> MOTHERDEMON = new MotherDemonStructure(
			DefaultFeatureConfig.CODEC);

	public static StructureFeature<DefaultFeatureConfig> PORTAL = new PortalStructure(DefaultFeatureConfig.CODEC);

	public static StructureFeature<DefaultFeatureConfig> NETHERPORTAL = new NetherPortalStructure(
			DefaultFeatureConfig.CODEC);

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