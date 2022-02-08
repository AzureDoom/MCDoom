package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DoomStructuresConfigured {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_HELL_CHURCH = DoomStructures.HELL_CHURCH
			.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new Identifier(DoomMod.MODID, "configured_hell_church"), CONFIGURED_HELL_CHURCH);
	}
}
