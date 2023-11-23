package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.ClassicDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class ClassicModel extends GeoModel<ClassicDoomArmor> {
    @Override
    public ResourceLocation getModelResource(ClassicDoomArmor object) {
        return MCDoom.modResource("geo/classicarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClassicDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/classic_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClassicDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}