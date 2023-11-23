package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.MaykrDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class MaykrModel extends GeoModel<MaykrDoomArmor> {
    @Override
    public ResourceLocation getModelResource(MaykrDoomArmor object) {
        return MCDoom.modResource("geo/maykrarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MaykrDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/maykr_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MaykrDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}