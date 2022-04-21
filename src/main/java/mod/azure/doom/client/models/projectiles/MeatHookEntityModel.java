package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class MeatHookEntityModel extends AnimatedGeoModel<MeatHookEntity> {
	@Override
	public Identifier getModelResource(MeatHookEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public Identifier getTextureResource(MeatHookEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/supershotgun.png");
	}

	@Override
	public Identifier getAnimationResource(MeatHookEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
