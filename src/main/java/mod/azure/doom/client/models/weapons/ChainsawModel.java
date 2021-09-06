package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChainsawModel extends AnimatedGeoModel<ChainsawAnimated> {
	@Override
	public Identifier getModelLocation(ChainsawAnimated object) {
		return new Identifier(DoomMod.MODID, "geo/chainsaw_eternal.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ChainsawAnimated object) {
		return new Identifier(DoomMod.MODID, "textures/items/chainsaweternal.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ChainsawAnimated animatable) {
		return new Identifier(DoomMod.MODID, "animations/chainsaw.animation.json");
	}
}
