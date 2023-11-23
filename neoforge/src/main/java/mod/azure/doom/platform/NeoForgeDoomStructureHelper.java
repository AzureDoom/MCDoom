package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomStructuresHelper;
import mod.azure.doom.registry.NeoDoomStructures;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class NeoForgeDoomStructureHelper implements DoomStructuresHelper {

    @Override
    public StructureType<?> getGladiatorStructure() {
        return NeoDoomStructures.GLADIATOR_FIGHT.get();
    }

    @Override
    public StructureType<?> getHellChurchStructure() {
        return NeoDoomStructures.HELL_CHURCH.get();
    }

    @Override
    public StructureType<?> getIconStructure() {
        return NeoDoomStructures.ICON_FIGHT.get();
    }

    @Override
    public StructureType<?> getMotherDemonStructure() {
        return NeoDoomStructures.MOTHERDEMON.get();
    }
}
