package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.AstroDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class AstroModel extends GeoModel<AstroDoomArmor> {
    @Override
    public ResourceLocation getModelResource(AstroDoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AstroDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/astro_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AstroDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}