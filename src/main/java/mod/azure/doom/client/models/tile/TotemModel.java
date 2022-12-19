package mod.azure.doom.client.models.tile;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TotemModel extends GeoModel<TotemEntity> {
	@Override
	public Identifier getAnimationResource(TotemEntity entity) {
		return new Identifier(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public Identifier getModelResource(TotemEntity animatable) {
		return new Identifier(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public Identifier getTextureResource(TotemEntity entity) {
		return new Identifier(DoomMod.MODID, "textures/block/totem.png");
	}

	@Override
	public RenderLayer getRenderType(TotemEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
