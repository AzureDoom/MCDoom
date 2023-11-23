package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.DarkLordArmor;
import net.minecraft.resources.ResourceLocation;

public class DarkLordModel extends GeoModel<DarkLordArmor> {
    @Override
    public ResourceLocation getModelResource(DarkLordArmor object) {
        return MCDoom.modResource("geo/darklordarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DarkLordArmor object) {
        return MCDoom.modResource("textures/models/armor/darklordarmor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DarkLordArmor animatable) {
        return MCDoom.modResource("animations/darklordarmor.animation.json");
    }
}