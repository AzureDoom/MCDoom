package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class DoomStructuresConfigured {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_HELL_CHURCH = DoomStructures.HELL_CHURCH.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ICON_FIGHT = DoomStructures.ICON_FIGHT.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_GLADIATOR_FIGHT = DoomStructures.GLADIATOR_FIGHT.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_hell_church"),
				CONFIGURED_HELL_CHURCH);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_icon_fight"),
				CONFIGURED_ICON_FIGHT);
		Registry.register(registry, new ResourceLocation(DoomMod.MODID, "configured_gladiator_fight"),
				CONFIGURED_GLADIATOR_FIGHT);
	}
}
