package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.ChainsawAnimated;
import net.minecraft.resources.ResourceLocation;

public class ChainsawModel extends GeoModel<ChainsawAnimated> {
    @Override
    public ResourceLocation getModelResource(ChainsawAnimated object) {
        return MCDoom.modResource("geo/chainsaw_eternal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChainsawAnimated object) {
        return MCDoom.modResource("textures/item/chainsaweternal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChainsawAnimated animatable) {
        return MCDoom.modResource("animations/chainsaw.animation.json");
    }
}
