package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.BFG9000;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFG9000Model extends AnimatedGeoModel<BFG9000> {
	@Override
	public Identifier getModelResource(BFG9000 object) {
		return new Identifier(DoomMod.MODID, "geo/bfg9000.geo.json");
	}

	@Override
	public Identifier getTextureResource(BFG9000 object) {
		return new Identifier(DoomMod.MODID, "textures/items/bfg9000.png");
	}

	@Override
	public Identifier getAnimationResource(BFG9000 animatable) {
		return new Identifier(DoomMod.MODID, "animations/bfg9000.animation.json");
	}
}
