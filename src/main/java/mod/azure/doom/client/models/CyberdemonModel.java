package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CyberdemonModel extends AnimatedTickingGeoModel<CyberdemonEntity> {

	public ResourceLocation classic_model = new ResourceLocation(DoomMod.MODID, "geo/cyberdemon.geo.json");
	public ResourceLocation c2016_model = new ResourceLocation(DoomMod.MODID, "geo/cyberdemon2016.geo.json");
	public ResourceLocation tyrant_model = new ResourceLocation(DoomMod.MODID, "geo/tyrant.geo.json");
	public ResourceLocation classic_texture = new ResourceLocation(DoomMod.MODID,
			"textures/entity/cyberdemon-texturemap.png");
	public ResourceLocation c2016_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/cyberdemon2016.png");
	public ResourceLocation tyrant_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/tyrant.png");
	public ResourceLocation classic_animation = new ResourceLocation(DoomMod.MODID,
			"animations/cyberdemon_animation.json");
	public ResourceLocation c2016_animation = new ResourceLocation(DoomMod.MODID,
			"animations/cyberdemon2016.animation.json");
	public ResourceLocation tyrant_animation = new ResourceLocation(DoomMod.MODID, "animations/tyrant.animation.json");

	@Override
	public ResourceLocation getModelResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_model : object.getVariant() == 3 ? tyrant_model : classic_model;
	}

	@Override
	public ResourceLocation getTextureResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_texture : object.getVariant() == 3 ? tyrant_texture : classic_texture;
	}

	@Override
	public ResourceLocation getAnimationResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_animation
				: object.getVariant() == 3 ? tyrant_animation : classic_animation;
	}
}