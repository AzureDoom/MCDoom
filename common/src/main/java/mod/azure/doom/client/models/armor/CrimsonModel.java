package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.CrimsonDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class CrimsonModel extends GeoModel<CrimsonDoomArmor> {
    @Override
    public ResourceLocation getModelResource(CrimsonDoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrimsonDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/crimson_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrimsonDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}