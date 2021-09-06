package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Chaingun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChaingunModel extends AnimatedGeoModel<Chaingun> {
	@Override
	public Identifier getModelLocation(Chaingun object) {
		return new Identifier(DoomMod.MODID, "geo/chaingun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Chaingun object) {
		return new Identifier(DoomMod.MODID, "textures/items/chainguneternal.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Chaingun animatable) {
		return new Identifier(DoomMod.MODID, "animations/chaingun.animation.json");
	}
}
