package mod.azure.doom.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

@Mixin(StructurePoolFeatureConfig.class)
public interface StructurePoolFeatureConfigAccessor {

    @Mutable
    @Accessor("startPool")
    void setStructures(Supplier<StructurePool> newPoolSupplier);

    @Mutable
    @Accessor("size")
    void setSize(int newSize);
}