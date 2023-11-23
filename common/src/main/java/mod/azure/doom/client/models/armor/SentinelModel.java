package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.SentinelDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class SentinelModel extends GeoModel<SentinelDoomArmor> {
    @Override
    public ResourceLocation getModelResource(SentinelDoomArmor object) {
        return MCDoom.modResource("geo/sentinelarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SentinelDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/sentinel_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SentinelDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}