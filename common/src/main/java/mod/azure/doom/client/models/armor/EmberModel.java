package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.EmberDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class EmberModel extends GeoModel<EmberDoomArmor> {
    @Override
    public ResourceLocation getModelResource(EmberDoomArmor object) {
        return MCDoom.modResource("geo/doom1armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EmberDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/ember_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EmberDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}