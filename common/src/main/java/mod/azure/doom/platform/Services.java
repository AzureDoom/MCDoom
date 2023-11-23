package mod.azure.doom.platform;

import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.platform.services.GeoRenderPhaseEventFactory;
import mod.azure.doom.platform.services.*;

import java.util.ServiceLoader;

public class Services {

    public static final GeoRenderPhaseEventFactory GEO_RENDER_PHASE_EVENT_FACTORY = load(GeoRenderPhaseEventFactory.class);
    public static final DoomNetwork NETWORK = load(DoomNetwork.class);
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final DoomItemsHelper ITEMS_HELPER = load(DoomItemsHelper.class);
    public static final DoomBlocksHelper BLOCKS_HELPER = load(DoomBlocksHelper.class);
    public static final DoomEntitiesHelper ENTITIES_HELPER = load(DoomEntitiesHelper.class);
    public static final DoomStructuresHelper STRUCTURES_HELPER = load(DoomStructuresHelper.class);
    public static final DoomSoundsHelper SOUNDS_HELPER = load(DoomSoundsHelper.class);
    public static final DoomParticlesHelper PARTICLES_HELPER = load(DoomParticlesHelper.class);

    private Services() {

    }

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        AzureLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}