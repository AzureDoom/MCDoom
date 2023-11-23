package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.DarkLordCrucibleItem;
import net.minecraft.resources.ResourceLocation;

public class DarkLordCrucibleModel extends GeoModel<DarkLordCrucibleItem> {
    @Override
    public ResourceLocation getModelResource(DarkLordCrucibleItem object) {
        return MCDoom.modResource("geo/darklordcrucible.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DarkLordCrucibleItem object) {
        return MCDoom.modResource("textures/item/darklordcrucible.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DarkLordCrucibleItem animatable) {
        return MCDoom.modResource("animations/cruciblesword.animation.json");
    }
}
