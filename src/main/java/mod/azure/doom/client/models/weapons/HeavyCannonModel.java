package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.HeavyCannon;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HeavyCannonModel extends AnimatedGeoModel<HeavyCannon> {
	@Override
	public Identifier getModelLocation(HeavyCannon object) {
		return new Identifier(DoomMod.MODID, "geo/heavycannon.geo.json");
	}

	@Override
	public Identifier getTextureLocation(HeavyCannon object) {
		return new Identifier(DoomMod.MODID, "textures/items/heavycannon.png");
	}

	@Override
	public Identifier getAnimationFileLocation(HeavyCannon animatable) {
		return new Identifier(DoomMod.MODID, "animations/heavycannon.animation.json");
	}
}
