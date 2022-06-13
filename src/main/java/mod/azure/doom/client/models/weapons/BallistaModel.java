package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Ballista;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BallistaModel extends AnimatedGeoModel<Ballista> {
	@Override
	public Identifier getModelResource(Ballista object) {
		return new Identifier(DoomMod.MODID, "geo/ballista.geo.json");
	}

	@Override
	public Identifier getTextureResource(Ballista object) {
		return new Identifier(DoomMod.MODID, "textures/items/ballista.png");
	}

	@Override
	public Identifier getAnimationResource(Ballista animatable) {
		return new Identifier(DoomMod.MODID, "animations/ballista.animation.json");
	}
}
