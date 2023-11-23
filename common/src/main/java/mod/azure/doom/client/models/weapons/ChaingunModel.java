package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.Chaingun;
import net.minecraft.resources.ResourceLocation;

public class ChaingunModel extends GeoModel<Chaingun> {
    @Override
    public ResourceLocation getModelResource(Chaingun object) {
        return MCDoom.modResource("geo/chaingun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Chaingun object) {
        return MCDoom.modResource("textures/item/chainguneternal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Chaingun animatable) {
        return MCDoom.modResource("animations/chaingun.animation.json");
    }
}
