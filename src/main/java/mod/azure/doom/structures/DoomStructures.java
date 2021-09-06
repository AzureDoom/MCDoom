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
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoomStructures {
	public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, DoomMod.MODID);

	public static final RegistryObject<Structure<NoFeatureConfig>> MAYKR = registerStructure("maykr",
			() -> (new MaykrStructure(NoFeatureConfig.CODEC)));

	public static final RegistryObject<Structure<NoFeatureConfig>> ARCHMAYKR = registerStructure("archmaykr",
			() -> (new ArchMaykrStructure(NoFeatureConfig.CODEC)));

	public static final RegistryObject<Structure<NoFeatureConfig>> TITAN_SKULL = registerStructure("titan_skull",
			() -> (new TitanSkullStructure(NoFeatureConfig.CODEC)));

	public static final RegistryObject<Structure<NoFeatureConfig>> PORTAL = registerStructure("portal",
			() -> (new PortalStructure(NoFeatureConfig.CODEC)));

	public static final RegistryObject<Structure<NoFeatureConfig>> NETHERPORTAL = registerStructure("netherportal",
			() -> (new NetherPortalStructure(NoFeatureConfig.CODEC)));

	public static final RegistryObject<Structure<NoFeatureConfig>> MOTHERDEMON = registerStructure("motherdemon",
			() -> (new MotherDemonStructure(NoFeatureConfig.CODEC)));

	private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	public static void setupStructures() {
		setupMapSpacingAndLand(MAYKR.get(), new StructureSeparationSettings(80, 20, 1234567890), true);
		setupMapSpacingAndLand(ARCHMAYKR.get(), new StructureSeparationSettings(80, 20, 1234567898), true);
		setupMapSpacingAndLand(TITAN_SKULL.get(), new StructureSeparationSettings(80, 20, 1234567890), true);
		setupMapSpacingAndLand(MOTHERDEMON.get(), new StructureSeparationSettings(80, 20, 1234567899), true);
		setupMapSpacingAndLand(PORTAL.get(), new StructureSeparationSettings(80, 20, 1234567890), true);
		setupMapSpacingAndLand(NETHERPORTAL.get(), new StructureSeparationSettings(80, 20, 1234567895), true);
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure,
			StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		if (transformSurroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
					.addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
				.putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings()
					.structureConfig();

			if (structureMap instanceof ImmutableMap) {
				Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, structureSeparationSettings);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else {
				structureMap.put(structure, structureSeparationSettings);
			}
		});
	}
}