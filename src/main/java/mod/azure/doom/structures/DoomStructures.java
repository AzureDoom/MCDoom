package mod.azure.doom.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.templates.ArchMaykrStructure;
import mod.azure.doom.structures.templates.MaykrStructure;
import mod.azure.doom.structures.templates.MotherDemonStructure;
import mod.azure.doom.structures.templates.NetherPortalStructure;
import mod.azure.doom.structures.templates.PortalStructure;
import mod.azure.doom.structures.templates.TitanSkullStructure;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomStructures {
	public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, DoomMod.MODID);

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> MAYKR = registerStructure("maykr",
			() -> (new MaykrStructure(JigsawConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> ARCHMAYKR = registerStructure("archmaykr",
			() -> (new ArchMaykrStructure(JigsawConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> TITAN_SKULL = registerStructure(
			"titan_skull", () -> (new TitanSkullStructure(JigsawConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> PORTAL = registerStructure("portal",
			() -> (new PortalStructure(JigsawConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> NETHERPORTAL = registerStructure(
			"netherportal", () -> (new NetherPortalStructure(JigsawConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<JigsawConfiguration>> MOTHERDEMON = registerStructure(
			"motherdemon", () -> (new MotherDemonStructure(JigsawConfiguration.CODEC)));

	private static <T extends StructureFeature<?>> RegistryObject<T> registerStructure(String name,
			Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	public static void setupStructures() {
		setupMapSpacingAndLand(MAYKR.get(), new StructureFeatureConfiguration(80, 20, 1234567890), true);
		setupMapSpacingAndLand(ARCHMAYKR.get(), new StructureFeatureConfiguration(80, 20, 1234567898), true);
		setupMapSpacingAndLand(TITAN_SKULL.get(), new StructureFeatureConfiguration(80, 20, 1234567897), true);
		setupMapSpacingAndLand(MOTHERDEMON.get(), new StructureFeatureConfiguration(80, 20, 1234567899), true);
		setupMapSpacingAndLand(PORTAL.get(), new StructureFeatureConfiguration(80, 20, 1234567896), true);
		setupMapSpacingAndLand(NETHERPORTAL.get(), new StructureFeatureConfiguration(80, 20, 1234567895), true);
	}

	public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure,
			StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
		StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder()
					.addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure).build();
		}
		StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
				.putAll(StructureSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

		BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue()
					.structureSettings().structureConfig();

			if (structureMap instanceof ImmutableMap) {
				Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, structureSeparationSettings);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else {
				structureMap.put(structure, structureSeparationSettings);
			}
		});
	}
}