package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class CyberdemonModel extends AnimatedTickingGeoModel<CyberdemonEntity> {

	public Identifier classic_model = new Identifier(DoomMod.MODID, "geo/cyberdemon.geo.json");
	public Identifier c2016_model = new Identifier(DoomMod.MODID, "geo/cyberdemon2016.geo.json");
	public Identifier tyrant_model = new Identifier(DoomMod.MODID, "geo/tyrant.geo.json");
	public Identifier classic_texture = new Identifier(DoomMod.MODID, "textures/entity/cyberdemon-texturemap.png");
	public Identifier d64_texture = new Identifier(DoomMod.MODID, "textures/entity/cyberdemon-64.png");
	public Identifier c2016_texture = new Identifier(DoomMod.MODID, "textures/entity/cyberdemon2016.png");
	public Identifier tyrant_texture = new Identifier(DoomMod.MODID, "textures/entity/tyrant.png");
	public Identifier classic_animation = new Identifier(DoomMod.MODID, "animations/cyberdemon_animation.json");
	public Identifier c2016_animation = new Identifier(DoomMod.MODID, "animations/cyberdemon2016.animation.json");
	public Identifier tyrant_animation = new Identifier(DoomMod.MODID, "animations/tyrant.animation.json");

	@Override
	public Identifier getModelResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_model : object.getVariant() == 3 ? tyrant_model : classic_model;
	}

	@Override
	public Identifier getTextureResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_texture
				: object.getVariant() == 3 ? tyrant_texture : object.getVariant() == 4 ? d64_texture : classic_texture;
	}

	@Override
	public Identifier getAnimationResource(CyberdemonEntity object) {
		return object.getVariant() == 2 ? c2016_animation
				: object.getVariant() == 3 ? tyrant_animation : classic_animation;
	}
}