package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArachnotronModel extends GeoModel<ArachnotronEntity> {

    @Override
    public ResourceLocation getModelResource(ArachnotronEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "geo/" + (object.getVariant() == 2 ? "arachnotron64" : "arachnotron") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArachnotronEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "textures/entity/arachnotron-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArachnotronEntity object) {
        return MCDoom.modResource("animations/" + (object.getVariant() == 2 ? "arachnotron64.animation" : "arachnotron_walking") + ".json");
    }

    @Override
    public RenderType getRenderType(ArachnotronEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}