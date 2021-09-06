package mod.azure.doom.structures;

import mod.azure.doom.DoomMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class DoomConfiguredStructures {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MAYKR = DoomStructures.MAYKR
			.configure(DefaultFeatureConfig.DEFAULT);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ARCHMAYKR = DoomStructures.ARCHMAYKR
			.configure(DefaultFeatureConfig.DEFAULT);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_TITAN_SKULL = DoomStructures.TITAN_SKULL
			.configure(DefaultFeatureConfig.DEFAULT);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_PORTAL = DoomStructures.PORTAL
			.configure(DefaultFeatureConfig.DEFAULT);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MOTHERDEMON = DoomStructures.MOTHERDEMON
			.configure(DefaultFeatureConfig.DEFAULT);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_NETHERPORTAL = DoomStructures.NETHERPORTAL
			.configure(DefaultFeatureConfig.DEFAULT);

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_maykr"), CONFIGURED_MAYKR);
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_archmaykr"), CONFIGURED_ARCHMAYKR);
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_titan_skull"), CONFIGURED_TITAN_SKULL);
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_portal"), CONFIGURED_PORTAL);
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_netherportal"), CONFIGURED_NETHERPORTAL);
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_motherdemon"), CONFIGURED_MOTHERDEMON);
	}

}
