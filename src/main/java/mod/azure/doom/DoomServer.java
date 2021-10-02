package mod.azure.doom;

import java.util.HashMap;
import java.util.Map;

import mod.azure.doom.mixin.StructuresConfigAccessor;
import mod.azure.doom.structures.DoomStructures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class DoomServer implements DedicatedServerModInitializer, ClientModInitializer {
	@Override
	public void onInitializeServer() {
		removeStructureSpawningFromSelectedDimension();
	}

	@Override
	public void onInitializeClient() {
		removeStructureSpawningFromSelectedDimension();
	}

	public static void removeStructureSpawningFromSelectedDimension() {
		ServerWorldEvents.LOAD.register((MinecraftServer minecraftServer, ServerWorld serverWorld) -> {
			Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(
					serverWorld.getChunkManager().getChunkGenerator().getStructuresConfig().getStructures());
			if (!serverWorld.getRegistryKey().getValue().getNamespace().equals("minecraft")) {
				tempMap.keySet().remove(DoomStructures.MAYKR);
				tempMap.keySet().remove(DoomStructures.ARCHMAYKR);
				tempMap.keySet().remove(DoomStructures.TITAN_SKULL);
				tempMap.keySet().remove(DoomStructures.PORTAL);
				tempMap.keySet().remove(DoomStructures.NETHERPORTAL);
				tempMap.keySet().remove(DoomStructures.MOTHERDEMON);
			}
			if (!serverWorld.getRegistryKey().getValue().getPath().equals("the_end")) {
				tempMap.keySet().remove(DoomStructures.MAYKR);
				tempMap.keySet().remove(DoomStructures.ARCHMAYKR);
			}
			if (!serverWorld.getRegistryKey().getValue().getPath().equals("the_nether")) {
				tempMap.keySet().remove(DoomStructures.TITAN_SKULL);
				tempMap.keySet().remove(DoomStructures.MOTHERDEMON);
				tempMap.keySet().remove(DoomStructures.NETHERPORTAL);
			}
			if ((serverWorld.getRegistryKey().getValue().getPath().equals("the_nether"))
					|| serverWorld.getRegistryKey().getValue().getPath().equals("the_end")) {
				tempMap.keySet().remove(DoomStructures.PORTAL);
			}

			((StructuresConfigAccessor) serverWorld.getChunkManager().getChunkGenerator().getStructuresConfig())
					.setStructures(tempMap);
		});
	}
}
