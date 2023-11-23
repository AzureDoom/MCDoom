package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.BFG9000;
import net.minecraft.resources.ResourceLocation;

public class BFG9000Model extends GeoModel<BFG9000> {
    @Override
    public ResourceLocation getModelResource(BFG9000 object) {
        return MCDoom.modResource("geo/bfg9000.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BFG9000 object) {
        return MCDoom.modResource("textures/item/bfg9000.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BFG9000 animatable) {
        return MCDoom.modResource("animations/bfg9000.animation.json");
    }
}
