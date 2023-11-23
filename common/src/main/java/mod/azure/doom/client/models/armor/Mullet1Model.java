package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.MulletDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class Mullet1Model extends GeoModel<MulletDoomArmor> {
    @Override
    public ResourceLocation getModelResource(MulletDoomArmor object) {
        return MCDoom.modResource("geo/mulletarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MulletDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/redneck1_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MulletDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}