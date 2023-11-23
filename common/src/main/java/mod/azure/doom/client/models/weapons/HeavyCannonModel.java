package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.HeavyCannon;
import net.minecraft.resources.ResourceLocation;

public class HeavyCannonModel extends GeoModel<HeavyCannon> {
    @Override
    public ResourceLocation getModelResource(HeavyCannon object) {
        return MCDoom.modResource("geo/heavycannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HeavyCannon object) {
        return MCDoom.modResource("textures/item/heavycannon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HeavyCannon animatable) {
        return MCDoom.modResource("animations/heavycannon.animation.json");
    }
}
