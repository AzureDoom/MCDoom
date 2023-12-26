package mod.azure.doom.registry;

import com.mojang.serialization.Codec;
import mod.azure.doom.MCDoom;
import mod.azure.doom.structures.HellChurchStructure;
import mod.azure.doom.structures.IconStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public record FabricDoomStructures() {

    public static StructureType<?> HELL_CHURCH;
    public static StructureType<?> ICON_FIGHT;
    public static StructureType<?> GLADIATOR_FIGHT;
    public static StructureType<?> MOTHERDEMON;
    public static StructureType<?> ARCHMAYKR;

    public static void registerStructureFeatures() {
        HELL_CHURCH = register(MCDoom.modResource("hell_church"), HellChurchStructure.CODEC);
        ICON_FIGHT = register(MCDoom.modResource("icon_fight"), IconStructure.CODEC);
        GLADIATOR_FIGHT = register(MCDoom.modResource("gladiator_fight"), JigsawStructure.CODEC);
        MOTHERDEMON = register(MCDoom.modResource("motherdemon1a"), JigsawStructure.CODEC);
        ARCHMAYKR = register(MCDoom.modResource("archmakyr"), JigsawStructure.CODEC);
    }

    private static <S extends Structure> StructureType<S> register(ResourceLocation id, Codec<S> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, id, () -> codec);
    }
}
