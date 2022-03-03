package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.structures.GladiatorStructure;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomStructures {

	public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, DoomMod.MODID);

	public static final RegistryObject<StructureFeature<?>> HELL_CHURCH = DEFERRED_REGISTRY_STRUCTURE
			.register("hell_church", HellChurchStructure::new);

	public static final RegistryObject<StructureFeature<?>> ICON_FIGHT = DEFERRED_REGISTRY_STRUCTURE
			.register("icon_fight", IconStructure::new);

	public static final RegistryObject<StructureFeature<?>> GLADIATOR_FIGHT = DEFERRED_REGISTRY_STRUCTURE
			.register("gladiator_fight", GladiatorStructure::new);

//	public static void setupStructures() {
//		setupMapSpacingAndLand(HELL_CHURCH.get(), new StructureFeatureConfiguration(80, 20, 1234567890), true);
//		setupMapSpacingAndLand(ICON_FIGHT.get(), new StructureFeatureConfiguration(80, 20, 1234567891), true);
//		setupMapSpacingAndLand(GLADIATOR_FIGHT.get(), new StructureFeatureConfiguration(80, 20, 1234567892), true);
//	}
//
//	public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure,
//			StructureFeatureConfiguration structureFeatureConfiguration, boolean transformSurroundingLand) {
//		StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
//		if (transformSurroundingLand) {
//			StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder()
//					.addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure).build();
//		}
//		StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
//				.putAll(StructureSettings.DEFAULTS).put(structure, structureFeatureConfiguration).build();
//		BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
//			Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue()
//					.structureSettings().structureConfig();
//			if (structureMap instanceof ImmutableMap) {
//				Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
//				tempMap.put(structure, structureFeatureConfiguration);
//				settings.getValue().structureSettings().structureConfig = tempMap;
//			} else {
//				structureMap.put(structure, structureFeatureConfiguration);
//			}
//		});
//	}

}
