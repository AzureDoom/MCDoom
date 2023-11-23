package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.Mullet3DoomArmor;
import net.minecraft.resources.ResourceLocation;

public class Mullet3Model extends GeoModel<Mullet3DoomArmor> {
    @Override
    public ResourceLocation getModelResource(Mullet3DoomArmor object) {
        return MCDoom.modResource("geo/mulletarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mullet3DoomArmor object) {
        return MCDoom.modResource("textures/models/armor/redneck3_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mullet3DoomArmor animatable) {
        return MCDoom.modResource("animations/armor_animation.json");
    }
}