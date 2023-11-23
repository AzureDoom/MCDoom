package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.NightmareDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class NightmareModel extends GeoModel<NightmareDoomArmor> {
    @Override
    public ResourceLocation getModelResource(NightmareDoomArmor object) {
        return MCDoom.modResource("geo/doomicornarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NightmareDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/nightmare_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NightmareDoomArmor animatable) {
        return MCDoom.modResource("animations/doomicorn_animation.json");
    }
}