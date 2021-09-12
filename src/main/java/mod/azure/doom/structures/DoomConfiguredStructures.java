package mod.azure.doom.structures;

import mod.azure.doom.DoomMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class DoomConfiguredStructures {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MAYKR = DoomStructures.MAYKR.get()
			.configured(FeatureConfiguration.NONE);
	
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ARCHMAYKR = DoomStructures.ARCHMAYKR.get()
			.configured(FeatureConfiguration.NONE);

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_TITAN_SKULL = DoomStructures.TITAN_SKULL.get()
			.configured(FeatureConfiguration.NONE);

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_PORTAL = DoomStructures.PORTAL.get()
			.configured(FeatureConfiguration.NONE);

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_NETHERPORTAL = DoomStructures.NETHERPORTAL.get()
			.configured(FeatureConfiguration.NONE);

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MOTHERDEMON = DoomStructures.MOTHERDEMON.get()
			.configured(FeatureConfiguration.NONE);

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_maykr"),
				CONFIGURED_MAYKR);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_archmaykr"),
				CONFIGURED_ARCHMAYKR);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_titan_skull"),
				CONFIGURED_TITAN_SKULL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_portal"),
				CONFIGURED_PORTAL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_netherportal"),
				CONFIGURED_NETHERPORTAL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_motherdemon"),
				CONFIGURED_MOTHERDEMON);

		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.MAYKR.get(), CONFIGURED_MAYKR);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.ARCHMAYKR.get(), CONFIGURED_ARCHMAYKR);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.TITAN_SKULL.get(), CONFIGURED_TITAN_SKULL);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.PORTAL.get(), CONFIGURED_PORTAL);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.NETHERPORTAL.get(), CONFIGURED_NETHERPORTAL);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DoomStructures.MOTHERDEMON.get(), CONFIGURED_MOTHERDEMON);
	}
}