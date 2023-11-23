package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.SuperShotgun;
import net.minecraft.resources.ResourceLocation;

public class SSGModel extends GeoModel<SuperShotgun> {
    @Override
    public ResourceLocation getModelResource(SuperShotgun object) {
        return MCDoom.modResource("geo/supershotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuperShotgun object) {
        return MCDoom.modResource("textures/item/supershotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuperShotgun animatable) {
        return MCDoom.modResource("animations/supershotgun.animation.json");
    }
}
