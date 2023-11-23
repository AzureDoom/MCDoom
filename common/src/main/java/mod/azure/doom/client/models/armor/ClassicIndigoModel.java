package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.ClassicIndigoDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class ClassicIndigoModel extends GeoModel<ClassicIndigoDoomArmor> {
    @Override
    public ResourceLocation getModelResource(ClassicIndigoDoomArmor object) {
        return MCDoom.modResource("geo/classicarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClassicIndigoDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/classic_indigo_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClassicIndigoDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}