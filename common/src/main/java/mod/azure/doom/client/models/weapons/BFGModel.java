package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.BFG;
import net.minecraft.resources.ResourceLocation;

public class BFGModel extends GeoModel<BFG> {
    @Override
    public ResourceLocation getModelResource(BFG object) {
        return MCDoom.modResource("geo/bfgeternal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BFG object) {
        return MCDoom.modResource("textures/item/bfgeternal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BFG animatable) {
        return MCDoom.modResource("animations/bfg.animation.json");
    }
}
