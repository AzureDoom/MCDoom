package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.HeavyCannon;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class HeavyCannonModel extends AnimatedGeoModel<HeavyCannon> {
	@Override
	public Identifier getModelResource(HeavyCannon object) {
		return new Identifier(DoomMod.MODID, "geo/heavycannon.geo.json");
	}

	@Override
	public Identifier getTextureResource(HeavyCannon object) {
		return new Identifier(DoomMod.MODID, "textures/items/heavycannon.png");
	}

	@Override
	public Identifier getAnimationResource(HeavyCannon animatable) {
		return new Identifier(DoomMod.MODID, "animations/heavycannon.animation.json");
	}
}
