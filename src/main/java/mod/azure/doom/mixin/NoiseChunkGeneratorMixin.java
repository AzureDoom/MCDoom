package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.doom.structures.DoomStructures;
import mod.azure.doom.structures.templates.ArchMaykrStructure;
import mod.azure.doom.structures.templates.MaykrStructure;
import mod.azure.doom.structures.templates.NetherPortalStructure;
import mod.azure.doom.structures.templates.PortalStructure;
import mod.azure.doom.structures.templates.TitanSkullStructure;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {

	@Inject(method = "getEntitySpawnList(Lnet/minecraft/world/biome/Biome;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/collection/Pool;", at = @At(value = "HEAD"), cancellable = true)
	private void structureMobs(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos,
			CallbackInfoReturnable<Pool<SpawnSettings.SpawnEntry>> cir) {
		Pool<SpawnSettings.SpawnEntry> pool = getStructureSpawns(biome, accessor, group, pos);

		// If not null, it was in our structure. Return the mob list and exit the method
		// now.
		if (pool != null)
			cir.setReturnValue(pool);
	}

	private static Pool<SpawnSettings.SpawnEntry> getStructureSpawns(Biome biome, StructureAccessor accessor,
			SpawnGroup group, BlockPos pos) {

		if (group == SpawnGroup.MONSTER) {
			if (accessor.getStructureAt(pos, DoomStructures.ARCHMAYKR).hasChildren()) {
				return ArchMaykrStructure.STRUCTURE_MONSTERS;
			}
			if (accessor.getStructureAt(pos, DoomStructures.MAYKR).hasChildren()) {
				return MaykrStructure.STRUCTURE_MONSTERS;
			}
			if (accessor.getStructureAt(pos, DoomStructures.NETHERPORTAL).hasChildren()) {
				return NetherPortalStructure.STRUCTURE_MONSTERS;
			}
			if (accessor.getStructureAt(pos, DoomStructures.PORTAL).hasChildren()) {
				return PortalStructure.STRUCTURE_MONSTERS;
			}
			if (accessor.getStructureAt(pos, DoomStructures.TITAN_SKULL).hasChildren()) {
				return TitanSkullStructure.STRUCTURE_MONSTERS;
			}
		} else if (group == SpawnGroup.CREATURE) {
			if (accessor.getStructureAt(pos, DoomStructures.ARCHMAYKR).hasChildren()) {
				return ArchMaykrStructure.STRUCTURE_CREATURES;
			}
			if (accessor.getStructureAt(pos, DoomStructures.MAYKR).hasChildren()) {
				return MaykrStructure.STRUCTURE_CREATURES;
			}
			if (accessor.getStructureAt(pos, DoomStructures.NETHERPORTAL).hasChildren()) {
				return NetherPortalStructure.STRUCTURE_CREATURES;
			}
			if (accessor.getStructureAt(pos, DoomStructures.PORTAL).hasChildren()) {
				return PortalStructure.STRUCTURE_CREATURES;
			}
			if (accessor.getStructureAt(pos, DoomStructures.TITAN_SKULL).hasChildren()) {
				return TitanSkullStructure.STRUCTURE_CREATURES;
			}
		}

		return null;
	}
}