package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.SwordCrucibleItem;
import net.minecraft.resources.ResourceLocation;

public class SwordCrucibleModel extends GeoModel<SwordCrucibleItem> {
    @Override
    public ResourceLocation getModelResource(SwordCrucibleItem object) {
        return MCDoom.modResource("geo/cruciblesword.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SwordCrucibleItem object) {
        return MCDoom.modResource("textures/item/crucible.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SwordCrucibleItem animatable) {
        return MCDoom.modResource("animations/cruciblesword.animation.json");
    }
}
