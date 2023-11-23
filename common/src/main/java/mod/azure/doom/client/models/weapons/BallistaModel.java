package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.Ballista;
import net.minecraft.resources.ResourceLocation;

public class BallistaModel extends GeoModel<Ballista> {
    @Override
    public ResourceLocation getModelResource(Ballista object) {
        return MCDoom.modResource("geo/ballista.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Ballista object) {
        return MCDoom.modResource("textures/item/ballista.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Ballista animatable) {
        return MCDoom.modResource("animations/ballista.animation.json");
    }
}
