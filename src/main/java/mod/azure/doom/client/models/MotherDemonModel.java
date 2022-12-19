package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MotherDemonModel extends GeoModel<MotherDemonEntity> {

	@Override
	public Identifier getModelResource(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "geo/motherdemon.geo.json");
	}

	@Override
	public Identifier getTextureResource(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/motherdemon.png");
	}

	@Override
	public Identifier getAnimationResource(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "animations/motherdemon.animation.json");
	}

	@Override
	public RenderLayer getRenderType(MotherDemonEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}