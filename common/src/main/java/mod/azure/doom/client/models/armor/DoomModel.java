package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.DoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DoomModel extends GeoModel<DoomArmor> {
    @Override
    public ResourceLocation getModelResource(DoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoomArmor object) {
        return MCDoom.modResource("textures/models/armor/doom_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}