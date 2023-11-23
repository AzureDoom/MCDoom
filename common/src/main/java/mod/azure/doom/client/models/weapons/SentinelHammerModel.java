package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.SentinelHammerItem;
import net.minecraft.resources.ResourceLocation;

public class SentinelHammerModel extends GeoModel<SentinelHammerItem> {
    @Override
    public ResourceLocation getModelResource(SentinelHammerItem object) {
        return MCDoom.modResource("geo/sentinelhammer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SentinelHammerItem object) {
        return MCDoom.modResource("textures/item/sentinel_hammer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SentinelHammerItem animatable) {
        return MCDoom.modResource("animations/sentinelhammer.animation.json");
    }
}
