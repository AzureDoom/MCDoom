package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.DoomicornDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DoomicornModel extends GeoModel<DoomicornDoomArmor> {
    @Override
    public ResourceLocation getModelResource(DoomicornDoomArmor object) {
        return MCDoom.modResource("geo/doomicornarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoomicornDoomArmor object) {
        return MCDoom.modResource("textures/models/armor/doomicorn_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DoomicornDoomArmor animatable) {
        return MCDoom.modResource("animations/doomicorn_animation.json");
    }
}