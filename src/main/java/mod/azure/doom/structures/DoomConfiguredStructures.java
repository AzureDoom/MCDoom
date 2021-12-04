package mod.azure.doom.structures;

import mod.azure.doom.DoomMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class DoomConfiguredStructures {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MAYKR = DoomStructures.MAYKR.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ARCHMAYKR = DoomStructures.ARCHMAYKR.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_TITAN_SKULL = DoomStructures.TITAN_SKULL.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_PORTAL = DoomStructures.PORTAL.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_NETHERPORTAL = DoomStructures.NETHERPORTAL.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_MOTHERDEMON = DoomStructures.MOTHERDEMON.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_maykr"), CONFIGURED_MAYKR);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_archmaykr"), CONFIGURED_ARCHMAYKR);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_titan_skull"),
				CONFIGURED_TITAN_SKULL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_portal"), CONFIGURED_PORTAL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_netherportal"),
				CONFIGURED_NETHERPORTAL);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_motherdemon"),
				CONFIGURED_MOTHERDEMON);
	}
}