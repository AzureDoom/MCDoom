package mod.azure.doom.structures;

import mod.azure.doom.DoomMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class DoomConfiguredStructures {

	public static StructureFeature<?, ?> CONFIGURED_MAYKR = DoomStructures.MAYKR.get()
			.configured(IFeatureConfig.NONE);
	
	public static StructureFeature<?, ?> CONFIGURED_ARCHMAYKR = DoomStructures.ARCHMAYKR.get()
			.configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_TITAN_SKULL = DoomStructures.TITAN_SKULL.get()
			.configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_PORTAL = DoomStructures.PORTAL.get()
			.configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_NETHERPORTAL = DoomStructures.NETHERPORTAL.get()
			.configured(IFeatureConfig.NONE);

	public static StructureFeature<?, ?> CONFIGURED_MOTHERDEMON = DoomStructures.MOTHERDEMON.get()
			.configured(IFeatureConfig.NONE);

	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
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

		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.MAYKR.get(), CONFIGURED_MAYKR);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.ARCHMAYKR.get(), CONFIGURED_ARCHMAYKR);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.TITAN_SKULL.get(), CONFIGURED_TITAN_SKULL);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.PORTAL.get(), CONFIGURED_PORTAL);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.NETHERPORTAL.get(), CONFIGURED_NETHERPORTAL);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(DoomStructures.MOTHERDEMON.get(), CONFIGURED_MOTHERDEMON);
	}
}