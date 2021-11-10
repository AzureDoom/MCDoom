package mod.azure.doom.util;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class RegistrationHelper {

	public static Supplier<StructurePool> pool(StructurePool pool) {
		return () -> pool;
	}

	public static void addToBiome(Identifier id, Predicate<BiomeSelectionContext> selectorPredicate,
			Consumer<BiomeModificationContext> biomeAdditionConsumer) {
		BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, selectorPredicate, biomeAdditionConsumer);
	}

	public static void addStructure(BiomeModificationContext context, ConfiguredStructureFeature<?, ?> feature) {
		context.getGenerationSettings().addBuiltInStructure(feature);
	}

}
