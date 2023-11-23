package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.PraetorDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class PraetorModel extends GeoModel<PraetorDoomArmor> {
    @Override
    public ResourceLocation getModelResource(PraetorDoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PraetorDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/praetor_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PraetorDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}