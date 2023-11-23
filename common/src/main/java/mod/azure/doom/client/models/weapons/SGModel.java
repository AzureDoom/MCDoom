package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.Shotgun;
import net.minecraft.resources.ResourceLocation;

public class SGModel extends GeoModel<Shotgun> {
    @Override
    public ResourceLocation getModelResource(Shotgun object) {
        return MCDoom.modResource("geo/shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shotgun object) {
        return MCDoom.modResource("textures/item/shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Shotgun animatable) {
        return MCDoom.modResource("animations/shotgun.animation.json");
    }
}
