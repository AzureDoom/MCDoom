package mod.azure.doom.client.models.tile;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemModel extends AnimatedGeoModel<TotemEntity> {
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
		return new Identifier(DoomMod.MODID, "textures/blocks/totem.png");
	}
}
