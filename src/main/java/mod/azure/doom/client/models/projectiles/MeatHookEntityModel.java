package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MeatHookEntityModel extends GeoModel<MeatHookEntity> {
	@Override
	public Identifier getModelResource(MeatHookEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public Identifier getTextureResource(MeatHookEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/supershotgun.png");
	}

	@Override
	public Identifier getAnimationResource(MeatHookEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/chainblade.animation.json");
	}

	@Override
	public RenderLayer getRenderType(MeatHookEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
