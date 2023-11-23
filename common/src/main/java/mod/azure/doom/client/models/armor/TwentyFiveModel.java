package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.TwentyFiveDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class TwentyFiveModel extends GeoModel<TwentyFiveDoomArmor> {
    @Override
    public ResourceLocation getModelResource(TwentyFiveDoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TwentyFiveDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/twenty_five_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TwentyFiveDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}