package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaykr;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnmaykrModel extends AnimatedGeoModel<Unmaykr> {
	@Override
	public Identifier getModelLocation(Unmaykr object) {
		return new Identifier(DoomMod.MODID, "geo/unmaykr.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Unmaykr object) {
		return new Identifier(DoomMod.MODID, "textures/items/unmaykr.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Unmaykr animatable) {
		return new Identifier(DoomMod.MODID, "animations/unmaykr.animation.json");
	}
}
