package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.DemoncideDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DemoncideModel extends GeoModel<DemoncideDoomArmor> {
    @Override
    public ResourceLocation getModelResource(DemoncideDoomArmor object) {
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DemoncideDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/demoncide_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DemoncideDoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}