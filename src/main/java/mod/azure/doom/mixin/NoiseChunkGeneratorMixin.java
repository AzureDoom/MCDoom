package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.doom.structures.DoomStructures;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
	
	@Inject(at = @At("HEAD"), method = "getEntitySpawnList", cancellable = true)
	private void structureMobs(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos,
			CallbackInfoReturnable<Pool<SpawnSettings.SpawnEntry>> cir) {

		Pool<SpawnSettings.SpawnEntry> list = getStructureSpawns(biome, accessor, group, pos);

		if (list != null)
			cir.setReturnValue(list);
	}

	private static Pool<SpawnSettings.SpawnEntry> getStructureSpawns(Biome biome, StructureAccessor accessor,
			SpawnGroup group, BlockPos pos) {

		if (group == SpawnGroup.MONSTER) {
			if (accessor.getStructureAt(pos, true, DoomStructures.ARCHMAYKR).hasChildren()) {
				return DoomStructures.ARCHMAYKR.getMonsterSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.MAYKR).hasChildren()) {
				return DoomStructures.MAYKR.getMonsterSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.PORTAL).hasChildren()) {
				return DoomStructures.PORTAL.getMonsterSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.NETHERPORTAL).hasChildren()) {
				return DoomStructures.NETHERPORTAL.getMonsterSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.TITAN_SKULL).hasChildren()) {
				return DoomStructures.TITAN_SKULL.getMonsterSpawns();
			}
		} else if (group == SpawnGroup.CREATURE) {
			if (accessor.getStructureAt(pos, true, DoomStructures.ARCHMAYKR).hasChildren()) {
				return DoomStructures.ARCHMAYKR.getCreatureSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.MAYKR).hasChildren()) {
				return DoomStructures.MAYKR.getCreatureSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.PORTAL).hasChildren()) {
				return DoomStructures.PORTAL.getCreatureSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.NETHERPORTAL).hasChildren()) {
				return DoomStructures.NETHERPORTAL.getCreatureSpawns();
			}
			if (accessor.getStructureAt(pos, true, DoomStructures.TITAN_SKULL).hasChildren()) {
				return DoomStructures.TITAN_SKULL.getCreatureSpawns();
			}
		}

		return null;
	}
}