package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomStructuresHelper;
import mod.azure.doom.registry.FabricDoomStructures;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class FabricDoomStructureHelper implements DoomStructuresHelper {

    @Override
    public StructureType<?> getGladiatorStructure() {
        return FabricDoomStructures.GLADIATOR_FIGHT;
    }

    @Override
    public StructureType<?> getHellChurchStructure() {
        return FabricDoomStructures.HELL_CHURCH;
    }

    @Override
    public StructureType<?> getIconStructure() {
        return FabricDoomStructures.ICON_FIGHT;
    }

    @Override
    public StructureType<?> getMotherDemonStructure() {
        return FabricDoomStructures.MOTHERDEMON;
    }
}
